package seedu.inventory.logic.parser.sale;

import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.inventory.logic.commands.sale.DeleteSaleCommand;

/**
 * Allows any sort of strings to be parsed and trimmed.
 */
public class DeleteSaleCommandParserTest {

    private DeleteSaleCommandParser parser = new DeleteSaleCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteSaleCommand("1"));
    }
}
