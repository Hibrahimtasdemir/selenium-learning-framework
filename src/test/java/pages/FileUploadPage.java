package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FileUploadPage extends BasePage {

  private final By fileInput = By.id("file-upload");
  private final By uploadButton = By.id("file-submit");
  private final By uploadedFiles = By.id("uploaded-files");

  public FileUploadPage(WebDriver driver) {
    super(driver);
  }

  public void open() {
    openPath("/upload");
  }

  public void chooseFile(String absoluteFilePath) {
    type(fileInput, absoluteFilePath);
  }

  public void submitUpload() {
    click(uploadButton);
  }

  public String getUploadedFileName() {
    return getText(uploadedFiles);
  }
}
