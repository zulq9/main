package seedu.inventory.logic.commands.staff;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.inventory.model.Model.PREDICATE_SHOW_ALL_STAFFS;

import java.util.List;
import java.util.Optional;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.commons.util.CollectionUtil;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.StaffName;
import seedu.inventory.model.staff.Username;

/**
 * Edits the details of an existing staff in the inventory manager.
 */
public class EditStaffCommand extends Command {

    public static final String COMMAND_WORD = "edit-staff";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the staff identified "
            + "by the index number used in the displayed staff list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_USERNAME + "USERNAME] "
            + "[" + PREFIX_PASSWORD + "PASSWORD] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_ROLE + "ROLE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_USERNAME + "johnd "
            + PREFIX_ROLE + "user";

    public static final String MESSAGE_EDIT_STAFF_SUCCESS = "Edited Staff: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_STAFF = "This username already exists in the inventory manager";

    private final Index index;
    private final EditStaffDescriptor editStaffDescriptor;

    /**
     * Constructs the edit staff command with the required inputs.
     *
     * @param index of the staff in the filtered staff list to edit
     * @param editStaffDescriptor details to edit the staff with
     */
    public EditStaffCommand(Index index, EditStaffDescriptor editStaffDescriptor) {
        requireAllNonNull(index, editStaffDescriptor);

        this.index = index;
        this.editStaffDescriptor = new EditStaffDescriptor(editStaffDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Staff> lastShownList = model.getFilteredStaffList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
        }

        Staff staffToEdit = lastShownList.get(index.getZeroBased());
        Staff editedStaff = createEditedStaff(staffToEdit, editStaffDescriptor);

        if (!staffToEdit.isSameStaff(editedStaff) && model.hasUsername(editedStaff)) {
            throw new CommandException(MESSAGE_DUPLICATE_STAFF);
        }

        model.updateStaff(staffToEdit, editedStaff);
        Staff currentUser = model.getUser();

        if (staffToEdit.isSameStaff(currentUser)) {
            model.updateUserSession(editedStaff);
        }

        model.updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFFS);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_EDIT_STAFF_SUCCESS, editedStaff));
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
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditStaffCommand)) {
            return false;
        }

        EditStaffCommand e = (EditStaffCommand) other;
        return index.equals(e.index)
                && editStaffDescriptor.equals(e.editStaffDescriptor);
    }

    /**
     * Stores the details to edit the staff with. Each non-empty field value will replace the
     * corresponding field value of the staff.
     */
    public static class EditStaffDescriptor {
        private StaffName name;
        private Username username;
        private Password password;
        private Staff.Role role;

        public EditStaffDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditStaffDescriptor(EditStaffDescriptor toCopy) {
            setName(toCopy.name);
            setUsername(toCopy.username);
            setPassword(toCopy.password);
            setRole(toCopy.role);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, username, password, role);
        }

        public void setName(StaffName name) {
            this.name = name;
        }

        public Optional<StaffName> getName() {
            return Optional.ofNullable(name);
        }

        public void setUsername(Username username) {
            this.username = username;
        }

        public Optional<Username> getUsername() {
            return Optional.ofNullable(username);
        }

        public void setPassword(Password password) {
            this.password = password;
        }

        public Optional<Password> getPassword() {
            return Optional.ofNullable(password);
        }

        public void setRole(Staff.Role role) {
            this.role = role;
        }

        public Optional<Staff.Role> getRole() {
            return Optional.ofNullable(role);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditStaffDescriptor)) {
                return false;
            }

            // state check
            EditStaffDescriptor e = (EditStaffDescriptor) other;

            return getName().equals(e.getName())
                    && getUsername().equals(e.getUsername())
                    && getPassword().equals(e.getPassword())
                    && getRole().equals(e.getRole());
        }
    }
}
