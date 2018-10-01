package seedu.address.model.staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName(""));
        assertFalse(Name.isValidName(" "));
        assertFalse(Name.isValidName("*"));
        assertFalse(Name.isValidName("john..."));

        // valid name
        assertTrue(Name.isValidName("john doe"));
        assertTrue(Name.isValidName("Alexander the 2nd"));
        assertTrue(Name.isValidName("23123"));
        assertTrue(Name.isValidName("Alex Da Myth"));
        assertTrue(Name.isValidName("Mohammed Salah bin Abdullah Salleh"));
    }
}
