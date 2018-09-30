package seedu.inventory.model.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class QuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Quantity(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Quantity.isValidQuantity(null));

        // invalid phone numbers
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only
        assertFalse(Quantity.isValidQuantity("91")); // less than 3 numbers
        assertFalse(Quantity.isValidQuantity("phone")); // non-numeric
        assertFalse(Quantity.isValidQuantity("9011p041")); // alphabets within digits
        assertFalse(Quantity.isValidQuantity("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Quantity.isValidQuantity("911")); // exactly 3 numbers
        assertTrue(Quantity.isValidQuantity("93121534"));
        assertTrue(Quantity.isValidQuantity("124293842033123")); // long phone numbers
    }
}
