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
import seedu.inventory.logic.commands.ExportCsvCommand;
import seedu.inventory.logic.commands.FindItemCommand;
import seedu.inventory.logic.commands.FindItemSkuCommand;
import seedu.inventory.logic.commands.HelpCommand;
import seedu.inventory.logic.commands.HistoryCommand;
import seedu.inventory.logic.commands.ImportCsvCommand;
import seedu.inventory.logic.commands.ListItemCommand;
import seedu.inventory.logic.commands.RedoCommand;
import seedu.inventory.logic.commands.SelectCommand;
import seedu.inventory.logic.commands.UndoCommand;
import seedu.inventory.logic.commands.authentication.ChangePasswordCommand;
import seedu.inventory.logic.commands.authentication.LoginCommand;
import seedu.inventory.logic.commands.authentication.LogoutCommand;
import seedu.inventory.logic.commands.purchaseorder.AddPurchaseOrderCommand;
import seedu.inventory.logic.commands.purchaseorder.ApprovePurchaseOrderCommand;
import seedu.inventory.logic.commands.purchaseorder.DeletePurchaseOrderCommand;
import seedu.inventory.logic.commands.purchaseorder.EditPurchaseOrderCommand;
import seedu.inventory.logic.commands.purchaseorder.ListPurchaseOrderCommand;
import seedu.inventory.logic.commands.purchaseorder.RejectPurchaseOrderCommand;
import seedu.inventory.logic.commands.sale.AddSaleCommand;
import seedu.inventory.logic.commands.sale.DeleteSaleCommand;
import seedu.inventory.logic.commands.sale.ListSaleCommand;
import seedu.inventory.logic.commands.staff.AddStaffCommand;
import seedu.inventory.logic.commands.staff.DeleteStaffCommand;
import seedu.inventory.logic.commands.staff.EditStaffCommand;
import seedu.inventory.logic.commands.staff.ListStaffCommand;
import seedu.inventory.logic.parser.authentication.ChangePasswordCommandParser;
import seedu.inventory.logic.parser.authentication.LoginCommandParser;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.logic.parser.purchaseorder.AddPurchaseOrderCommandParser;
import seedu.inventory.logic.parser.purchaseorder.ApprovePurchaseOrderCommandParser;
import seedu.inventory.logic.parser.purchaseorder.DeletePurchaseOrderCommandParser;
import seedu.inventory.logic.parser.purchaseorder.EditPurchaseOrderCommandParser;
import seedu.inventory.logic.parser.purchaseorder.RejectPurchaseOrderCommandParser;
import seedu.inventory.logic.parser.sale.AddSaleCommandParser;
import seedu.inventory.logic.parser.sale.DeleteSaleCommandParser;
import seedu.inventory.logic.parser.staff.AddStaffCommandParser;
import seedu.inventory.logic.parser.staff.DeleteStaffCommandParser;
import seedu.inventory.logic.parser.staff.EditStaffCommandParser;

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

        case ChangePasswordCommand.COMMAND_WORD:
            return new ChangePasswordCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case AddStaffCommand.COMMAND_WORD:
            return new AddStaffCommandParser().parse(arguments);

        case ListStaffCommand.COMMAND_WORD:
            return new ListStaffCommand();

        case EditStaffCommand.COMMAND_WORD:
            return new EditStaffCommandParser().parse(arguments);

        case DeleteStaffCommand.COMMAND_WORD:
            return new DeleteStaffCommandParser().parse(arguments);

        case AddItemCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditItemCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteItemCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindItemCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindItemSkuCommand.COMMAND_WORD:
            return new FindItemSkuCommandParser().parse(arguments);

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

        case AddPurchaseOrderCommand.COMMAND_WORD:
            return new AddPurchaseOrderCommandParser().parse(arguments);

        case DeletePurchaseOrderCommand.COMMAND_WORD:
            return new DeletePurchaseOrderCommandParser().parse(arguments);

        case ApprovePurchaseOrderCommand.COMMAND_WORD:
            return new ApprovePurchaseOrderCommandParser().parse(arguments);

        case RejectPurchaseOrderCommand.COMMAND_WORD:
            return new RejectPurchaseOrderCommandParser().parse(arguments);

        case EditPurchaseOrderCommand.COMMAND_WORD:
            return new EditPurchaseOrderCommandParser().parse(arguments);

        case AddSaleCommand.COMMAND_WORD:
            return new AddSaleCommandParser().parse(arguments);

        case DeleteSaleCommand.COMMAND_WORD:
            return new DeleteSaleCommandParser().parse(arguments);

        case ListSaleCommand.COMMAND_WORD:
            return new ListSaleCommand();

        case ExportCsvCommand.COMMAND_WORD_ITEMS:
        case ExportCsvCommand.COMMAND_WORD_SALES:
        case ExportCsvCommand.COMMAND_WORD_STAFFS:
        case ExportCsvCommand.COMMAND_WORD_PURCHASE_ORDERS:
            return new ExportCsvCommandParser().parse(arguments).setCommandWord(commandWord);

        case ImportCsvCommand.COMMAND_WORD_ITEMS:
        case ImportCsvCommand.COMMAND_WORD_SALES:
        case ImportCsvCommand.COMMAND_WORD_STAFFS:
        case ImportCsvCommand.COMMAND_WORD_PURCHASE_ORDERS:
            return new ImportCsvCommandParser().parse(arguments).setCommandWord(commandWord);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
