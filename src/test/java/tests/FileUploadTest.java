package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FileUploadPage;

import java.nio.file.Path;

public class FileUploadTest extends BaseTest {

    @Test
    public void shouldUploadFileSuccessfully() {
        FileUploadPage fileUploadPage = new FileUploadPage(driver);
        String absoluteFilePath = Path.of("src", "test", "resources", "sample-upload.txt")
                .toAbsolutePath()
                .toString();

        fileUploadPage.open();
        fileUploadPage.chooseFile(absoluteFilePath);
        fileUploadPage.submitUpload();

        Assert.assertEquals(fileUploadPage.getUploadedFileName(), "sample-upload.txt",
                "Uploaded file name is not correct.");
    }
}
