package seedu.inventory.logic.commands.authentication;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.logic.commands.staff.EditStaffCommand.EditStaffDescriptor;
import seedu.inventory.model.Model;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.StaffName;
import seedu.inventory.model.staff.Username;

/**
 * Changes the password of the current user.
 */
public class ChangePasswordCommand extends Command {


    public static final String COMMAND_WORD = "change-password";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the password of the current user."
            + "Parameter: "
            + PREFIX_PASSWORD + "PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PASSWORD + "12345678";

    public static final String MESSAGE_SUCCESS = "You have successfully changed your password.";
    public static final String MESSAGE_FAILED = "Please enter a new password "
            + "which is differ from your current password.";
    public static final String MESSAGE_USER_NOT_LOGGED_IN = "Please login to change your password.";

    private final Password toChange;

    public ChangePasswordCommand(Password password) {
        requireNonNull(password);
        toChange = password;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isUserLoggedIn()) {
            throw new CommandException(MESSAGE_USER_NOT_LOGGED_IN);
        }

        Staff user = model.getUser();
        if (user.getPassword().equals(toChange)) {
            throw new CommandException(MESSAGE_FAILED);
        }

        Staff staff = model.retrieveStaff(user);
        EditStaffDescriptor descriptor = new EditStaffDescriptor();
        descriptor.setPassword(toChange);
        Staff editedStaff = createEditedStaff(staff, descriptor);

        model.updateStaff(staff, editedStaff);
        model.updateUserSession(editedStaff);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, staff.getStaffName()));
    }

    /**
     * Creates and returns a {@code Staff} with the details of {@code staffToEdit}
     * edited with {@code editStaffDescriptor}.
     */
    private static Staff createEditedStaff(Staff staffToEdit, EditStaffDescriptor editStaffDescriptor) {
        requireNonNull(staffToEdit);

        StaffName updatedName = editStaffDescriptor.getName().orElse(staffToEdit.getStaffName());
        Username updatedUsername = editStaffDescriptor.getUsername().orElse(staffToEdit.getUsername());
        Password updatedPassword = editStaffDescriptor.getPassword().orElse(staffToEdit.getPassword());
        Staff.Role updatedRole = editStaffDescriptor.getRole().orElse(staffToEdit.getRole());

        return new Staff(updatedUsername, updatedPassword, updatedName, updatedRole);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangePasswordCommand // instanceof handles nulls
                && toChange.equals(((ChangePasswordCommand) other).toChange));
    }
}
