package seedu.inventory.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.ui.testutil.GuiTestAssert.assertCardDisplaysPurchaseOrder;

import org.junit.Test;

import guitests.guihandles.PurchaseOrderCardHandle;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.testutil.purchaseorder.PurchaseOrderBuilder;
import seedu.inventory.ui.purchaseorder.PurchaseOrderCard;

public class PurchaseOrderCardTest extends GuiUnitTest {

    @Test
    public void display() {
        PurchaseOrder po = new PurchaseOrderBuilder().build();
        PurchaseOrderCard poCard = new PurchaseOrderCard(po, 1);
        uiPartRule.setUiPart(poCard);
        assertCardDisplay(poCard, po, 1);
    }

    @Test
    public void equals() {
        PurchaseOrder po = new PurchaseOrderBuilder().build();
        PurchaseOrderCard poCard = new PurchaseOrderCard(po, 0);

        // same po, same index -> returns true
        PurchaseOrderCard copy = new PurchaseOrderCard(po, 0);
        assertTrue(poCard.equals(copy));

        // same object -> returns true
        assertTrue(poCard.equals(poCard));

        // null -> returns false
        assertFalse(poCard.equals(null));

        // different types -> returns false
        assertFalse(poCard.equals(0));

        // different PurchaseOrder, same index -> returns false
        PurchaseOrder differentPo = new PurchaseOrderBuilder().withSku("different").build();
        assertFalse(poCard.equals(new PurchaseOrderCard(differentPo, 0)));

        // same PurchaseOrder, different index -> returns false
        assertFalse(poCard.equals(new PurchaseOrderCard(po, 1)));
    }

    /**
     * Asserts that {@code poCard} displays the details of {@code expectedPo} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PurchaseOrderCard poCard, PurchaseOrder expectedPo, int expectedId) {
        guiRobot.pauseForHuman();

        PurchaseOrderCardHandle poCardHandle = new PurchaseOrderCardHandle(poCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", poCardHandle.getId());

        // verify PurchaseOrder details are displayed correctly
        assertCardDisplaysPurchaseOrder(expectedPo, poCardHandle);
    }
}
