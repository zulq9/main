package seedu.inventory.logic.parser.authentication;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.inventory.logic.commands.authentication.ChangePasswordCommand;
import seedu.inventory.logic.parser.ArgumentMultimap;
import seedu.inventory.logic.parser.ArgumentTokenizer;
import seedu.inventory.logic.parser.Parser;
import seedu.inventory.logic.parser.ParserUtil;
import seedu.inventory.logic.parser.Prefix;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.staff.Password;

/**
 * Parses input arguments and creates a new ChangePasswordCommand object.
 */
public class ChangePasswordCommandParser implements Parser<ChangePasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangePasswordCommand
     * and returns and ChangePasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangePasswordCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PASSWORD);

        if (!isPrefixPresent(argumentMultimap, PREFIX_PASSWORD)
            || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangePasswordCommand.MESSAGE_USAGE));
        }

        Password password = ParserUtil.parsePassword(argumentMultimap.getValue(PREFIX_PASSWORD).get());

        return new ChangePasswordCommand(password);
    }

    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }
}
