package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InfiniteScrollPage;

public class InfiniteScrollTest extends BaseTest {

    @Test(groups = {"regression"})
    public void shouldLoadMoreContentWhenScrollingDown() {
        InfiniteScrollPage infiniteScrollPage = new InfiniteScrollPage(driver);

        infiniteScrollPage.open();
        int initialParagraphCount = infiniteScrollPage.getParagraphCount();

        Assert.assertTrue(infiniteScrollPage.scrollUntilParagraphCountIncreases(initialParagraphCount),
                "Paragraph count should increase after scrolling down.");
    }
}
