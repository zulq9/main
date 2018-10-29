package seedu.inventory.logic.commands.item;

import static java.util.Objects.requireNonNull;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.model.Model;

/**
 * Lists all items in the inventory to the user.
 */
public class ListItemCommand extends Command {

    public static final String COMMAND_WORD = "list-item";

    public static final String MESSAGE_SUCCESS = "Listed all items";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.viewItem();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
