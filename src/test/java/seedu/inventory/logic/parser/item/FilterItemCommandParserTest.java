package seedu.inventory.logic.parser.item;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.inventory.logic.commands.item.FilterItemCommand;
import seedu.inventory.model.item.FilterItemPredicate;
import seedu.inventory.model.item.FilterPrice;
import seedu.inventory.model.item.FilterQuantity;

public class FilterItemCommandParserTest {

    private FilterItemCommandParser parser = new FilterItemCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterItemCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterItemCommand() {
        // no leading and trailing whitespaces with price and quantity as conditions
        FilterItemCommand expectedFilterItemCommand =
                new FilterItemCommand(new FilterItemPredicate(new FilterPrice(">1500"), new FilterQuantity(">10")));
        assertParseSuccess(parser, " p/>1500 q/>10", expectedFilterItemCommand);

        // multiple whitespaces between keywords with price and quantity as conditions
        assertParseSuccess(parser, " \n p/>1500 \n \t  q/>10 \t", expectedFilterItemCommand);

        // no leading and trailing whitespaces with only price as condition
        expectedFilterItemCommand =
                new FilterItemCommand(new FilterItemPredicate(new FilterPrice(">1500")));
        assertParseSuccess(parser, " p/>1500", expectedFilterItemCommand);

        // multiple whitespaces between keywords with only price as condition
        assertParseSuccess(parser, " \n p/>1500 \n \t  \t", expectedFilterItemCommand);

        // no leading and trailing whitespaces with only quantity as condition
        expectedFilterItemCommand =
                new FilterItemCommand(new FilterItemPredicate(new FilterQuantity(">100")));
        assertParseSuccess(parser, " q/>100", expectedFilterItemCommand);

        // multiple whitespaces between keywords with only quantity as condition
        assertParseSuccess(parser, " \n q/>100 \n \t  \t", expectedFilterItemCommand);
    }

}
