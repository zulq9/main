package seedu.address.model.purchaseorder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SupplierTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Supplier(null));
    }

    @Test
    public void constructor_invalidSupplier_throwsIllegalArgumentException() {
        String invalidSupplier = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Supplier(invalidSupplier));
    }

    @Test
    public void isValidSupplier() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Supplier.isValidSupplier(null));

        // invalid Supplier
        assertFalse(Supplier.isValidSupplier("")); // empty string
        assertFalse(Supplier.isValidSupplier(" ")); // spaces only

        // valid Supplier
        assertTrue(Supplier.isValidSupplier("Samsung"));
    }
}
