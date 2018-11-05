package seedu.inventory.ui;

import static org.junit.Assert.assertEquals;
import static seedu.inventory.testutil.TypicalItems.getTypicalItems;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ItemTableViewHandle;
import seedu.inventory.model.item.Item;

public class ItemTableViewTest extends GuiUnitTest {

    private static final ObservableList<Item> TYPICAL_ITEMS =
            FXCollections.observableList(getTypicalItems());

    private ItemTableView itemTableView;
    private ItemTableViewHandle itemTableViewHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> itemTableView = new ItemTableView(TYPICAL_ITEMS));
        uiPartRule.setUiPart(itemTableView);

        itemTableViewHandle = new ItemTableViewHandle(itemTableView.getRoot());
    }

    @Test
    public void display() throws Exception {
        // The list size displayed in item table view should be equal to the number of items
        assertEquals(itemTableViewHandle.getListSize(), TYPICAL_ITEMS.size());

        // The list should be same
        assertEquals(itemTableViewHandle.getItemList(), TYPICAL_ITEMS);

        // The header should be correct
        assertEquals("Name", itemTableViewHandle.getColumnHeader(0));
        assertEquals("Price", itemTableViewHandle.getColumnHeader(1));
        assertEquals("Quantity", itemTableViewHandle.getColumnHeader(2));
        assertEquals("Sku", itemTableViewHandle.getColumnHeader(3));
        assertEquals("Tags", itemTableViewHandle.getColumnHeader(4));
    }
}
