package seedu.inventory.storage.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.StaffName;
import seedu.inventory.model.staff.Username;

/**
 * Csv-friendly adapted version of the Sale.
 */
public class CsvAdaptedStaff {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Staff's %s field is missing!";
    public static final String MISSING_FIELD_MESSAGE = "Staff's field is missing!";

    private String username;
    private String password;
    private String staffName;
    private String role;


    /**
     * Constructs an {@code CsvAdaptedStaff} with the given staff details.
     */
    public CsvAdaptedStaff(String username, String password, String staffName, String role) {
        this.username = username;
        this.password = password;
        this.staffName = staffName;
        this.role = role;
    }


    /**
     * Converts a given Staff into this class for Csv use.
     *
     * @param source future changes to this will not affect the created CsvAdaptedStaff
     */
    public CsvAdaptedStaff(Staff source) {
        this.username = source.getUsername().username;
        this.password = source.getPassword().password;
        this.staffName = source.getStaffName().fullName;
        this.role = source.getRole().name();
    }

    /**
     * Converts this Csv-friendly adapted staff object into the model's Staff object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted staff
     */
    public Staff toModelType() throws IllegalValueException {
        if (this.username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }
        if (!Username.isValidUsername(this.username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        final Username modelUsername = new Username(username);

        if (this.password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Password.class.getSimpleName()));
        }
        if (!Password.isValidPassword(this.password)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        final Password modelPassword = new Password(password);

        if (this.staffName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StaffName.class.getSimpleName()));
        }
        if (!StaffName.isValidName(this.staffName)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        final StaffName modelStaffName = new StaffName(staffName);

        if (this.role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Staff.Role.class.getSimpleName()));
        }

        final Staff.Role modelRole;

        if (this.role.equals("admin")) {
            modelRole = Staff.Role.admin;
        } else if (this.role.equals("manager")) {
            modelRole = Staff.Role.manager;
        } else if (this.role.equals("user")) {
            modelRole = Staff.Role.user;
        } else {
            throw new IllegalValueException(Staff.Role.MESSAGE_ROLE_CONSTRAINTS);
        }

        return new Staff(modelUsername, modelPassword, modelStaffName, modelRole);
    }


    /**
     * Combine a Csv-friendly adapted staff into a list of string representing the content.
     *
     * @param staff A Csv-friendly staff
     * @return content A list of string representing the content.
     */
    public static List<String> getContentFromStaff(CsvAdaptedStaff staff) {
        List<String> content = new ArrayList<>();
        content.add(staff.username);
        content.add(staff.password);
        content.add(staff.staffName);
        content.add(staff.role);
        return content;
    }

    /**
     * Split a list of string representing the content of staff into the Csv-friendly adapted staff
     *
     * @param content A list of string representing the content of staff
     * @return The Csv-friendly adapted staff containing the content of the list.
     */
    public static CsvAdaptedStaff splitContentToStaff(List<String> content) throws IllegalValueException {
        if (content.size() < 4) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE);
        }
        String username = content.get(0);
        String password = content.get(1);
        String name = content.get(2);
        String role = content.get(3);
        return new CsvAdaptedStaff(username, password, name, role);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CsvAdaptedStaff)) {
            return false;
        }

        CsvAdaptedStaff otherStaff = (CsvAdaptedStaff) other;
        return Objects.equals(username, otherStaff.username)
                && Objects.equals(password, otherStaff.password)
                && Objects.equals(staffName, otherStaff.staffName)
                && Objects.equals(role, otherStaff.role);
    }
}
