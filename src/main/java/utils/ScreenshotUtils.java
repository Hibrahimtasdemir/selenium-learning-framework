package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {

    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtils() {
    }

    public static String captureScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            return null;
        }

        try {
            Path screenshotDirectory = Path.of("test-output", "screenshots");
            Files.createDirectories(screenshotDirectory);

            String sanitizedName = testName.replaceAll("[^a-zA-Z0-9._-]", "_");
            String fileName = sanitizedName + "_" + LocalDateTime.now().format(TIMESTAMP_FORMAT) + ".png";
            Path targetPath = screenshotDirectory.resolve(fileName);

            Files.copy(
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath(),
                    targetPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return targetPath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to capture screenshot.", e);
        }
    }
}
