package seedu.inventory.logic.commands.staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.logic.commands.CommandTestUtil.showStaffAtIndex;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import org.junit.Test;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.RedoCommand;
import seedu.inventory.logic.commands.UndoCommand;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.UserPrefs;
import seedu.inventory.model.staff.Staff;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteStaffCommand}.
 */
public class DeleteStaffCommandTest {

    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_ValidIndexUnfilteredList_success() {
        Staff staffToDelete = model.getFilteredStaffList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteStaffCommand deleteStaffCommand = new DeleteStaffCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteStaffCommand.MESSAGE_DELETE_STAFF_SUCCESS, staffToDelete);

        ModelManager expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList());
        expectedModel.deleteStaff(staffToDelete);
        expectedModel.commitInventory();

        assertCommandSuccess(deleteStaffCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        DeleteStaffCommand deleteStaffCommand = new DeleteStaffCommand(outOfBoundIndex);

        assertCommandFailure(deleteStaffCommand, model, commandHistory, Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showStaffAtIndex(model, INDEX_FIRST_ITEM);

        Staff staffToDelete = model.getFilteredStaffList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteStaffCommand deleteStaffCommand = new DeleteStaffCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteStaffCommand.MESSAGE_DELETE_STAFF_SUCCESS, staffToDelete);

        Model expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList());
        expectedModel.deleteStaff(staffToDelete);
        expectedModel.commitInventory();
        showNoStaff(expectedModel);

        assertCommandSuccess(deleteStaffCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showStaffAtIndex(model, INDEX_FIRST_ITEM);

        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of inventory list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventory().getStaffList().size());

        DeleteStaffCommand deleteStaffCommand = new DeleteStaffCommand(outOfBoundIndex);

        assertCommandFailure(deleteStaffCommand, model, commandHistory, Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Staff staffToDelete = model.getFilteredStaffList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteStaffCommand deleteStaffCommand = new DeleteStaffCommand(INDEX_FIRST_ITEM);
        Model expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList());
        expectedModel.deleteStaff(staffToDelete);
        expectedModel.commitInventory();

        // delete -> first item deleted
        deleteStaffCommand.execute(model, commandHistory);

        // undo -> reverts inventory back to previous state and filtered item list to show all items
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first item deleted again
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStaffList().size() + 1);
        DeleteStaffCommand deleteStaffCommand = new DeleteStaffCommand(outOfBoundIndex);

        // execution failed -> inventory state not added into model
        assertCommandFailure(deleteStaffCommand, model, commandHistory, Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);

        // single inventory state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Staff} from a filtered staff list.
     * 2. Undo the deletion.
     * 3. The unfiltered staff list should be shown now. Verify that the index of the previously deleted staff in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the staff object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameStaffDeleted() throws Exception {
        DeleteStaffCommand deleteStaffCommand = new DeleteStaffCommand(INDEX_FIRST_ITEM);
        Model expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList());

        showStaffAtIndex(model, INDEX_SECOND_ITEM);
        Staff staffToDelete = model.getFilteredStaffList().get(INDEX_FIRST_ITEM.getZeroBased());
        expectedModel.deleteStaff(staffToDelete);
        expectedModel.commitInventory();

        // delete -> deletes second staff in unfiltered staff list / first staff in filtered staff list
        deleteStaffCommand.execute(model, commandHistory);

        // undo -> reverts inventory back to previous state and filtered staff list to show all staffs
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(staffToDelete, model.getFilteredStaffList().get(INDEX_FIRST_ITEM.getZeroBased()));
        // redo -> deletes same second staff in unfiltered staff list
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteStaffCommand deleteFirstStaffCommand = new DeleteStaffCommand(INDEX_FIRST_ITEM);
        DeleteStaffCommand deleteSecondStaffCommand = new DeleteStaffCommand(INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(deleteFirstStaffCommand.equals(deleteFirstStaffCommand));

        // same values -> returns true
        DeleteStaffCommand deleteFirstStaffCommandCopy = new DeleteStaffCommand(INDEX_FIRST_ITEM);
        assertTrue(deleteFirstStaffCommand.equals(deleteFirstStaffCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstStaffCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstStaffCommand.equals(null));

        // different item -> returns false
        assertFalse(deleteFirstStaffCommand.equals(deleteSecondStaffCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoStaff(Model model) {
        model.updateFilteredStaffList(p -> false);

        assertTrue(model.getFilteredStaffList().isEmpty());
    }
}
