package seedu.inventory.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.inventory.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.inventory.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.UserPrefs;
import seedu.inventory.model.item.Item;
import seedu.inventory.testutil.EditPersonDescriptorBuilder;
import seedu.inventory.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Item editedItem = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedItem).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.updateItem(model.getFilteredPersonList().get(0), editedItem);
        expectedModel.commitInventory();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Item lastItem = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastItem);
        Item editedItem = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.updateItem(lastItem, editedItem);
        expectedModel.commitInventory();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Item editedItem = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.commitInventory();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Item itemInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Item editedItem = new PersonBuilder(itemInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.updateItem(model.getFilteredPersonList().get(0), editedItem);
        expectedModel.commitInventory();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Item firstItem = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstItem).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit item in filtered list into a duplicate in inventory book
        Item itemInList = model.getInventory().getItemList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(itemInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of inventory book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of inventory book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventory().getItemList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Item editedItem = new PersonBuilder().build();
        Item itemToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedItem).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.updateItem(itemToEdit, editedItem);
        expectedModel.commitInventory();

        // edit -> first item edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered item list to show all persons
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first item edited again
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> inventory book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        // single inventory book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Item} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited item in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the item object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Item editedItem = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedItem).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Item itemToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.updateItem(itemToEdit, editedItem);
        expectedModel.commitInventory();

        // edit -> edits second item in unfiltered item list / first item in filtered item list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered item list to show all persons
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), itemToEdit);
        // redo -> edits same second item in unfiltered item list
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
