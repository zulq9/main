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

public class RejectPurchaseOrderCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelStubWithPurchaseOrder model = new ModelStubWithPurchaseOrder();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_invalidIndexUnfilteredPoList_throwsCommandException() throws Exception {
        Index invalidPoIndex = Index.fromOneBased(model.getFilteredPurchaseOrderList().size() + 1);
        RejectPurchaseOrderCommand cmd = new RejectPurchaseOrderCommand(invalidPoIndex);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PO_DISPLAYED_INDEX);
        cmd.execute(model, commandHistory);
    }

    @Test
    public void execute_nonPendingPurchaseOrder_throwsCommandException() throws Exception {
        RejectPurchaseOrderCommand cmd = new RejectPurchaseOrderCommand(INDEX_FIRST_ITEM);
        RejectPurchaseOrderCommand secondCmd = new RejectPurchaseOrderCommand(INDEX_FIRST_ITEM);
        cmd.execute(model, commandHistory);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_SELECT_PENDING);
        secondCmd.execute(model, commandHistory);
    }

    @Test
    public void execute_rejectPurchaseOrder_successful() throws Exception {
        PurchaseOrder po = model.getFilteredPurchaseOrderList().get(INDEX_FIRST_ITEM.getZeroBased());
        CommandResult commandResult = new RejectPurchaseOrderCommand(INDEX_FIRST_ITEM).execute(model, commandHistory);

        assertEquals(String.format(RejectPurchaseOrderCommand.MESSAGE_REJECT_PURCHASE_ORDER_SUCCESS, po),
                commandResult.feedbackToUser);
    }

    @Test
    public void equals() {
        RejectPurchaseOrderCommand rejectFirstCommand = new RejectPurchaseOrderCommand(INDEX_FIRST_ITEM);
        RejectPurchaseOrderCommand rejectSecondCommand = new RejectPurchaseOrderCommand(INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(rejectFirstCommand.equals(rejectFirstCommand));

        // same values -> returns true
        RejectPurchaseOrderCommand rejectFirstCommandCopy = new RejectPurchaseOrderCommand(INDEX_FIRST_ITEM);
        assertTrue(rejectFirstCommand.equals(rejectFirstCommandCopy));

        // different types -> returns false
        assertFalse(rejectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(rejectFirstCommand.equals(null));

        // different purchase order -> returns false
        assertFalse(rejectFirstCommand.equals(rejectSecondCommand));
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
        public void rejectPurchaseOrder(PurchaseOrder purchaseOrder) {
            requireNonNull(purchaseOrder);
            inventory.rejectPurchaseOrder(purchaseOrder);
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
            // called by {@code RejectPurchaseOrderCommand#execute()}
        }
    }

}
