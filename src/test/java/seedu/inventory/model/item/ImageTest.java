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
        Assert.assertThrows(NullPointerException.class, () -> Image.isValidImage(null));

        // invalid addresses
        assertFalse(Image.isValidImage("")); // empty string
        assertFalse(Image.isValidImage(" ")); // spaces only

        // valid addresses
        assertTrue(Image.isValidImage("docs/images/iphone.jpg"));
        assertTrue(Image.isValidImage("docs/images/yao-tx.png")); // one character
    }
}
