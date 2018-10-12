package seedu.inventory.model.sale;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class SaleIDTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new SaleID(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        // Test empty string
        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleID(""));

        // Test letters
        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleID("ABCDE"));

        // Test alphanumeric
        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleID("123ABC"));
    }

    @Test
    public void isValidSaleID() {
        // null sale ID
        Assert.assertThrows(NullPointerException.class, () -> SaleID.isValidSaleID(null));
    }
}
