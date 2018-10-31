package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.Model;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.util.SampleDataUtil;

/**
 * Clears the inventory.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Inventory, sale orders and purchase orders have been cleared! Staff list has been reset.";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new Inventory());
        model.resetStaffList(SampleDataUtil.getSampleStaffList());
        model.resetSaleList(new SaleList());
        model.commitInventory();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
