package seedu.inventory.model.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class SkuTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Sku(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Sku(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> Sku.isValidEmail(null));

        // blank email
        assertFalse(Sku.isValidEmail("")); // empty string
        assertFalse(Sku.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Sku.isValidEmail("@example.com")); // missing local part
        assertFalse(Sku.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Sku.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Sku.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Sku.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Sku.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Sku.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Sku.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(Sku.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(Sku.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Sku.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Sku.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Sku.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Sku.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Sku.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Sku.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Sku.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(Sku.isValidEmail("a@bc")); // minimal
        assertTrue(Sku.isValidEmail("test@localhost")); // alphabets only
        assertTrue(Sku.isValidEmail("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Sku.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(Sku.isValidEmail("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Sku.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Sku.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
