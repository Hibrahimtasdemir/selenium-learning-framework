package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TablePage extends BasePage {

  private final By table = By.id("table1");

  public TablePage(WebDriver driver) {
    super(driver);
  }

  public void open() {
    openPath("/tables");
  }

  public int getRowCount() {
    return findAll(By.cssSelector("#table1 tbody tr")).size();
  }

  public String getCellText(int rowIndex, int columnIndex) {
    By cellLocator =
        By.cssSelector(
            "#table1 tbody tr:nth-of-type(" + rowIndex + ") td:nth-of-type(" + columnIndex + ")");
    return getText(cellLocator);
  }

  public String getEmailByLastName(String lastName) {
    int rowIndex = getRowIndexByLastName(lastName);
    return getCellText(rowIndex, 3);
  }

  public String getDueAmountByLastName(String lastName) {
    int rowIndex = getRowIndexByLastName(lastName);
    return getCellText(rowIndex, 4);
  }

  private int getRowIndexByLastName(String lastName) {
    int rowCount = getRowCount();

    for (int rowIndex = 1; rowIndex <= rowCount; rowIndex++) {
      String currentLastName = getCellText(rowIndex, 1);

      if (currentLastName.equals(lastName)) {
        return rowIndex;
      }
    }

    throw new IllegalArgumentException("Last name was not found in table: " + lastName);
  }
}
