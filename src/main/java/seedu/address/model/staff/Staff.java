package seedu.address.model.staff;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Staff for user management in the system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Staff {
    private Username username;
    private Password password;
    private Name name;
    private Role role;

    /**
     * This is a enum for staff's role.
     */
    public enum Role {
        admin, manager, user
    }

    /**
     * Every field must be present and not null.
     */
    private Staff(Username username, Password password, Name name, Role role) {
        requireAllNonNull(username, password, name, role);
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public Username getUsername() {
        return this.username;
    }

    public Password getPassword() {
        return this.password;
    }

    public Name getName() {
        return this.name;
    }

    public Role getRole() {
        return this.role;
    }

    /**
     * Returns true if both staffs have the same identity and data fields.
     * This defines a stronger notion of equality between two staffs.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Staff)) {
            return false;
        }

        if (other == this) {
            return true;
        }

        Staff otherStaff = (Staff) other;
        return otherStaff.getUsername().equals(getUsername())
                && otherStaff.getPassword().equals(getPassword())
                && otherStaff.getName().equals(getName())
                && otherStaff.getRole().equals(getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, name, role);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getUsername())
                .append(" Name: ")
                .append(getName())
                .append(" Role: ")
                .append(getRole());
        return builder.toString();
    }
}
