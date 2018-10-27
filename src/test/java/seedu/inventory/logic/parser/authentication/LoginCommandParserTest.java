package seedu.inventory.logic.parser.authentication;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.PASSWORD_DESC_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.PASSWORD_DESC_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.inventory.logic.commands.CommandTestUtil.USERNAME_DESC_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.USERNAME_DESC_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PASSWORD_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_USERNAME_ZUL;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.logic.commands.authentication.LoginCommand;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.Username;
import seedu.inventory.testutil.staff.StaffBuilder;

public class LoginCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private LoginCommandParser parser = new LoginCommandParser();
    private Staff zul;

    @Before
    public void setup() {
        zul = new StaffBuilder().withUsername(VALID_USERNAME_ZUL).withPassword(Password.hash(VALID_PASSWORD_ZUL))
                .withName("dummy").withRole(Staff.Role.role("user")).build();
    }

    @Test
    public void parse_allFieldsPresent_success() {
        // white space only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_ZUL + PASSWORD_DESC_ZUL,
                new LoginCommand(zul));

        // white space only
        assertParseSuccess(parser, USERNAME_DESC_ZUL + PASSWORD_DESC_ZUL, new LoginCommand(zul));
    }

    @Test
    public void parse_someFieldsPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

        // missing username
        assertParseFailure(parser, PASSWORD_DESC_DARREN, expectedMessage);

        // missing password
        assertParseFailure(parser, USERNAME_DESC_DARREN, expectedMessage);

        // missing input
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser, INVALID_USERNAME_DESC + PASSWORD_DESC_ZUL,
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, USERNAME_DESC_ZUL + INVALID_PASSWORD_DESC,
                Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // 2 invalid values, only first one is detected
        assertParseFailure(parser, INVALID_USERNAME_DESC + INVALID_PASSWORD_DESC,
                Username.MESSAGE_USERNAME_CONSTRAINTS);
    }
}
