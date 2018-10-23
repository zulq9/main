package seedu.inventory.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.ui.testutil.GuiTestAssert.assertCardDisplaysSale;

import org.junit.Test;

import guitests.guihandles.SaleCardHandle;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;
import seedu.inventory.testutil.TypicalItems;
import seedu.inventory.ui.sale.SaleCard;

public class SaleCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Sale sale = new Sale(new SaleId("1"), TypicalItems.IPHONE, new Quantity("1"), new SaleDate("2018-08-01"));
        SaleCard saleCard = new SaleCard(sale);
        uiPartRule.setUiPart(saleCard);
        assertCardDisplay(saleCard, sale, 1);
    }

    @Test
    public void equals() {
        Sale sale = new Sale(new SaleId("1"), TypicalItems.IPHONE, new Quantity("1"), new SaleDate("2018-08-01"));
        SaleCard saleCard = new SaleCard(sale);

        // same item, same index -> returns true
        SaleCard copy = new SaleCard(sale);
        assertTrue(saleCard.equals(copy));

        // same object -> returns true
        assertTrue(saleCard.equals(saleCard));

        // null -> returns false
        assertFalse(saleCard.equals(null));

        // different types -> returns false
        assertFalse(saleCard.equals(0));
    }

    /**
     * Asserts that {@code itemCard} displays the details of {@code expectedItem} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(SaleCard saleCard, Sale expectedSale, int expectedId) {
        guiRobot.pauseForHuman();

        SaleCardHandle saleCardHandle = new SaleCardHandle(saleCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", saleCardHandle.getSaleId());

        // verify item details are displayed correctly
        assertCardDisplaysSale(expectedSale, saleCardHandle);
    }
}
