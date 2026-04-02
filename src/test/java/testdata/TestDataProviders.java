package testdata;

import org.openqa.selenium.Keys;
import org.testng.annotations.DataProvider;

public class TestDataProviders {

    @DataProvider(name = "dropdownSelectionData")
    public Object[][] dropdownSelectionData() {
        return new Object[][]{
                {"visibleText", "Option 1", "Option 1"},
                {"value", "2", "Option 2"}
        };
    }

    @DataProvider(name = "checkboxIndexData")
    public Object[][] checkboxIndexData() {
        return new Object[][]{
                {1},
                {2}
        };
    }

    @DataProvider(name = "keyPressData")
    public Object[][] keyPressData() {
        return new Object[][]{
                {"A", "You entered: A"},
                {"B", "You entered: B"},
                {Keys.TAB, "You entered: TAB"}
        };
    }
}
