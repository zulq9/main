package seedu.inventory.logic.parser;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.commands.SelectItemCommand;
import seedu.inventory.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectItemCommand object
 */
public class SelectCommandParser implements Parser<SelectItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectItemCommand
     * and returns an SelectItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectItemCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectItemCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectItemCommand.MESSAGE_USAGE), pe);
        }
    }
}
