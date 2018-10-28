package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Model;

/**
 * Lists all items in the inventory that have quantities less than or equal to 10 to the user.
 */
public class ListLowQuantityCommand extends Command {

    public static final String COMMAND_WORD = "list-low-qty";

    public static final String MESSAGE_SUCCESS = "Listed all items with low quantities";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.viewLowQuantity();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
