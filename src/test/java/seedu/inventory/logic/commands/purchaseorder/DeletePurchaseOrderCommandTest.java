package seedu.inventory.logic.commands.purchaseorder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventoryWithPo;

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
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeletePurchaseOrderCommand}.
 */
public class DeletePurchaseOrderCommandTest {

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalInventoryWithPo(), new UserPrefs(), new SaleList());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        PurchaseOrder purchaseOrderToDelete = model.getFilteredPurchaseOrderList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeletePurchaseOrderCommand deletePurchaseOrderCommand = new DeletePurchaseOrderCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeletePurchaseOrderCommand.MESSAGE_DELETE_PURCHASE_ORDER_SUCCESS,
                purchaseOrderToDelete);

        ModelManager expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList());
        expectedModel.deletePurchaseOrder(INDEX_FIRST_ITEM.getZeroBased());
        expectedModel.commitInventory();

        assertCommandSuccess(deletePurchaseOrderCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPurchaseOrderList().size() + 1);
        DeletePurchaseOrderCommand deletePurchaseOrderCommand = new DeletePurchaseOrderCommand(outOfBoundIndex);

        assertCommandFailure(deletePurchaseOrderCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PO_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        PurchaseOrder purchaseOrderToDelete = model.getFilteredPurchaseOrderList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeletePurchaseOrderCommand deletePurchaseOrderCommand = new DeletePurchaseOrderCommand(INDEX_FIRST_ITEM);
        Model expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList());
        expectedModel.deletePurchaseOrder(INDEX_FIRST_ITEM.getZeroBased());
        expectedModel.commitInventory();

        // delete -> first PurchaseOrder deleted
        deletePurchaseOrderCommand.execute(model, commandHistory);

        // undo -> reverts inventory back to previous state and filtered PurchaseOrder list to show all PurchaseOrders
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first PurchaseOrder deleted again
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPurchaseOrderList().size() + 1);
        DeletePurchaseOrderCommand deletePurchaseOrderCommand = new DeletePurchaseOrderCommand(outOfBoundIndex);

        // execution failed -> inventory state not added into model
        assertCommandFailure(deletePurchaseOrderCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PO_DISPLAYED_INDEX);

        // single inventory state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }


    @Test
    public void equals() {
        DeletePurchaseOrderCommand deleteFirstCommand = new DeletePurchaseOrderCommand(INDEX_FIRST_ITEM);
        DeletePurchaseOrderCommand deleteSecondCommand = new DeletePurchaseOrderCommand(INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePurchaseOrderCommand deleteFirstCommandCopy = new DeletePurchaseOrderCommand(INDEX_FIRST_ITEM);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different PurchaseOrder -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
