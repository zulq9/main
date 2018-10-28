package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.inventory.storage.CsvAdaptedStaff.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.inventory.testutil.staff.TypicalStaffs.TENGXIONG;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.Staff.Role;
import seedu.inventory.model.staff.StaffName;
import seedu.inventory.model.staff.Username;
import seedu.inventory.testutil.Assert;

public class CsvAdaptedStaffTest {
    private static final String INVALID_USERNAME = "y@0-tx";
    private static final String INVALID_PASSWORD = "..asdas..";
    private static final String INVALID_NAME = "@asdvd daniel";
    private static final String INVALID_ROLE = "role";


    private static final String VALID_USERNAME = TENGXIONG.getUsername().toString();
    private static final String VALID_PASSWORD = TENGXIONG.getPassword().toString();
    private static final String VALID_NAME = TENGXIONG.getStaffName().toString();
    private static final String VALID_ROLE = Role.user.name();

    @Test
    public void toModelType_validStaffDetails_returnsSale() throws Exception {
        CsvAdaptedStaff staff = new CsvAdaptedStaff(TENGXIONG);
        assertEquals(TENGXIONG, staff.toModelType());

    }

    @Test
    public void toModelType_invalidUsername_throwsIllegalValueException() {
        CsvAdaptedStaff staff = new CsvAdaptedStaff(INVALID_USERNAME, VALID_PASSWORD, VALID_NAME, VALID_ROLE);
        String expectedMessage = Username.MESSAGE_USERNAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_nullUsername_throwsIllegalValueException() {
        CsvAdaptedStaff staff = new CsvAdaptedStaff(null, VALID_PASSWORD, VALID_NAME, VALID_ROLE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Username.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_invalidPassword_throwsIllegalValueException() {
        CsvAdaptedStaff staff = new CsvAdaptedStaff(VALID_USERNAME, INVALID_PASSWORD, VALID_NAME, VALID_ROLE);
        String expectedMessage = Password.MESSAGE_PASSWORD_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_nullPassword_throwsIllegalValueException() {
        CsvAdaptedStaff staff = new CsvAdaptedStaff(VALID_USERNAME, null, VALID_NAME, VALID_ROLE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Password.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        CsvAdaptedStaff staff = new CsvAdaptedStaff(VALID_USERNAME, VALID_PASSWORD, INVALID_NAME, VALID_ROLE);
        String expectedMessage = Username.MESSAGE_USERNAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        CsvAdaptedStaff staff = new CsvAdaptedStaff(VALID_USERNAME, VALID_PASSWORD, null, VALID_ROLE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StaffName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        CsvAdaptedStaff staff = new CsvAdaptedStaff(VALID_USERNAME, VALID_PASSWORD, VALID_NAME, INVALID_ROLE);
        String expectedMessage = Role.MESSAGE_ROLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        CsvAdaptedStaff staff = new CsvAdaptedStaff(VALID_USERNAME, VALID_PASSWORD, VALID_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Staff.Role.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }


    @Test
    public void splitContentToStaff_validContent() throws Exception {
        List<String> validContent = new ArrayList<>();
        validContent.add(VALID_USERNAME);
        validContent.add(VALID_PASSWORD);
        validContent.add(VALID_NAME);
        validContent.add(VALID_ROLE);
        CsvAdaptedStaff validStaff =
                new CsvAdaptedStaff(VALID_USERNAME, VALID_PASSWORD, VALID_NAME, VALID_ROLE);

        assertEquals(CsvAdaptedStaff.splitContentToStaff(validContent), validStaff);
    }

    @Test
    public void splitContentToSale_invalidContent_illegalValueException() throws Exception {
        List<String> invalidContent = new ArrayList<>();
        invalidContent.add(VALID_USERNAME);
        invalidContent.add(VALID_PASSWORD);
        invalidContent.add(VALID_NAME);
        Assert.assertThrows(IllegalValueException.class, () -> CsvAdaptedStaff.splitContentToStaff(invalidContent));
    }

    @Test
    public void getContentFromItem() throws Exception {
        List<String> validContent = new ArrayList<>();
        validContent.add(VALID_USERNAME);
        validContent.add(VALID_PASSWORD);
        validContent.add(VALID_NAME);
        validContent.add(VALID_ROLE);
        CsvAdaptedStaff validStaff =
                new CsvAdaptedStaff(VALID_USERNAME, VALID_PASSWORD, VALID_NAME, VALID_ROLE);

        assertEquals(CsvAdaptedStaff.getContentFromStaff(validStaff), validContent);
    }

    @Test
    public void equals() {
        CsvAdaptedStaff validStaff =
                new CsvAdaptedStaff(VALID_USERNAME, INVALID_PASSWORD, VALID_NAME, VALID_ROLE);
        CsvAdaptedStaff anotherValidStaff =
                new CsvAdaptedStaff(VALID_USERNAME, INVALID_PASSWORD, VALID_NAME, VALID_ROLE);
        CsvAdaptedStaff invalidStaff =
                new CsvAdaptedStaff(INVALID_USERNAME, INVALID_PASSWORD, VALID_NAME, VALID_ROLE);

        assertEquals(validStaff, anotherValidStaff);
        assertEquals(validStaff, validStaff);
        assertNotEquals(validStaff, Role.user);
        assertNotEquals(validStaff, invalidStaff);
    }
}
