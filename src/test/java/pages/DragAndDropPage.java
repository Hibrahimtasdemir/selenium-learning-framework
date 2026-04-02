package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DragAndDropPage extends BasePage {

    private final By columnA = By.id("column-a");
    private final By columnB = By.id("column-b");

    public DragAndDropPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPath("/drag_and_drop");
    }

    public void dragColumnAToColumnB() {
        WebElement source = find(columnA);
        WebElement target = find(columnB);

        String script = """
                function createCustomEvent(type) {
                    var event = new CustomEvent(type, {bubbles: true, cancelable: true});
                    event.dataTransfer = {
                        data: {},
                        setData: function(key, value) { this.data[key] = value; },
                        getData: function(key) { return this.data[key]; }
                    };
                    return event;
                }

                function dispatchEvent(element, event, transferData) {
                    if (transferData) {
                        event.dataTransfer = transferData;
                    }
                    element.dispatchEvent(event);
                }

                function simulateDragAndDrop(sourceElement, targetElement) {
                    var dragStartEvent = createCustomEvent('dragstart');
                    dispatchEvent(sourceElement, dragStartEvent);

                    var dropEvent = createCustomEvent('drop');
                    dispatchEvent(targetElement, dropEvent, dragStartEvent.dataTransfer);

                    var dragEndEvent = createCustomEvent('dragend');
                    dispatchEvent(sourceElement, dragEndEvent, dropEvent.dataTransfer);
                }

                simulateDragAndDrop(arguments[0], arguments[1]);
                """;

        ((JavascriptExecutor) driver).executeScript(script, source, target);
        pauseForDemo();
        waitUtils.waitForTextToBe(columnA, "B");
        waitUtils.waitForTextToBe(columnB, "A");
    }

    public String getColumnAText() {
        return getText(columnA);
    }

    public String getColumnBText() {
        return getText(columnB);
    }
}
