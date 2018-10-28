package seedu.inventory.logic.commands.purchaseorder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.inventory.logic.commands.CommandTestUtil.DESC_OPPO_PO;
import static seedu.inventory.logic.commands.CommandTestUtil.DESC_SONY_PO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_QUANTITY_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_REQUIRED_DATE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SUPPLIER_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventoryWithPo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.ClearCommand;
import seedu.inventory.logic.commands.RedoCommand;
import seedu.inventory.logic.commands.UndoCommand;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.logic.commands.purchaseorder.EditPurchaseOrderCommand.EditPoDescriptor;

import seedu.inventory.model.Inventory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.UserPrefs;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.testutil.purchaseorder.EditPurchaseOrderDescriptorBuilder;
import seedu.inventory.testutil.purchaseorder.PurchaseOrderBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditPurchaseOrderCommand.
 */
public class EditPurchaseOrderCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalInventoryWithPo(), new UserPrefs(), new SaleList());

    @Test
    public void execute_invalidIndexUnfilteredPoList_throwsCommandException() throws Exception {
        Index invalidPoIndex = Index.fromOneBased(model.getFilteredPurchaseOrderList().size() + 1);
        EditPurchaseOrderCommand cmd = new EditPurchaseOrderCommand(invalidPoIndex, DESC_OPPO_PO);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PO_DISPLAYED_INDEX);
        cmd.execute(model, commandHistory);
    }

    @Test
    public void execute_nonPendingPurchaseOrder_throwsCommandException() throws Exception {
        ApprovePurchaseOrderCommand cmd = new ApprovePurchaseOrderCommand(INDEX_FIRST_ITEM);
        cmd.execute(model, commandHistory);
        EditPurchaseOrderCommand secondCmd = new EditPurchaseOrderCommand(INDEX_FIRST_ITEM, DESC_OPPO_PO);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_SELECT_PENDING);
        secondCmd.execute(model, commandHistory);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPo = Index.fromOneBased(model.getFilteredPurchaseOrderList().size());
        PurchaseOrder lastPo = model.getFilteredPurchaseOrderList().get(indexLastPo.getZeroBased());

        PurchaseOrderBuilder poInList = new PurchaseOrderBuilder(lastPo);
        PurchaseOrder editedPo = poInList.withQuantity(VALID_QUANTITY_SONY).withRequiredDate(VALID_REQUIRED_DATE_SONY)
                .withSupplier(VALID_SUPPLIER_SONY).build();

        EditPoDescriptor descriptor = new EditPurchaseOrderDescriptorBuilder()
                .withQuantity(VALID_QUANTITY_SONY).withRequiredDate(VALID_REQUIRED_DATE_SONY)
                .withSupplier(VALID_SUPPLIER_SONY).build();

        EditPurchaseOrderCommand editPoCommand = new EditPurchaseOrderCommand(indexLastPo, descriptor);

        String expectedMessage = String.format(EditPurchaseOrderCommand.MESSAGE_EDIT_PO_SUCCESS, editedPo);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs(), new SaleList());
        expectedModel.updatePurchaseOrder(lastPo, editedPo);
        expectedModel.commitInventory();

        assertCommandSuccess(editPoCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditPurchaseOrderCommand editPoCommand = new EditPurchaseOrderCommand(INDEX_FIRST_ITEM, new EditPoDescriptor());
        PurchaseOrder editedPo = model.getFilteredPurchaseOrderList().get(INDEX_FIRST_ITEM.getZeroBased());

        String expectedMessage = String.format(EditPurchaseOrderCommand.MESSAGE_EDIT_PO_SUCCESS, editedPo);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs(), new SaleList());
        expectedModel.commitInventory();

        assertCommandSuccess(editPoCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPurchaseOrderList().size() + 1);
        EditPurchaseOrderCommand.EditPoDescriptor descriptor =
                new EditPurchaseOrderDescriptorBuilder().withQuantity(VALID_QUANTITY_SONY).build();
        EditPurchaseOrderCommand editPoCommand = new EditPurchaseOrderCommand(outOfBoundIndex, descriptor);

        // execution failed -> inventory state not added into model
        assertCommandFailure(editPoCommand, model, commandHistory, Messages.MESSAGE_INVALID_PO_DISPLAYED_INDEX);

        // single inventory state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        final EditPurchaseOrderCommand standardCommand = new EditPurchaseOrderCommand(INDEX_FIRST_ITEM, DESC_OPPO_PO);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPurchaseOrderCommand(INDEX_SECOND_ITEM, DESC_OPPO_PO)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPurchaseOrderCommand(INDEX_FIRST_ITEM, DESC_SONY_PO)));
    }

}
