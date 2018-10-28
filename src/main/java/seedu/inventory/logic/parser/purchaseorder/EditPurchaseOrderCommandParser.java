package seedu.inventory.logic.parser.purchaseorder;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_REQDATE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SUPPLIER;

import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.commands.purchaseorder.EditPurchaseOrderCommand;
import seedu.inventory.logic.parser.ArgumentMultimap;
import seedu.inventory.logic.parser.ArgumentTokenizer;
import seedu.inventory.logic.parser.Parser;
import seedu.inventory.logic.parser.ParserUtil;
import seedu.inventory.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditPurchaseOrderCommand object
 */
public class EditPurchaseOrderCommandParser implements Parser<EditPurchaseOrderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditPurchaseOrderCommand
     * and returns an EditPurchaseOrderCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPurchaseOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUANTITY, PREFIX_REQDATE, PREFIX_SUPPLIER);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditPurchaseOrderCommand.MESSAGE_USAGE), pe);
        }

        EditPurchaseOrderCommand.EditPoDescriptor editPoDescriptor = new EditPurchaseOrderCommand.EditPoDescriptor();
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            editPoDescriptor.setQuantity(ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()));
        }
        if (argMultimap.getValue(PREFIX_REQDATE).isPresent()) {
            editPoDescriptor.setReqDate(ParserUtil.parseReqDate(argMultimap.getValue(PREFIX_REQDATE).get()));
        }
        if (argMultimap.getValue(PREFIX_SUPPLIER).isPresent()) {
            editPoDescriptor.setSupplier(ParserUtil.parseSupplier(argMultimap.getValue(PREFIX_SUPPLIER).get()));
        }

        if (!editPoDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPurchaseOrderCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPurchaseOrderCommand(index, editPoDescriptor);
    }
}
