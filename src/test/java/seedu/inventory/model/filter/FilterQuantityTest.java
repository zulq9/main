package seedu.inventory.model.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.model.item.FilterQuantity;
import seedu.inventory.testutil.Assert;

public class FilterQuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new FilterQuantity(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new FilterQuantity(invalidName));
    }

    @Test
    public void isValidName() {
        // null filter quantity
        Assert.assertThrows(NullPointerException.class, () -> FilterQuantity.isValidFilterQuantity(null));

        // invalid filter quantity
        assertFalse(FilterQuantity.isValidFilterQuantity("")); // empty string
        assertFalse(FilterQuantity.isValidFilterQuantity(" ")); // spaces only
        assertFalse(FilterQuantity.isValidFilterQuantity("^")); // only non-alphanumeric characters
        assertFalse(FilterQuantity.isValidFilterQuantity("<^")); // non-alphanumeric characters with condition
        assertFalse(FilterQuantity.isValidFilterQuantity("35")); // valid quantity without condition
        assertFalse(FilterQuantity.isValidFilterQuantity(">35*")); // contains non-alphanumeric characters
        assertFalse(FilterQuantity.isValidFilterQuantity("=100")); // invalid condition
        assertFalse(FilterQuantity.isValidFilterQuantity("<100.00")); // invalid number

        // valid filter quantity
        assertTrue(FilterQuantity.isValidFilterQuantity(">134")); // more than whole numbers only
        assertTrue(FilterQuantity.isValidFilterQuantity("<134")); // less than whole numbers only
    }
}
