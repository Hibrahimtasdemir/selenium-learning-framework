package listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import driver.DriverFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.stream.Stream;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import reporting.HtmlReportManager;
import utils.ScreenshotUtils;

public class TestListener implements ITestListener, IExecutionListener {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Override
  public void onExecutionStart() {
    HtmlReportManager.startExecution();
  }

  @Override
  public void onExecutionFinish() {
    enrichAllureSeverityLabels();
    String reportPath = HtmlReportManager.flush();
    Reporter.log("HTML report saved to: " + reportPath, true);
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    RetryAnalyzer.clearRetryState(result);
    HtmlReportManager.addResult(result, "PASS", "Test passed.", null);
  }

  @Override
  public void onTestFailure(ITestResult result) {
    boolean retryPending = Boolean.TRUE.equals(result.getAttribute("retry.pending"));

    if (retryPending) {
      Object retryAttempt = result.getAttribute("retry.attempt");
      String retryMessage =
          "Retry scheduled. Attempt "
              + retryAttempt
              + " of "
              + config.FrameworkConfig.getRetryCount();
      HtmlReportManager.addResult(result, "RETRY", retryMessage, null);
      Reporter.log(retryMessage, true);
      return;
    }

    RetryAnalyzer.clearRetryState(result);
    String screenshotPath =
        ScreenshotUtils.captureScreenshot(
            DriverFactory.getDriver(), result.getMethod().getMethodName());

    String failureMessage = extractFailureMessage(result);
    HtmlReportManager.addResult(result, "FAIL", failureMessage, screenshotPath);
    addAllureFailureArtifacts(failureMessage, screenshotPath);

    if (screenshotPath != null) {
      Reporter.log("Screenshot saved to: " + screenshotPath, true);
    }
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    RetryAnalyzer.clearRetryState(result);
    HtmlReportManager.addResult(result, "SKIP", extractFailureMessage(result), null);
  }

  private String extractFailureMessage(ITestResult result) {
    if (result.getThrowable() == null) {
      return "No failure message.";
    }

    String message = result.getThrowable().getMessage();
    return message == null || message.isBlank()
        ? result.getThrowable().getClass().getSimpleName()
        : message;
  }

  private void addAllureFailureArtifacts(String failureMessage, String screenshotPath) {
    Allure.addAttachment("Failure Message", "text/plain", failureMessage);

    if (screenshotPath == null || screenshotPath.isBlank()) {
      return;
    }

    try {
      byte[] screenshotBytes = Files.readAllBytes(Path.of(screenshotPath));
      Allure.addAttachment(
          "Failure Screenshot", "image/png", new ByteArrayInputStream(screenshotBytes), ".png");
    } catch (IOException e) {
      Reporter.log("Could not attach screenshot to Allure: " + e.getMessage(), true);
    }
  }

  private void enrichAllureSeverityLabels() {
    Path resultsDirectory = resolveAllureResultsDirectory();
    if (!Files.isDirectory(resultsDirectory)) {
      return;
    }

    try (Stream<Path> resultFiles = Files.list(resultsDirectory)) {
      resultFiles
          .filter(path -> path.getFileName().toString().endsWith("-result.json"))
          .forEach(this::appendSeverityLabelIfMissing);
    } catch (IOException e) {
      Reporter.log("Could not enrich Allure severity labels: " + e.getMessage(), true);
    }
  }

  private Path resolveAllureResultsDirectory() {
    String configuredPath = System.getProperty("allure.results.directory", "target/allure-results");
    Path path = Path.of(configuredPath);
    if (path.isAbsolute()) {
      return path;
    }
    return Path.of("").toAbsolutePath().resolve(path).normalize();
  }

  private void appendSeverityLabelIfMissing(Path resultFile) {
    try {
      JsonNode rootNode = OBJECT_MAPPER.readTree(resultFile.toFile());
      if (!(rootNode instanceof ObjectNode resultNode)) {
        return;
      }

      String fullName = resultNode.path("fullName").asText("");
      String severityValue = resolveSeverityValue(fullName);
      if (severityValue == null) {
        return;
      }

      ArrayNode labelsNode = getOrCreateLabelsNode(resultNode);
      if (hasSeverityLabel(labelsNode)) {
        return;
      }

      ObjectNode severityLabelNode = OBJECT_MAPPER.createObjectNode();
      severityLabelNode.put("name", "severity");
      severityLabelNode.put("value", severityValue);
      labelsNode.add(severityLabelNode);

      OBJECT_MAPPER.writeValue(resultFile.toFile(), resultNode);
    } catch (IOException e) {
      Reporter.log(
          "Could not update Allure result file for severity: "
              + resultFile.getFileName()
              + " - "
              + e.getMessage(),
          true);
    }
  }

  private String resolveSeverityValue(String fullName) {
    if (fullName == null || fullName.isBlank()) {
      return null;
    }

    int methodSeparatorIndex = fullName.lastIndexOf('.');
    if (methodSeparatorIndex <= 0 || methodSeparatorIndex >= fullName.length() - 1) {
      return null;
    }

    String className = fullName.substring(0, methodSeparatorIndex);
    String methodName = fullName.substring(methodSeparatorIndex + 1);

    try {
      Class<?> testClass = Class.forName(className);
      Severity methodSeverity = resolveMethodSeverity(testClass, methodName);
      Severity severity = methodSeverity != null ? methodSeverity : testClass.getAnnotation(Severity.class);
      if (severity == null) {
        return null;
      }
      return severity.value().name().toLowerCase(Locale.ROOT);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  private Severity resolveMethodSeverity(Class<?> testClass, String methodName) {
    for (Method method : testClass.getDeclaredMethods()) {
      if (!method.getName().equals(methodName)) {
        continue;
      }
      Severity severity = method.getAnnotation(Severity.class);
      if (severity != null) {
        return severity;
      }
    }
    return null;
  }

  private ArrayNode getOrCreateLabelsNode(ObjectNode resultNode) {
    JsonNode labelsNode = resultNode.get("labels");
    if (labelsNode instanceof ArrayNode arrayNode) {
      return arrayNode;
    }

    ArrayNode newLabelsNode = OBJECT_MAPPER.createArrayNode();
    resultNode.set("labels", newLabelsNode);
    return newLabelsNode;
  }

  private boolean hasSeverityLabel(ArrayNode labelsNode) {
    for (JsonNode labelNode : labelsNode) {
      if ("severity".equals(labelNode.path("name").asText())) {
        return true;
      }
    }
    return false;
  }
}
