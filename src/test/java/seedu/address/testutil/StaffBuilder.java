package seedu.address.testutil;

import seedu.address.model.staff.Name;
import seedu.address.model.staff.Password;
import seedu.address.model.staff.Staff;
import seedu.address.model.staff.Username;

/**
 * An utility class to help with building Staff objects.
 */
public class StaffBuilder {

    public static final String DEFAULT_USERNAME = "johnd";
    public static final String DEFAULT_PASSWORD = "johndoe1";
    public static final String DEFAULT_NAME = "John Doe";
    public static final Staff.Role DEFAULT_ROLE = Staff.Role.user;

    private Username username;
    private Password password;
    private Name name;
    private Staff.Role role;

    public StaffBuilder() {
        username = new Username(DEFAULT_USERNAME);
        password = new Password(DEFAULT_PASSWORD);
        name = new Name(DEFAULT_NAME);
        role = DEFAULT_ROLE;
    }

    /**
     * Initializes the StaffBuilder with the data of {@code staffToCopy}
     * @param staffToCopy the staff to be copied
     */
    public StaffBuilder(Staff staffToCopy) {
        username = staffToCopy.getUsername();
        password = staffToCopy.getPassword();
        name = staffToCopy.getName();
        role = staffToCopy.getRole();
    }

    /**
     * Sets the {@code Username} of the {@code Staff} that we are building.
     */
    public StaffBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Staff} that we are building.
     */
    public StaffBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Staff} that we are building.
     */
    public StaffBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Staff} that we are building.
     */
    public StaffBuilder withRole(Staff.Role role) {
        this.role = role;
        return this;
    }

    public Staff build() {
        return new Staff(username, password, name, role);
    }
}
