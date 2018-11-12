package seedu.inventory.logic.commands.staff;

import static java.util.Objects.requireNonNull;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.model.Model;

/**
 * Lists all staffs in the inventory manager to the user.
 */
public class ListStaffCommand extends Command {

    public static final String COMMAND_WORD = "list-staff";

    public static final String MESSAGE_SUCCESS = "Listed all the staffs";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.viewStaff();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
