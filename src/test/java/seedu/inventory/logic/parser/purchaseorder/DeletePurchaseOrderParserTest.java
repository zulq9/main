package seedu.inventory.logic.parser.purchaseorder;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.Test;

import seedu.inventory.logic.commands.purchaseorder.DeletePurchaseOrderCommand;
import seedu.inventory.logic.parser.item.DeleteItemCommandParserTest;

/**
 * Test scope: similar to {@code DeleteItemCommandParserTest}.
 *
 * @see DeleteItemCommandParserTest
 */
public class DeletePurchaseOrderParserTest {

    private DeletePurchaseOrderCommandParser parser = new DeletePurchaseOrderCommandParser();

    @Test
    public void parse_validArgs_returnsDeletePurchaseOrderCommand() {
        assertParseSuccess(parser, "1", new DeletePurchaseOrderCommand(INDEX_FIRST_ITEM));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeletePurchaseOrderCommand.MESSAGE_USAGE));
    }

}
