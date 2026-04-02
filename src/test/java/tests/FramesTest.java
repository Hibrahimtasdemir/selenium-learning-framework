package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FramesPage;

public class FramesTest extends BaseTest {

    @Test(groups = {"regression"})
    public void shouldTypeInsideIframeEditor() {
        FramesPage framesPage = new FramesPage(driver);

        framesPage.open();
        framesPage.switchToEditorFrame();
        framesPage.clearEditor();
        framesPage.typeIntoEditor("Hello Frame");

        Assert.assertEquals(framesPage.getEditorText(), "Hello Frame");

        framesPage.switchToDefaultContent();
    }
}
