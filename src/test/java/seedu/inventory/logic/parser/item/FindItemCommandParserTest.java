package seedu.inventory.logic.parser.item;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.inventory.logic.commands.item.FindItemCommand;
import seedu.inventory.model.item.NameContainsKeywordsPredicate;

public class FindItemCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindItemCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindItemCommand expectedFindItemCommand =
                new FindItemCommand(new NameContainsKeywordsPredicate(Arrays.asList("Samsung", "iPhone")));
        assertParseSuccess(parser, "Samsung iPhone", expectedFindItemCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Samsung \n \t iPhone  \t", expectedFindItemCommand);
    }

}
