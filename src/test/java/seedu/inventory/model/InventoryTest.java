package seedu.inventory.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.inventory.testutil.TypicalItems.ALICE;
import static seedu.inventory.testutil.TypicalItems.getTypicalAddressBook;

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
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        Inventory newData = getTypicalAddressBook();
        inventory.resetData(newData);
        assertEquals(newData, inventory);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two items with the same identity fields
        Item editedAlice = new ItemBuilder(ALICE).withImage(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Item> newItems = Arrays.asList(ALICE, editedAlice);
        InventoryStub newData = new InventoryStub(newItems);

        thrown.expect(DuplicateItemException.class);
        inventory.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        inventory.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(inventory.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        inventory.addPerson(ALICE);
        assertTrue(inventory.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        inventory.addPerson(ALICE);
        Item editedAlice = new ItemBuilder(ALICE).withImage(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(inventory.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        inventory.getItemList().remove(0);
    }

    /**
     * A stub ReadOnlyInventory whose items list can violate interface constraints.
     */
    private static class InventoryStub implements ReadOnlyInventory {
        private final ObservableList<Item> items = FXCollections.observableArrayList();

        InventoryStub(Collection<Item> items) {
            this.items.setAll(items);
        }

        @Override
        public ObservableList<Item> getItemList() {
            return items;
        }
    }

}
