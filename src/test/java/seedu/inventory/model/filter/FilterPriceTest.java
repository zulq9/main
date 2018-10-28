package seedu.inventory.model.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class FilterPriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new FilterPrice(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new FilterPrice(invalidName));
    }

    @Test
    public void isValidName() {
        // null filter price
        Assert.assertThrows(NullPointerException.class, () -> FilterPrice.isValidFilterPrice(null));

        // invalid filter price
        assertFalse(FilterPrice.isValidFilterPrice("")); // empty string
        assertFalse(FilterPrice.isValidFilterPrice(" ")); // spaces only
        assertFalse(FilterPrice.isValidFilterPrice("<^")); // non-alphanumeric characters with condition
        assertFalse(FilterPrice.isValidFilterPrice("^")); // only non-alphanumeric characters
        assertFalse(FilterPrice.isValidFilterPrice(">@35*")); // contains non-alphanumeric characters
        assertFalse(FilterPrice.isValidFilterPrice("36")); // valid price without condition
        assertFalse(FilterPrice.isValidFilterPrice("100.00")); // valid price without condition
        assertFalse(FilterPrice.isValidFilterPrice("=100")); // invalid condition
        assertFalse(FilterPrice.isValidFilterPrice(">100.000")); // invalid double

        // valid filter price
        assertTrue(FilterPrice.isValidFilterPrice(">134")); // more than whole numbers only
        assertTrue(FilterPrice.isValidFilterPrice("<134")); // less than whole numbers only
        assertTrue(FilterPrice.isValidFilterPrice("<134.00")); // more than decimals only
        assertTrue(FilterPrice.isValidFilterPrice(">134.0")); // less than decimals only
    }
}
