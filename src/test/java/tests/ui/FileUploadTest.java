package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import java.nio.file.Path;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FileUploadPage;

public class FileUploadTest extends BaseTest {

  @Severity(SeverityLevel.NORMAL)

  @Test(groups = {"smoke", "regression"})
  public void shouldUploadFileSuccessfully() {
    FileUploadPage fileUploadPage = new FileUploadPage(driver);
    String absoluteFilePath =
        Path.of("src", "test", "resources", "sample-upload.txt").toAbsolutePath().toString();

    fileUploadPage.open();
    fileUploadPage.chooseFile(absoluteFilePath);
    fileUploadPage.submitUpload();

    Assert.assertEquals(
        fileUploadPage.getUploadedFileName(),
        "sample-upload.txt",
        "Uploaded file name is not correct.");
  }
}
