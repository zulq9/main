package seedu.inventory.logic.parser.sale;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.inventory.logic.commands.sale.DeleteSaleCommand;
import seedu.inventory.logic.parser.Parser;
import seedu.inventory.logic.parser.ParserUtil;
import seedu.inventory.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteItemCommand object
 */
public class DeleteSaleCommandParser implements Parser<DeleteSaleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSaleCommand
     * and returns an DeleteSaleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteSaleCommand parse(String args) throws ParseException {
        try {
            String saleId = ParserUtil.parseString(args);
            return new DeleteSaleCommand(saleId);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSaleCommand.MESSAGE_USAGE), pe);
        }
    }

}
