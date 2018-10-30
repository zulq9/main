package seedu.inventory.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ItemCardHandle;
import guitests.guihandles.ItemListPanelHandle;
import guitests.guihandles.PurchaseOrderCardHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.SaleCardHandle;
import guitests.guihandles.StaffCardHandle;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.staff.Staff;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(ItemCardHandle expectedCard, ItemCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getImage(), actualCard.getImage());
        assertEquals(expectedCard.getSku(), actualCard.getSku());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getQuantity(), actualCard.getQuantity());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(StaffCardHandle expectedCard, StaffCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getUsername(), actualCard.getUsername());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getRole(), actualCard.getRole());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PurchaseOrderCardHandle expectedCard, PurchaseOrderCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getSku(), actualCard.getSku());
        assertEquals(expectedCard.getQuantity(), actualCard.getQuantity());
        assertEquals(expectedCard.getReqDate(), actualCard.getReqDate());
        assertEquals(expectedCard.getSupplier(), actualCard.getSupplier());
        assertEquals(expectedCard.getStatus(), actualCard.getStatus());

    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedItem}.
     */
    public static void assertCardDisplaysItem(Item expectedItem, ItemCardHandle actualCard) {
        assertEquals(expectedItem.getName().fullName, actualCard.getName());
        assertEquals(expectedItem.getQuantity().value, actualCard.getQuantity());
        assertEquals(expectedItem.getSku().value, actualCard.getSku());
        assertEquals(expectedItem.getImage().value, actualCard.getImage());
        assertEquals(expectedItem.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedSale}.
     */
    public static void assertCardDisplaysSale(Sale expectedSale, SaleCardHandle actualCard) {
        assertEquals(expectedSale.getSaleDate().toString(), actualCard.getSaleDate());
        assertEquals("$" + expectedSale.getItem().getPrice().toString(), actualCard.getPrice());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedStaff}.
     */
    public static void assertCardDisplaysStaff(Staff expectedStaff, StaffCardHandle actualCard) {
        assertEquals(expectedStaff.getUsername().username, actualCard.getUsername());
        assertEquals(expectedStaff.getStaffName().fullName, actualCard.getName());
        assertEquals(expectedStaff.getRole().toString(), actualCard.getRole());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPo}.
     */
    public static void assertCardDisplaysPurchaseOrder(PurchaseOrder expectedPo, PurchaseOrderCardHandle actualCard) {
        assertEquals(expectedPo.getSku().value, actualCard.getSku());
        assertEquals(expectedPo.getQuantity().value, actualCard.getQuantity());
        assertEquals(expectedPo.getReqDate().requiredDate, actualCard.getReqDate());
        assertEquals(expectedPo.getSupplier().supplierName, actualCard.getSupplier());
        assertEquals(expectedPo.getStatus().name(), actualCard.getStatus());

    }

    /**
     * Asserts that the list in {@code itemListPanelHandle} displays the details of {@code items} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ItemListPanelHandle itemListPanelHandle, Item... items) {
        for (int i = 0; i < items.length; i++) {
            itemListPanelHandle.navigateToCard(i);
            assertCardDisplaysItem(items[i], itemListPanelHandle.getItemCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code itemListPanelHandle} displays the details of {@code items} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ItemListPanelHandle itemListPanelHandle, List<Item> items) {
        assertListMatching(itemListPanelHandle, items.toArray(new Item[0]));
    }

    /**
     * Asserts the size of the list in {@code itemListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(ItemListPanelHandle itemListPanelHandle, int size) {
        int numberOfPeople = itemListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
