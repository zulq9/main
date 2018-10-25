package seedu.inventory.logic.parser;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.inventory.logic.commands.FindItemCommand;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.item.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindItemCommand object
 */
public class FindCommandParser implements Parser<FindItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindItemCommand
     * and returns a FindItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindItemCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindItemCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindItemCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
