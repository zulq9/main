package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.inventory.commons.core.EventsCenter;
import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.commons.events.ui.JumpToItemListRequestEvent;
import seedu.inventory.commons.events.ui.JumpToPurchaseOrderListRequestEvent;
import seedu.inventory.commons.events.ui.JumpToSalesListRequestEvent;
import seedu.inventory.commons.events.ui.JumpToStaffListRequestEvent;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.staff.Staff;

/**
 * Selects an object identified using it's displayed index from the list.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the object identified by the index number used in the displayed list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_SUCCESS = "Selected: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Object> filteredAccessedList = model.getAccessedList();

        if (targetIndex.getZeroBased() >= filteredAccessedList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        String accessing = filteredAccessedList.get(0).getClass().getSimpleName();

        if (accessing.equals(Item.class.getSimpleName())) {
            EventsCenter.getInstance().post(new JumpToItemListRequestEvent(targetIndex));
        } else if (accessing.equals(Sale.class.getSimpleName())) {
            EventsCenter.getInstance().post(new JumpToSalesListRequestEvent(targetIndex));
        } else if (accessing.equals(Staff.class.getSimpleName())) {
            EventsCenter.getInstance().post(new JumpToStaffListRequestEvent(targetIndex));
        } else if (accessing.equals(PurchaseOrder.class.getSimpleName())) {
            EventsCenter.getInstance().post(new JumpToPurchaseOrderListRequestEvent(targetIndex));
        }

        return new CommandResult(String.format(MESSAGE_SELECT_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
