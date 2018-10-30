package seedu.inventory.logic.parser.purchaseorder;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.Test;

import seedu.inventory.logic.commands.purchaseorder.ApprovePurchaseOrderCommand;
import seedu.inventory.logic.parser.item.DeleteItemCommandParserTest;

/**
 * Test scope: similar to {@code DeleteItemCommandParserTest}.
 *
 * @see DeleteItemCommandParserTest
 */
public class ApprovePurchaseOrderParserTest {

    private ApprovePurchaseOrderCommandParser parser = new ApprovePurchaseOrderCommandParser();

    @Test
    public void parse_validArgs_returnsApprovePurchaseOrderCommand() {
        assertParseSuccess(parser, "1", new ApprovePurchaseOrderCommand(INDEX_FIRST_ITEM));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ApprovePurchaseOrderCommand.MESSAGE_USAGE));
    }
}
