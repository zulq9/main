package seedu.inventory.logic.parser.authentication;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.PASSWORD_DESC_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PASSWORD_ZUL;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.logic.commands.authentication.ChangePasswordCommand;
import seedu.inventory.model.staff.Password;

public class ChangePasswordCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ChangePasswordCommandParser parser = new ChangePasswordCommandParser();
    private Password zulPassword;

    @Before
    public void setUp() {
        zulPassword = new Password(Password.hash(VALID_PASSWORD_ZUL));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        // white space only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PASSWORD_DESC_ZUL,
                new ChangePasswordCommand(zulPassword));

        // white space only
        assertParseSuccess(parser, PASSWORD_DESC_ZUL, new ChangePasswordCommand(zulPassword));
    }

    @Test
    public void parse_missingField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePasswordCommand.MESSAGE_USAGE);

        // missing input
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid password
        assertParseFailure(parser, INVALID_PASSWORD_DESC, Password.MESSAGE_PASSWORD_CONSTRAINTS);
    }
}
