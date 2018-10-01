package seedu.inventory.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static seedu.inventory.testutil.TypicalPersons.ALICE;
import static seedu.inventory.testutil.TypicalPersons.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.model.item.NameContainsKeywordsPredicate;
import seedu.inventory.testutil.AddressBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasItem(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasItem(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addItem(ALICE);
        assertTrue(modelManager.hasItem(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredItemList().remove(0);
    }

    @Test
    public void equals() {
        Inventory inventory = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        Inventory differentInventory = new Inventory();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(inventory, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(inventory, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different inventory -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentInventory, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredItemList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(inventory, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setInventoryFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(inventory, differentUserPrefs)));
    }
}
