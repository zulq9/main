package seedu.inventory.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PurchaseOrderTableViewHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder;

public class PurchaseOrderTableViewTest extends GuiUnitTest {

    private static final ObservableList<PurchaseOrder> TYPICAL_PURCHASE_ORDERS =
            FXCollections.observableList(TypicalPurchaseOrder.getTypicalPurchaseOrder());

    private PurchaseOrderTableView purchaseOrderTableView;
    private PurchaseOrderTableViewHandle purchaseOrderTableViewHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> purchaseOrderTableView = new PurchaseOrderTableView(TYPICAL_PURCHASE_ORDERS));
        uiPartRule.setUiPart(purchaseOrderTableView);

        purchaseOrderTableViewHandle = new PurchaseOrderTableViewHandle(purchaseOrderTableView.getRoot());
    }

    @Test
    public void display() throws Exception {
        // The list size displayed in item table view should be equal to the number of items
        assertEquals(purchaseOrderTableViewHandle.getListSize(), TYPICAL_PURCHASE_ORDERS.size());

        // The list should be same
        assertEquals(purchaseOrderTableViewHandle.getItemList(), TYPICAL_PURCHASE_ORDERS);

        // The header should be correct
        assertEquals("Sku", purchaseOrderTableViewHandle.getColumnHeader(0));
        assertEquals("Quantity", purchaseOrderTableViewHandle.getColumnHeader(1));
        assertEquals("Date", purchaseOrderTableViewHandle.getColumnHeader(2));
        assertEquals("Supplier", purchaseOrderTableViewHandle.getColumnHeader(3));
        assertEquals("Status", purchaseOrderTableViewHandle.getColumnHeader(4));
    }
}
