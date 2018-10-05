package seedu.inventory.model.purchaseorder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.model.item.Item;
import seedu.inventory.testutil.Assert;
import seedu.inventory.testutil.TypicalItems;

public class PurchaseOrderTest {

    private static Item item = TypicalItems.IPHONE;
    private static Quantity quantity = new Quantity("15");
    private static RequiredDate rDate = new RequiredDate("2019-10-31");
    private static Supplier supplier = new Supplier("Apple Inc.");
    private static PurchaseOrder.Status status = PurchaseOrder.Status.PENDING;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PurchaseOrder(null, null, null, null, null));
    }

    @Test
    public void constructor_validPurchaseOrder() {
        PurchaseOrder po = new PurchaseOrder(item, quantity, rDate, supplier, status);
        assertNotNull(po);
    }

    @Test
    public void testToString() {
        PurchaseOrder po = new PurchaseOrder(item, quantity, rDate, supplier, status);
        String expected = " SKU: " + po.getItem().getSku()
                + " Item name: " + po.getItem().getName()
                + " Quantity: " + po.getQuantity()
                + " Date Required: " + po.getReqDate()
                + " Supplier: " + po.getSupplier()
                + " Status: " + po.getStatus();
        assertEquals(expected, po.toString());
    }

    @Test
    public void testHashCodeSymmetric() {
        // hashCode check name field value
        PurchaseOrder po1 = new PurchaseOrder(item, quantity, rDate, supplier, status);
        PurchaseOrder po2 = new PurchaseOrder(item, quantity, rDate, supplier, status);
        assertTrue(po1.hashCode() == po2.hashCode());
    }
}

