package seedu.inventory.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_IMAGE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_SMARTPHONE;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.exceptions.DuplicateItemException;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.testutil.ItemBuilder;

public class InventoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Inventory inventory = new Inventory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), inventory.getItemList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        inventory.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyInventory_replacesData() {
        Inventory newData = getTypicalInventory();
        inventory.resetData(newData);
        assertEquals(newData, inventory);
    }

    @Test
    public void resetData_withDuplicateItems_throwsDuplicatePersonException() {
        // Two items with the same identity fields
        Item editedAlice = new ItemBuilder(IPHONE).withImage(VALID_IMAGE_SONY).withTags(VALID_TAG_SMARTPHONE)
                .build();
        List<Item> newItems = Arrays.asList(IPHONE, editedAlice);
        InventoryStub newData = new InventoryStub(newItems);

        thrown.expect(DuplicateItemException.class);
        inventory.resetData(newData);
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        inventory.hasItem(null);
    }

    @Test
    public void hasItem_itemNotInInventory_returnsFalse() {
        assertFalse(inventory.hasItem(IPHONE));
    }

    @Test
    public void hasItem_itemInInventory_returnsTrue() {
        inventory.addItem(IPHONE);
        assertTrue(inventory.hasItem(IPHONE));
    }

    @Test
    public void hasItem_itemWithSameIdentityFieldsInInventory_returnsTrue() {
        inventory.addItem(IPHONE);
        Item editedAlice = new ItemBuilder(IPHONE).withImage(VALID_IMAGE_SONY).withTags(VALID_TAG_SMARTPHONE)
                .build();
        assertTrue(inventory.hasItem(editedAlice));
    }

    @Test
    public void getItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        inventory.getItemList().remove(0);
    }

    /**
     * A stub ReadOnlyInventory whose items list can violate interface constraints.
     */
    private static class InventoryStub implements ReadOnlyInventory {
        private final ObservableList<Item> items = FXCollections.observableArrayList();
        private final ObservableList<PurchaseOrder> purchaseOrders = FXCollections.observableArrayList();

        InventoryStub(Collection<Item> items, Collection<PurchaseOrder> purchaseOrders) {
            this.items.setAll(items);
            this.purchaseOrders.setAll(purchaseOrders);
        }

        InventoryStub(Collection<Item> items) {
            this.items.setAll(items);
        }

        @Override
        public ObservableList<Item> getItemList() {
            return items;
        }

        @Override
        public ObservableList<PurchaseOrder> getPurchaseOrderList() {
            return purchaseOrders;
        }
    }

}
