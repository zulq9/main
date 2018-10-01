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
        Assert.assertThrows(NullPointerException.class, () -> Sku.isValidSku(null));

        // blank email
        assertFalse(Sku.isValidSku("")); // empty string
        assertFalse(Sku.isValidSku(" ")); // spaces only

        // missing parts
        assertFalse(Sku.isValidSku("@example.com")); // missing local part
        assertFalse(Sku.isValidSku("peterjackexample.com")); // missing '@' symbol
        assertFalse(Sku.isValidSku("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Sku.isValidSku("peterjack@-")); // invalid domain name
        assertFalse(Sku.isValidSku("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Sku.isValidSku("peter jack@example.com")); // spaces in local part
        assertFalse(Sku.isValidSku("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Sku.isValidSku(" peterjack@example.com")); // leading space
        assertFalse(Sku.isValidSku("peterjack@example.com ")); // trailing space
        assertFalse(Sku.isValidSku("peterjack@@example.com")); // double '@' symbol
        assertFalse(Sku.isValidSku("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Sku.isValidSku("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Sku.isValidSku("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Sku.isValidSku("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Sku.isValidSku("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Sku.isValidSku("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Sku.isValidSku("PeterJack_1190@example.com"));
        assertTrue(Sku.isValidSku("a@bc")); // minimal
        assertTrue(Sku.isValidSku("test@localhost")); // alphabets only
        assertTrue(Sku.isValidSku("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Sku.isValidSku("123@145")); // numeric local part and domain name
        assertTrue(Sku.isValidSku("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Sku.isValidSku("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Sku.isValidSku("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
