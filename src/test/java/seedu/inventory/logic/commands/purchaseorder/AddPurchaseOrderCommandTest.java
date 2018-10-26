package seedu.inventory.logic.commands.purchaseorder;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.testutil.ModelStub;
import seedu.inventory.testutil.TypicalItems;
import seedu.inventory.testutil.purchaseorder.PurchaseOrderBuilder;
import seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder;

public class AddPurchaseOrderCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPurchaseOrderCommand(null);
    }

    @Test
    public void execute_purchaseOrderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPurchaseOrderAdded modelStub = new ModelStubAcceptingPurchaseOrderAdded();

        PurchaseOrder validPurchaseOrder = new PurchaseOrderBuilder().build();

        CommandResult commandResult = new AddPurchaseOrderCommand(validPurchaseOrder)
                .execute(modelStub, commandHistory);

        assertEquals(String.format(AddPurchaseOrderCommand.MESSAGE_SUCCESS, validPurchaseOrder),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validPurchaseOrder), modelStub.purchaseOrdersAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        PurchaseOrder iPhone = TypicalPurchaseOrder.IPHONEPO;
        PurchaseOrder lg = TypicalPurchaseOrder.LGPO;
        AddPurchaseOrderCommand addIphoneCommand = new AddPurchaseOrderCommand(iPhone);
        AddPurchaseOrderCommand addLgCommand = new AddPurchaseOrderCommand(lg);

        // same object -> returns true
        assertTrue(addIphoneCommand.equals(addIphoneCommand));

        // same values -> returns true
        AddPurchaseOrderCommand addLgCommandCopy = new AddPurchaseOrderCommand(lg);
        assertTrue(addLgCommand.equals(addLgCommandCopy));

        // different types -> returns false
        assertFalse(addIphoneCommand.equals(1));

        // null -> returns false
        assertFalse(addIphoneCommand.equals(null));

        // different item -> returns false
        assertFalse(addIphoneCommand.equals(addLgCommand));
    }

    /**
     * A Model stub that contains a single item.
     */
    private class ModelStubWithPurchaseOrder extends ModelStub {
        private final PurchaseOrder purchaseOrder;

        ModelStubWithPurchaseOrder(PurchaseOrder purchaseOrder) {
            requireNonNull(purchaseOrder);
            this.purchaseOrder = purchaseOrder;
        }

        @Override
        public boolean hasPurchaseOrder(PurchaseOrder po) {
            requireNonNull(po);
            return this.purchaseOrder.isSameItem(po);
        }
    }

    /**
     * A Model stub that always accept the purchaseorder being added.
     */
    private class ModelStubAcceptingPurchaseOrderAdded extends ModelStub {
        private final ArrayList<PurchaseOrder> purchaseOrdersAdded = new ArrayList<>();

        @Override
        public ReadOnlyInventory getInventory() {
            return TypicalItems.getTypicalInventory();
        }

        @Override
        public boolean hasPurchaseOrder(PurchaseOrder purchaseOrder) {
            requireNonNull(purchaseOrder);
            return purchaseOrdersAdded.stream().anyMatch(purchaseOrder::isSameItem);
        }

        @Override
        public void addPurchaseOrder(PurchaseOrder purchaseOrder) {
            requireNonNull(purchaseOrder);
            purchaseOrdersAdded.add(purchaseOrder);
        }


        @Override
        public void commitInventory() {
            // called by {@code AddPurchaseOrderCommand#execute()}
        }
    }
}
