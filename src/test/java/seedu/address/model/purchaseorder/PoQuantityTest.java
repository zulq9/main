package seedu.address.model.purchaseorder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PoQuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PoQuantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new PoQuantity(invalidQuantity));
    }
    @Test
    public void isValidPoQuantity() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> PoQuantity.isValidPoQuantity(null));

        // invalid PoQuantity numbers
        assertFalse(PoQuantity.isValidPoQuantity("")); // empty string
        assertFalse(PoQuantity.isValidPoQuantity(" ")); // spaces only
        assertFalse(PoQuantity.isValidPoQuantity("0")); // 0 quantity
        assertFalse(PoQuantity.isValidPoQuantity("012")); // starts with 0
        assertFalse(PoQuantity.isValidPoQuantity("-213")); // negative number
        assertFalse(PoQuantity.isValidPoQuantity("Quantity")); // non-numeric
        assertFalse(PoQuantity.isValidPoQuantity("asd123")); // alphabets within digits
        assertFalse(PoQuantity.isValidPoQuantity("31 221")); // spaces within digits

        // valid PoQuantity numbers
        assertTrue(PoQuantity.isValidPoQuantity("121"));
        assertTrue(PoQuantity.isValidPoQuantity("12334441"));
        assertTrue(PoQuantity.isValidPoQuantity("9218391230983912")); // long PoQuantity numbers
    }
}
