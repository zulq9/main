package seedu.inventory.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder.IPHONEPO;
import static seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder.SAMSUNGNOTEPO;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.model.purchaseorder.PurchaseOrder;

public class PurchaseOrderListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final PurchaseOrderList purchaseOrderList = new PurchaseOrderList();

    @Test
    public void hasPurchaseOrder_nullPurchaseOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        purchaseOrderList.hasPurchaseOrder((PurchaseOrder) null);
    }

    @Test
    public void hasPurchaseOrder_purchaseOrderNotInInventory_returnsFalse() {
        assertFalse(purchaseOrderList.hasPurchaseOrder(IPHONEPO));
    }

    @Test
    public void hasPurchaseOrder_purchaseOrderInInventory_returnsFalse() {
        purchaseOrderList.addPurchaseOrder(IPHONEPO);
        assertTrue(purchaseOrderList.hasPurchaseOrder(IPHONEPO));
    }

    @Test
    public void getPurchaseOrderList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        purchaseOrderList.getPurchaseOrderList().remove(0);
    }

    @Test
    public void resetData() {
        ReadOnlyPurchaseOrderList anotherPurchaseOrderList = new PurchaseOrderList(purchaseOrderList);
        assertEquals(purchaseOrderList, anotherPurchaseOrderList);
    }

    @Test
    public void updatePurchaseOrder() {
        purchaseOrderList.addPurchaseOrder(IPHONEPO);
        assertTrue(purchaseOrderList.hasPurchaseOrder(IPHONEPO));
        assertFalse(purchaseOrderList.hasPurchaseOrder(SAMSUNGNOTEPO));
        purchaseOrderList.updatePurchaseOrder(IPHONEPO, SAMSUNGNOTEPO);
        assertFalse(purchaseOrderList.hasPurchaseOrder(IPHONEPO));
        assertTrue(purchaseOrderList.hasPurchaseOrder(SAMSUNGNOTEPO));
    }

    @Test
    public void removePurchaseOrder() {
        purchaseOrderList.addPurchaseOrder(IPHONEPO);
        assertTrue(purchaseOrderList.hasPurchaseOrder(IPHONEPO));
        purchaseOrderList.removePurchaseOrder(IPHONEPO);
        assertFalse(purchaseOrderList.hasPurchaseOrder(IPHONEPO));
    }
}
