package seedu.inventory.logic.commands.staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.DESC_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.DESC_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PASSWORD_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_USERNAME_USER;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.logic.commands.CommandTestUtil.showStaffAtIndex;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventoryWithStaff;

import org.junit.Test;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.ClearCommand;
import seedu.inventory.logic.commands.RedoCommand;
import seedu.inventory.logic.commands.UndoCommand;
import seedu.inventory.logic.commands.staff.EditStaffCommand.EditStaffDescriptor;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.UserPrefs;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.testutil.staff.EditStaffDescriptorBuilder;
import seedu.inventory.testutil.staff.StaffBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditStaffCommand.
 */
public class EditStaffCommandTest {

    private Model model = new ModelManager(getTypicalInventoryWithStaff(), new UserPrefs(), new SaleList());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Staff editedStaff = new StaffBuilder().build();
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder(editedStaff).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_FIRST_ITEM, descriptor);

        String expectedMessage = String.format(EditStaffCommand.MESSAGE_EDIT_STAFF_SUCCESS, editedStaff);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs(), new SaleList());
        expectedModel.updateStaff(model.getFilteredStaffList().get(0), editedStaff);
        expectedModel.commitInventory();

        assertCommandSuccess(editStaffCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastStaff = Index.fromOneBased(model.getFilteredStaffList().size());
        Staff lastStaff = model.getFilteredStaffList().get(indexLastStaff.getZeroBased());

        StaffBuilder staffInList = new StaffBuilder(lastStaff);
        Staff editedStaff = staffInList.withUsername(VALID_USERNAME_USER).withName(VALID_NAME_DARREN)
                .withPassword(VALID_PASSWORD_DARREN).build();

        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder().withUsername(VALID_USERNAME_USER)
                .withName(VALID_NAME_DARREN).withPassword(VALID_PASSWORD_DARREN).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(indexLastStaff, descriptor);

        String expectedMessage = String.format(EditStaffCommand.MESSAGE_EDIT_STAFF_SUCCESS, editedStaff);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs(), new SaleList());
        expectedModel.updateStaff(lastStaff, editedStaff);
        expectedModel.commitInventory();

        assertCommandSuccess(editStaffCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execcute_noFieldSpecifiedUnfilteredList_success() {
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_FIRST_ITEM, new EditStaffDescriptor());
        Staff editedStaff = model.getFilteredStaffList().get(INDEX_FIRST_ITEM.getZeroBased());

        String expectedMessage = String.format(EditStaffCommand.MESSAGE_EDIT_STAFF_SUCCESS, editedStaff);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs(), new SaleList());
        expectedModel.commitInventory();

        assertCommandSuccess(editStaffCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showStaffAtIndex(model, INDEX_FIRST_ITEM);

        Staff staffInFilteredList = model.getFilteredStaffList().get(INDEX_FIRST_ITEM.getZeroBased());
        Staff editedStaff = new StaffBuilder(staffInFilteredList).withName(VALID_NAME_DARREN).build();
        EditStaffCommand editItemCommand = new EditStaffCommand(INDEX_FIRST_ITEM,
                new EditStaffDescriptorBuilder().withName(VALID_NAME_DARREN).build());

        String expectedMessage = String.format(EditStaffCommand.MESSAGE_EDIT_STAFF_SUCCESS, editedStaff);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs(), new SaleList());
        expectedModel.updateStaff(model.getFilteredStaffList().get(0), editedStaff);
        expectedModel.commitInventory();

        assertCommandSuccess(editItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateStaffUnfilteredList_failure() {
        Staff firstStaff = model.getFilteredStaffList().get(INDEX_FIRST_ITEM.getZeroBased());
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder(firstStaff).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_SECOND_ITEM, descriptor);

        assertCommandFailure(editStaffCommand, model, commandHistory, EditStaffCommand.MESSAGE_DUPLICATE_STAFF);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStaffList().size() + 1);
        EditStaffDescriptor descriptor =
                new EditStaffDescriptorBuilder().withName(VALID_NAME_DARREN).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editStaffCommand, model, commandHistory, Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of stafflist
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showStaffAtIndex(model, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of inventory list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventory().getStaffList().size());

        EditStaffCommand editStaffCommand = new EditStaffCommand(outOfBoundIndex,
                new EditStaffDescriptorBuilder().withName(VALID_NAME_DARREN).build());

        assertCommandFailure(editStaffCommand, model, commandHistory, Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Staff editedStaff = new StaffBuilder().build();
        Staff staffToEdit = model.getFilteredStaffList().get(INDEX_FIRST_ITEM.getZeroBased());
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder(editedStaff).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_FIRST_ITEM, descriptor);
        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs(), new SaleList());
        expectedModel.updateStaff(staffToEdit, editedStaff);
        expectedModel.commitInventory();

        // edit -> first staff edited
        editStaffCommand.execute(model, commandHistory);

        // undo -> reverts inventory back to previous state and filtered staff list to show all staffs
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first item edited again
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStaffList().size() + 1);
        EditStaffDescriptor descriptor =
                new EditStaffDescriptorBuilder().withName(VALID_NAME_DARREN).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(outOfBoundIndex, descriptor);

        // execution failed -> inventory state not added into model
        assertCommandFailure(editStaffCommand, model, commandHistory, Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);

        // single inventory state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Staff} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited staff in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the staff object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameItemEdited() throws Exception {
        Staff editedStaff = new StaffBuilder().build();
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder(editedStaff).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_FIRST_ITEM, descriptor);
        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs(), new SaleList());

        showStaffAtIndex(model, INDEX_SECOND_ITEM);
        Staff staffToEdit = model.getFilteredStaffList().get(INDEX_FIRST_ITEM.getZeroBased());
        expectedModel.updateStaff(staffToEdit, editedStaff);
        expectedModel.commitInventory();

        // edit -> edits second item in unfiltered staff list / first staff in filtered staff list
        editStaffCommand.execute(model, commandHistory);

        // undo -> reverts inventory back to previous state and filtered item list to show all items
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredStaffList().get(INDEX_FIRST_ITEM.getZeroBased()), staffToEdit);
        // redo -> edits same second item in unfiltered item list
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditStaffCommand standardCommand = new EditStaffCommand(INDEX_FIRST_ITEM, DESC_ZUL);

        // same values -> returns true
        EditStaffDescriptor copyDescriptor = new EditStaffDescriptor(DESC_ZUL);
        EditStaffCommand commandWithSameValues = new EditStaffCommand(INDEX_FIRST_ITEM, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditStaffCommand(INDEX_SECOND_ITEM, DESC_ZUL)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditStaffCommand(INDEX_FIRST_ITEM, DESC_DARREN)));
    }
}
