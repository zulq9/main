package seedu.inventory.logic.commands.purchaseorder;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.purchaseorder.PurchaseOrder;


/**
 * Deletes a purchase order identified using it's displayed index from the inventory.
 */
public class DeletePurchaseOrderCommand extends Command {
    public static final String COMMAND_WORD = "delete-po";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the purchase order identified by the index number used in the displayed purchase order list.\n"
            + "Parameters: PURCHASE ORDER ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PURCHASE_ORDER_SUCCESS = "Deleted Purchase Order: %1$s";

    private final Index targetIndex;

    public DeletePurchaseOrderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<PurchaseOrder> lastShownList = model.getFilteredPurchaseOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PO_DISPLAYED_INDEX);
        }

        PurchaseOrder purchaseOrderToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePurchaseOrder(targetIndex.getZeroBased());
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_DELETE_PURCHASE_ORDER_SUCCESS, purchaseOrderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePurchaseOrderCommand // instanceof handles nulls
                && targetIndex.equals(((DeletePurchaseOrderCommand) other).targetIndex)); // state check
    }
}
