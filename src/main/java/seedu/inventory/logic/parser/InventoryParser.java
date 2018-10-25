package seedu.inventory.logic.parser;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.inventory.logic.commands.AddItemCommand;
import seedu.inventory.logic.commands.ClearCommand;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.DeleteItemCommand;
import seedu.inventory.logic.commands.EditItemCommand;
import seedu.inventory.logic.commands.ExitCommand;
import seedu.inventory.logic.commands.ExportCsvItemsCommand;
import seedu.inventory.logic.commands.FindItemCommand;
import seedu.inventory.logic.commands.HelpCommand;
import seedu.inventory.logic.commands.HistoryCommand;
import seedu.inventory.logic.commands.ImportCsvItemsCommand;
import seedu.inventory.logic.commands.ListItemCommand;
import seedu.inventory.logic.commands.LoginCommand;
import seedu.inventory.logic.commands.RedoCommand;
import seedu.inventory.logic.commands.SelectItemCommand;
import seedu.inventory.logic.commands.UndoCommand;
import seedu.inventory.logic.commands.purchaseorder.GeneratePurchaseOrderCommand;
import seedu.inventory.logic.commands.purchaseorder.ListPurchaseOrderCommand;
import seedu.inventory.logic.commands.sale.AddSaleCommand;
import seedu.inventory.logic.commands.sale.DeleteSaleCommand;
import seedu.inventory.logic.commands.sale.ListSaleCommand;
import seedu.inventory.logic.commands.staff.AddStaffCommand;
import seedu.inventory.logic.commands.staff.DeleteStaffCommand;
import seedu.inventory.logic.commands.staff.ListStaffCommand;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.logic.parser.purchaseorder.GeneratePurchaseOrderCommandParser;
import seedu.inventory.logic.parser.sale.AddSaleCommandParser;
import seedu.inventory.logic.parser.sale.DeleteSaleCommandParser;

/**
 * Parses user input.
 */
public class InventoryParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case AddStaffCommand.COMMAND_WORD:
            return new AddStaffCommandParser().parse(arguments);

        case ListStaffCommand.COMMAND_WORD:
            return new ListStaffCommand();

        case DeleteStaffCommand.COMMAND_WORD:
            return new DeleteStaffCommandParser().parse(arguments);

        case AddItemCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditItemCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectItemCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteItemCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindItemCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListItemCommand.COMMAND_WORD:
            return new ListItemCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ListPurchaseOrderCommand.COMMAND_WORD:
            return new ListPurchaseOrderCommand();

        case GeneratePurchaseOrderCommand.COMMAND_WORD:
            return new GeneratePurchaseOrderCommandParser().parse(arguments);

        case AddSaleCommand.COMMAND_WORD:
            return new AddSaleCommandParser().parse(arguments);

        case DeleteSaleCommand.COMMAND_WORD:
            return new DeleteSaleCommandParser().parse(arguments);

        case ListSaleCommand.COMMAND_WORD:
            return new ListSaleCommand();

        case ExportCsvItemsCommand.COMMAND_WORD:
            return new ExportCsvItemsCommandParser().parse(arguments);

        case ImportCsvItemsCommand.COMMAND_WORD:
            return new ImportCsvItemsCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
