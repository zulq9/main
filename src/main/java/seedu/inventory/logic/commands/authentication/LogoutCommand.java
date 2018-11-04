package seedu.inventory.logic.commands.authentication;

import static java.util.Objects.requireNonNull;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;

/**
 * Logs the user out from the system.
 */
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Log an user out from the system.";

    public static final String MESSAGE_SUCCESS = "You have successfully logged out from the system.\n"
            + "All commands history are cleared.";
    public static final String MESSAGE_FAILED = "You are not logged in.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isUserLoggedIn()) {
            throw new CommandException(MESSAGE_FAILED);
        }

        model.logoutUser();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof LogoutCommand;
    }
}
