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
 * Rejects a purchase order identified using it's displayed index from the purchase order list.
 */
public class RejectPurchaseOrderCommand extends Command {
    public static final String COMMAND_WORD = "reject-po";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Rejects the purchaseOrder identified by the index number used in the displayed purchase order list.\n"
            + "Parameters: PURCHASE ORDER ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REJECT_PURCHASE_ORDER_SUCCESS = "Rejected Purchase Order: %1$s";

    private final Index targetIndex;

    public RejectPurchaseOrderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<PurchaseOrder> lastShownList = model.getFilteredPurchaseOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PO_DISPLAYED_INDEX);
        }

        PurchaseOrder purchaseOrderToReject = lastShownList.get(targetIndex.getZeroBased());

        if (!purchaseOrderToReject.getStatus().equals(PurchaseOrder.Status.PENDING)) {
            throw new CommandException(Messages.MESSAGE_SELECT_PENDING);
        }

        model.rejectPurchaseOrder(targetIndex.getZeroBased(), purchaseOrderToReject);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_REJECT_PURCHASE_ORDER_SUCCESS, purchaseOrderToReject));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RejectPurchaseOrderCommand // instanceof handles nulls
                && targetIndex.equals(((RejectPurchaseOrderCommand) other).targetIndex)); // state check
    }
}
