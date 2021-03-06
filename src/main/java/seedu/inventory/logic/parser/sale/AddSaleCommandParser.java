package seedu.inventory.logic.parser.sale;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;

import java.util.stream.Stream;

import seedu.inventory.logic.commands.sale.AddSaleCommand;
import seedu.inventory.logic.parser.ArgumentMultimap;
import seedu.inventory.logic.parser.ArgumentTokenizer;
import seedu.inventory.logic.parser.Parser;
import seedu.inventory.logic.parser.ParserUtil;
import seedu.inventory.logic.parser.Prefix;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;

/**
 * Parses input arguments and creates a new AddSaleCommand object
 */
public class AddSaleCommandParser implements Parser<AddSaleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSaleCommand
     * and returns an AddSaleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSaleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SKU, PREFIX_QUANTITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_SKU, PREFIX_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSaleCommand.MESSAGE_USAGE));
        }

        Sku sku = ParserUtil.parseSku(argMultimap.getValue(PREFIX_SKU).get());
        Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());

        return new AddSaleCommand(sku, quantity);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
