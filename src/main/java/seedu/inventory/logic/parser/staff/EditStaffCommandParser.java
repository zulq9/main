package seedu.inventory.logic.parser.staff;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.commands.staff.EditStaffCommand;
import seedu.inventory.logic.commands.staff.EditStaffCommand.EditStaffDescriptor;
import seedu.inventory.logic.parser.ArgumentMultimap;
import seedu.inventory.logic.parser.ArgumentTokenizer;
import seedu.inventory.logic.parser.Parser;
import seedu.inventory.logic.parser.ParserUtil;
import seedu.inventory.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditStaffCommand object
 */
public class EditStaffCommandParser implements Parser<EditStaffCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditStaffCommand
     * and returns an EditStaffCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditStaffCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_NAME, PREFIX_ROLE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStaffCommand.MESSAGE_USAGE, pe));
        }

        EditStaffDescriptor editStaffDescriptor = new EditStaffDescriptor();
        if (argumentMultimap.getValue(PREFIX_USERNAME).isPresent()) {
            editStaffDescriptor.setUsername(ParserUtil.parseUsername(argumentMultimap.getValue(PREFIX_USERNAME).get()));
        }
        if (argumentMultimap.getValue(PREFIX_PASSWORD).isPresent()) {
            editStaffDescriptor.setPassword(ParserUtil.parsePassword(argumentMultimap.getValue(PREFIX_PASSWORD).get()));
        }
        if (argumentMultimap.getValue(PREFIX_NAME).isPresent()) {
            editStaffDescriptor.setName(ParserUtil.parseStaffName(argumentMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argumentMultimap.getValue(PREFIX_ROLE).isPresent()) {
            editStaffDescriptor.setRole(ParserUtil.parseRole(argumentMultimap.getValue(PREFIX_ROLE).get()));
        }

        if (!editStaffDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditStaffCommand.MESSAGE_NOT_EDITED);
        }

        return new EditStaffCommand(index, editStaffDescriptor);
    }
}
