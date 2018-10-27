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
import static seedu.inventory.logic.commands.CommandTestUtil.ROLE_DESC_ADMIN;
import static seedu.inventory.logic.commands.CommandTestUtil.ROLE_DESC_USER;
import static seedu.inventory.logic.commands.CommandTestUtil.USERNAME_DESC_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.USERNAME_DESC_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PASSWORD_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PASSWORD_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_USERNAME_ZUL;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_THIRD_ITEM;

import org.junit.Test;

import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.commands.staff.EditStaffCommand;
import seedu.inventory.logic.commands.staff.EditStaffCommand.EditStaffDescriptor;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.StaffName;
import seedu.inventory.model.staff.Username;
import seedu.inventory.testutil.staff.EditStaffDescriptorBuilder;

public class EditStaffCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStaffCommand.MESSAGE_USAGE);

    private EditStaffCommandParser parser = new EditStaffCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_ZUL, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditStaffCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_ZUL, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_ZUL, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_STAFFNAME_DESC, StaffName.MESSAGE_NAME_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_USERNAME_DESC,
                Username.MESSAGE_USERNAME_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_PASSWORD_DESC,
                Password.MESSAGE_PASSWORD_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_ROLE_DESC, Staff.Role.MESSAGE_ROLE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_USERNAME_DESC + INVALID_PASSWORD_DESC
                + INVALID_STAFFNAME_DESC + INVALID_ROLE_DESC, Username.MESSAGE_USERNAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ITEM;
        String userInput = targetIndex.getOneBased() + USERNAME_DESC_ZUL + PASSWORD_DESC_ZUL + NAME_DESC_ZUL
                + ROLE_DESC_USER;

        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder().withName(VALID_NAME_ZUL)
                .withUsername(VALID_USERNAME_ZUL).withPassword(Password.hash(VALID_PASSWORD_ZUL))
                .withRole("user").build();
        EditStaffCommand expectedCommand = new EditStaffCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + NAME_DESC_DARREN + ROLE_DESC_ADMIN;

        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder().withName(VALID_NAME_DARREN)
                .withRole("admin").build();
        EditStaffCommand expectedCommand = new EditStaffCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_ITEM;
        String userInput = targetIndex.getOneBased() + NAME_DESC_DARREN;
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder().withName(VALID_NAME_DARREN).build();
        EditStaffCommand expectedCommand = new EditStaffCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // password
        userInput = targetIndex.getOneBased() + PASSWORD_DESC_ZUL;
        descriptor = new EditStaffDescriptorBuilder().withPassword(Password.hash(VALID_PASSWORD_ZUL)).build();
        expectedCommand = new EditStaffCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // username
        userInput = targetIndex.getOneBased() + USERNAME_DESC_ZUL;
        descriptor = new EditStaffDescriptorBuilder().withUsername(VALID_USERNAME_ZUL).build();
        expectedCommand = new EditStaffCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // role
        userInput = targetIndex.getOneBased() + ROLE_DESC_USER;
        descriptor = new EditStaffDescriptorBuilder().withRole("user").build();
        expectedCommand = new EditStaffCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + USERNAME_DESC_DARREN + USERNAME_DESC_ZUL
                + PASSWORD_DESC_DARREN + PASSWORD_DESC_ZUL + NAME_DESC_DARREN + NAME_DESC_ZUL + ROLE_DESC_USER
                + ROLE_DESC_ADMIN;

        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder().withName(VALID_NAME_ZUL)
                .withUsername(VALID_USERNAME_ZUL).withPassword(Password.hash(VALID_PASSWORD_ZUL))
                .withRole("admin").build();
        EditStaffCommand expectedCommand = new EditStaffCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + INVALID_STAFFNAME_DESC + NAME_DESC_ZUL;
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder().withName(VALID_NAME_ZUL).build();
        EditStaffCommand expectedCommand = new EditStaffCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + NAME_DESC_DARREN + INVALID_PASSWORD_DESC
                + PASSWORD_DESC_DARREN + ROLE_DESC_ADMIN;
        descriptor = new EditStaffDescriptorBuilder().withName(VALID_NAME_DARREN)
                .withPassword(Password.hash(VALID_PASSWORD_DARREN))
                .withRole("admin").build();
        expectedCommand = new EditStaffCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
