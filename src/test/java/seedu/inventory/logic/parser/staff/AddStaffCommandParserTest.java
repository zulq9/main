package seedu.inventory.logic.parser.staff;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_STAFFNAME_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.NAME_DESC_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.NAME_DESC_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.PASSWORD_DESC_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.PASSWORD_DESC_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.inventory.logic.commands.CommandTestUtil.ROLE_DESC_ADMIN;
import static seedu.inventory.logic.commands.CommandTestUtil.ROLE_DESC_USER;
import static seedu.inventory.logic.commands.CommandTestUtil.USERNAME_DESC_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.USERNAME_DESC_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PASSWORD_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_USERNAME_ZUL;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.inventory.testutil.staff.TypicalStaffs.ZUL;

import org.junit.Test;

import seedu.inventory.logic.commands.staff.AddStaffCommand;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.StaffName;
import seedu.inventory.model.staff.Username;
import seedu.inventory.testutil.staff.StaffBuilder;


public class AddStaffCommandParserTest {
    private AddStaffCommandParser parser = new AddStaffCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Staff expectedStaff = new StaffBuilder(ZUL).withRole(Staff.Role.user).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_ZUL + PASSWORD_DESC_ZUL
                + NAME_DESC_ZUL + ROLE_DESC_USER,
                new AddStaffCommand(expectedStaff));

        // multiple usernames - last name accepted
        assertParseSuccess(parser, USERNAME_DESC_DARREN + USERNAME_DESC_ZUL + PASSWORD_DESC_ZUL
                + NAME_DESC_ZUL + ROLE_DESC_USER,
                new AddStaffCommand(expectedStaff));

        // multiple passwords - last password accepted
        assertParseSuccess(parser, USERNAME_DESC_ZUL + PASSWORD_DESC_DARREN + PASSWORD_DESC_ZUL
                + NAME_DESC_ZUL + ROLE_DESC_USER,
                new AddStaffCommand(expectedStaff));

        // multiple names - last name accepted
        assertParseSuccess(parser, USERNAME_DESC_ZUL + PASSWORD_DESC_ZUL + NAME_DESC_DARREN
                        + NAME_DESC_ZUL + ROLE_DESC_USER,
                new AddStaffCommand(expectedStaff));

        // multiple roles - last role accepted
        assertParseSuccess(parser, USERNAME_DESC_ZUL + PASSWORD_DESC_ZUL + NAME_DESC_ZUL
                        + ROLE_DESC_ADMIN + ROLE_DESC_USER,
                new AddStaffCommand(expectedStaff));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStaffCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, VALID_PASSWORD_ZUL + VALID_NAME_ZUL + ROLE_DESC_USER, expectedMessage);

        // missing password prefix
        assertParseFailure(parser, VALID_USERNAME_ZUL + VALID_NAME_ZUL + ROLE_DESC_USER, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, VALID_USERNAME_ZUL + VALID_PASSWORD_ZUL + ROLE_DESC_USER, expectedMessage);

        // missing role prefix
        assertParseFailure(parser, VALID_USERNAME_ZUL + VALID_PASSWORD_ZUL + VALID_NAME_ZUL, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser, INVALID_USERNAME_DESC + PASSWORD_DESC_ZUL + NAME_DESC_ZUL + ROLE_DESC_USER,
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, USERNAME_DESC_ZUL + INVALID_PASSWORD_DESC + NAME_DESC_ZUL + ROLE_DESC_USER,
                Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, USERNAME_DESC_ZUL + PASSWORD_DESC_ZUL + INVALID_STAFFNAME_DESC
                + ROLE_DESC_USER,
                StaffName.MESSAGE_NAME_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, USERNAME_DESC_ZUL + PASSWORD_DESC_ZUL + NAME_DESC_ZUL + INVALID_ROLE_DESC,
                Staff.Role.MESSAGE_ROLE_CONSTRAINTS);
    }

}
