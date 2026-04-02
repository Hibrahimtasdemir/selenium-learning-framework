package listeners;

import driver.DriverFactory;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import reporting.HtmlReportManager;
import utils.ScreenshotUtils;

public class TestListener implements ITestListener, IExecutionListener {

  @Override
  public void onExecutionStart() {
    HtmlReportManager.startExecution();
  }

  @Override
  public void onExecutionFinish() {
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
}
