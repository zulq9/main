package seedu.inventory.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.inventory.testutil.EventsUtil.postNow;
import static seedu.inventory.testutil.TypicalItems.IPHONE;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.inventory.commons.events.ui.ItemPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private ItemPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new ItemPanelSelectionChangedEvent(IPHONE);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default image should be none as it is just a background image for ImageView
        assertEquals("", browserPanelHandle.getLoadedUrl());

        // associated image file of a item
        postNow(selectionChangedEventStub);
        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals("file:docs/images/iphone.jpg", browserPanelHandle.getLoadedUrl());
    }
}
