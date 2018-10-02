package seedu.address.model.sale;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SaleProductTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new SaleProduct(null));
    }

    @Test
    public void constructor_invalidSKU_throwsIllegalArgumentException() {
        String invalidProductSku = "";

        Assert.assertThrows(IllegalArgumentException.class, () -> new SaleProduct(invalidProductSku));
    }

    @Test
    public void isValidProductSKU() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> SaleProduct.isValidProductSku(null));
    }

}
