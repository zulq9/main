package seedu.inventory.logic.parser.item;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.inventory.logic.commands.item.FilterItemCommand;
import seedu.inventory.logic.parser.ArgumentMultimap;
import seedu.inventory.logic.parser.ArgumentTokenizer;
import seedu.inventory.logic.parser.Parser;
import seedu.inventory.logic.parser.ParserUtil;
import seedu.inventory.logic.parser.Prefix;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.item.FilterItemPredicate;
import seedu.inventory.model.item.FilterPrice;
import seedu.inventory.model.item.FilterQuantity;


/**
 * Parses input arguments and creates a new AddItemCommand object
 */
public class FilterItemCommandParser implements Parser<FilterItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddItemCommand
     * and returns an FilterItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterItemCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PRICE, PREFIX_QUANTITY);

        if (!(arePrefixesPresent(argMultimap, PREFIX_PRICE)
                || arePrefixesPresent(argMultimap, PREFIX_QUANTITY))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterItemCommand.MESSAGE_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_PRICE, PREFIX_QUANTITY)) {
            FilterPrice price = ParserUtil.parseFilterPrice(argMultimap.getValue(PREFIX_PRICE).get());
            FilterQuantity quantity = ParserUtil.parseFilterQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());

            return new FilterItemCommand(new FilterItemPredicate(price, quantity));
        } else if (arePrefixesPresent(argMultimap, PREFIX_PRICE)) {
            FilterPrice price = ParserUtil.parseFilterPrice(argMultimap.getValue(PREFIX_PRICE).get());

            return new FilterItemCommand(new FilterItemPredicate(price));
        } else {
            FilterQuantity quantity = ParserUtil.parseFilterQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());

            return new FilterItemCommand(new FilterItemPredicate(quantity));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
