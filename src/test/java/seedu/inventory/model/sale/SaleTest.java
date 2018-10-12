package seedu.inventory.model.sale;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.testutil.Assert;
import seedu.inventory.testutil.TypicalItems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SaleTest {
    private static Item item = TypicalItems.IPHONE;
    private static Quantity quantity = new Quantity("1");
    private static SaleDate saleDate = new SaleDate("2018-08-01");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Sale(null, null, null));
    }

    @Test
    public void constructor_validSale() {
        Sale sale = new Sale(item, quantity, saleDate);
        assertNotNull(sale);
    }

    @Test
    public void isValidToString() {
        Sale sale = new Sale(item, quantity, saleDate);

        String expectedString = "[2018-08-01] 1x " + TypicalItems.IPHONE.getName();

        assertEquals(sale.toString(), expectedString);
    }
}
