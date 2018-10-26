package seedu.inventory.logic.parser;

import seedu.inventory.logic.commands.FindItemSkuCommand;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.item.SkuContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new FindItemSkuCommand object
 */
public class FindItemSkuCommandParser implements Parser<FindItemSkuCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindItemSkuCommand
     * and returns a FindItemSkuCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindItemSkuCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindItemSkuCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindItemSkuCommand(new SkuContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
