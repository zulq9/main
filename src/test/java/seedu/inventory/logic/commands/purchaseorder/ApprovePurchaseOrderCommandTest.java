package seedu.inventory.logic.commands.purchaseorder;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_SECOND_ITEM;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.testutil.ModelStub;
import seedu.inventory.testutil.TypicalItems;

public class ApprovePurchaseOrderCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelStubWithPurchaseOrder model = new ModelStubWithPurchaseOrder();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_invalidIndexUnfilteredPoList_throwsCommandException() throws Exception {
        Index invalidPoIndex = Index.fromOneBased(model.getFilteredPurchaseOrderList().size() + 1);
        ApprovePurchaseOrderCommand cmd = new ApprovePurchaseOrderCommand(invalidPoIndex);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PO_DISPLAYED_INDEX);
        cmd.execute(model, commandHistory);
    }

    @Test
    public void execute_nonPendingPurchaseOrder_throwsCommandException() throws Exception {
        ApprovePurchaseOrderCommand cmd = new ApprovePurchaseOrderCommand(INDEX_FIRST_ITEM);
        ApprovePurchaseOrderCommand secondCmd = new ApprovePurchaseOrderCommand(INDEX_FIRST_ITEM);
        cmd.execute(model, commandHistory);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_SELECT_PENDING);
        secondCmd.execute(model, commandHistory);
    }

    @Test
    public void execute_approvePurchaseOrder_successful() throws Exception {
        PurchaseOrder po = model.getFilteredPurchaseOrderList().get(INDEX_FIRST_ITEM.getZeroBased());
        CommandResult commandResult = new ApprovePurchaseOrderCommand(INDEX_FIRST_ITEM).execute(model, commandHistory);

        assertEquals(String.format(ApprovePurchaseOrderCommand.MESSAGE_APPROVE_PURCHASE_ORDER_SUCCESS, po),
                commandResult.feedbackToUser);
    }

    @Test
    public void equals() {
        ApprovePurchaseOrderCommand approveFirstCommand = new ApprovePurchaseOrderCommand(INDEX_FIRST_ITEM);
        ApprovePurchaseOrderCommand approveSecondCommand = new ApprovePurchaseOrderCommand(INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(approveFirstCommand.equals(approveFirstCommand));

        // same values -> returns true
        ApprovePurchaseOrderCommand approveFirstCommandCopy = new ApprovePurchaseOrderCommand(INDEX_FIRST_ITEM);
        assertTrue(approveFirstCommand.equals(approveFirstCommandCopy));

        // different types -> returns false
        assertFalse(approveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(approveFirstCommand.equals(null));

        // different purchase order -> returns false
        assertFalse(approveFirstCommand.equals(approveSecondCommand));
    }

    /**
     * A Model stub that containing items and purchase orders.
     */
    private class ModelStubWithPurchaseOrder extends ModelStub {
        private Inventory inventory;

        public ModelStubWithPurchaseOrder() {
            inventory = new Inventory(TypicalItems.getTypicalInventoryWithPo());
        }

        @Override
        public ReadOnlyInventory getInventory() {
            return inventory;
        }

        @Override
        public void approvePurchaseOrder(int target, PurchaseOrder purchaseOrder) {
            requireNonNull(purchaseOrder);
            inventory.approvePurchaseOrder(target, purchaseOrder);
        }

        @Override
        public void updateItem(Item target, Item editedItem) {
            requireAllNonNull(target, editedItem);
            inventory.updateItem(target, editedItem);
        }

        @Override
        public ObservableList<PurchaseOrder> getFilteredPurchaseOrderList() {
            return FXCollections.unmodifiableObservableList(inventory.getPurchaseOrderList());
        }

        @Override
        public void commitInventory() {
            // called by {@code ApprovePurchaseOrderCommand#execute()}
        }
    }

}
