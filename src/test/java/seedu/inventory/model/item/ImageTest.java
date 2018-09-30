package seedu.inventory.model.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class ImageTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Image(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Image(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null inventory
        Assert.assertThrows(NullPointerException.class, () -> Image.isValidAddress(null));

        // invalid addresses
        assertFalse(Image.isValidAddress("")); // empty string
        assertFalse(Image.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Image.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Image.isValidAddress("-")); // one character
        assertTrue(Image.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long inventory
    }
}
