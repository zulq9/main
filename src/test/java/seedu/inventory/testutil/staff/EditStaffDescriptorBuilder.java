package seedu.inventory.testutil.staff;

import seedu.inventory.logic.commands.staff.EditStaffCommand.EditStaffDescriptor;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.StaffName;
import seedu.inventory.model.staff.Username;

/**
 * An utility class to help with building EditStaffDescriptor objects.
 */
public class EditStaffDescriptorBuilder {
    private EditStaffDescriptor descriptor;

    public EditStaffDescriptorBuilder() {
        this.descriptor = new EditStaffDescriptor();
    }

    public EditStaffDescriptorBuilder(EditStaffDescriptor descriptor) {
        this.descriptor = new EditStaffDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditStaffDescriptor} with fields containing {@code item}'s details
     */
    public EditStaffDescriptorBuilder(Staff staff) {
        descriptor = new EditStaffDescriptor();
        descriptor.setName(staff.getStaffName());
        descriptor.setUsername(staff.getUsername());
        descriptor.setPassword(staff.getPassword());
        descriptor.setRole(staff.getRole());
    }

    /**
     * Sets the {@code StaffName} of the {@code EditStaffDescriptor} that we are building.
     */
    public EditStaffDescriptorBuilder withName(String name) {
        descriptor.setName(new StaffName(name));
        return this;
    }

    /**
     * Sets the {@code Username} of the {@code EditStaffDescriptor} that we are building.
     */
    public EditStaffDescriptorBuilder withUsername(String username) {
        descriptor.setUsername(new Username(username));
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code EditStaffDescriptor} that we are building.
     */
    public EditStaffDescriptorBuilder withPassword(String password) {
        descriptor.setPassword(new Password(password));
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code EditStaffDescriptor} that we are building.
     */
    public EditStaffDescriptorBuilder withRole(String role) {
        descriptor.setRole(Staff.Role.role(role));
        return this;
    }

    public EditStaffDescriptor build() {
        return descriptor;
    }
}
