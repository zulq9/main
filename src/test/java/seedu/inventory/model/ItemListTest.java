package seedu.inventory.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_IMAGE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_SMARTPHONE;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.NOKIA;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.exceptions.DuplicateItemException;
import seedu.inventory.testutil.ItemBuilder;

public class ItemListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ItemList itemList = new ItemList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), itemList.getItemList());
    }

    @Test
    public void resetItemList_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        itemList.resetItemList(null);
    }

    @Test
    public void resetItemList_withValidReadOnlyItemList_replacesData() {
        Inventory newData = getTypicalInventory();
        itemList.resetItemList(newData);
        assertEquals(new ItemList(newData), itemList);
    }

    ////===================== Item ==================================================================

    @Test
    public void resetItemList_withDuplicateItems_throwsDuplicateItemException() {
        // Two items with the same identity fields
        Item editedIphone = new ItemBuilder(IPHONE).withImage(VALID_IMAGE_SONY).withTags(VALID_TAG_SMARTPHONE)
                .build();
        List<Item> newItems = Arrays.asList(IPHONE, editedIphone);

        thrown.expect(DuplicateItemException.class);
        itemList.setItems(newItems);
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        itemList.hasItem(null);
    }

    @Test
    public void hasItem_itemNotInInventory_returnsFalse() {
        assertFalse(itemList.hasItem(IPHONE));
    }

    @Test
    public void hasItem_itemInInventory_returnsTrue() {
        itemList.addItem(IPHONE);
        assertTrue(itemList.hasItem(IPHONE));
    }

    @Test
    public void hasItem_itemWithSameIdentityFieldsInInventory_returnsTrue() {
        itemList.addItem(IPHONE);
        Item editedIphone = new ItemBuilder(IPHONE).withImage(VALID_IMAGE_SONY).withTags(VALID_TAG_SMARTPHONE)
                .build();
        assertTrue(itemList.hasItem(editedIphone));
    }

    @Test
    public void getItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        itemList.getItemList().remove(0);
    }

    @Test
    public void removeItem() {
        assertFalse(itemList.hasItem(IPHONE));
        itemList.addItem(IPHONE);
        assertTrue(itemList.hasItem(IPHONE));
        itemList.removeItem(IPHONE);
        assertFalse(itemList.hasItem(IPHONE));
    }

    @Test
    public void updateItem() {
        itemList.addItem(IPHONE);
        assertTrue(itemList.hasItem(IPHONE));
        assertFalse(itemList.hasItem(NOKIA));
        itemList.updateItem(IPHONE, NOKIA);
        assertFalse(itemList.hasItem(IPHONE));
        assertTrue(itemList.hasItem(NOKIA));
    }
}
