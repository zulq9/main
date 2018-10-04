package seedu.inventory.model.sale;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class SaleQuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new SaleQuantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        // Test empty field
        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleQuantity(""));

        // Test 0 quantity
        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleQuantity("0"));
    }

    @Test
    public void isValidSaleQuantity() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> SaleQuantity.isValidSaleQuantity(null));
    }
}
