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
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.purchaseorder.PurchaseOrder;


/**
 * Approves a purchase order identified using it's displayed index from the purchase order list.
 */
public class ApprovePurchaseOrderCommand extends Command {
    public static final String COMMAND_WORD = "approve-po";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Approves the purchaseOrder identified by the index number used in the displayed purchase order list.\n"
            + "Parameters: PURCHASE ORDER ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_APPROVE_PURCHASE_ORDER_SUCCESS = "Approved Purchase Order: %1$s";

    private final Index targetIndex;

    public ApprovePurchaseOrderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<PurchaseOrder> lastShownList = model.getFilteredPurchaseOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PO_DISPLAYED_INDEX);
        }

        PurchaseOrder purchaseOrderToApprove = lastShownList.get(targetIndex.getZeroBased());

        if (!purchaseOrderToApprove.getStatus().equals(PurchaseOrder.Status.PENDING)) {
            throw new CommandException(Messages.MESSAGE_SELECT_PENDING);
        }

        model.approvePurchaseOrder(targetIndex.getZeroBased(), purchaseOrderToApprove);

        Item item = model.getInventory().getItemBySku(purchaseOrderToApprove.getSku().value);
        int newIntQuantity = Integer.parseInt(item.getQuantity().toString())
                + Integer.parseInt(purchaseOrderToApprove.getQuantity().toString());
        Quantity newQuantity = new Quantity(Integer.toString(newIntQuantity));

        model.updateItem(item, new Item(item.getName(), item.getPrice(), newQuantity, item.getSku(),
                item.getImage(), item.getTags()));

        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_APPROVE_PURCHASE_ORDER_SUCCESS, purchaseOrderToApprove));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ApprovePurchaseOrderCommand // instanceof handles nulls
                && targetIndex.equals(((ApprovePurchaseOrderCommand) other).targetIndex)); // state check
    }
}
