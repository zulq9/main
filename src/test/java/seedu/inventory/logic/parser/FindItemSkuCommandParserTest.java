package seedu.inventory.logic.parser;

import org.junit.Test;
import seedu.inventory.logic.commands.FindItemSkuCommand;
import seedu.inventory.model.item.SkuContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class FindItemSkuCommandParserTest {

    private FindItemSkuCommandParser parser = new FindItemSkuCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindItemSkuCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindItemSkuCommand expectedFindItemSkuCommand =
                new FindItemSkuCommand(new SkuContainsKeywordsPredicate(Arrays.asList("samsung", "iphone")));
        assertParseSuccess(parser, "samsung iphone", expectedFindItemSkuCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n samsung \n \t iphone  \t", expectedFindItemSkuCommand);
    }

}
