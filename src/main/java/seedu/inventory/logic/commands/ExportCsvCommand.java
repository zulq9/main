package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.nio.file.Path;

import seedu.inventory.commons.util.FileUtil;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;



/**
 * Export the data to a Csv file.
 */
public class ExportCsvCommand extends Command {

    public static final String COMMAND_WORD_ITEMS = "export-csv-items";
    public static final String COMMAND_WORD_SALES = "export-csv-sales";
    public static final String COMMAND_WORD_STAFFS = "export-csv-staffs";
    public static final String COMMAND_WORD_PURCHASE_ORDERS = "export-csv-orders";

    public static final String MESSAGE_USAGE_ITEMS = COMMAND_WORD_ITEMS + ": Export the item list to a Csv file. "
            + "Parameters: "
            + PREFIX_FILEPATH + "FILEPATH \n"
            + "Example: " + COMMAND_WORD_ITEMS + " "
            + PREFIX_FILEPATH + "E:/out/items.csv";

    public static final String MESSAGE_USAGE_SALES = COMMAND_WORD_SALES + ": Export the sale list to a Csv file. "
            + "Parameters: "
            + PREFIX_FILEPATH + "FILEPATH \n"
            + "Example: " + COMMAND_WORD_SALES + " "
            + PREFIX_FILEPATH + "E:/out/sales.csv";

    public static final String MESSAGE_USAGE_STAFFS = COMMAND_WORD_STAFFS + ": Export the staff list to a Csv file. "
            + "Parameters: "
            + PREFIX_FILEPATH + "FILEPATH \n"
            + "Example: " + COMMAND_WORD_STAFFS + " "
            + PREFIX_FILEPATH + "E:/out/staffs.csv";

    public static final String MESSAGE_USAGE_PURCHASE_ORDERS = COMMAND_WORD_PURCHASE_ORDERS
            + ": Export the purchase order list to a Csv file. "
            + "Parameters: "
            + PREFIX_FILEPATH + "FILEPATH \n"
            + "Example: " + COMMAND_WORD_PURCHASE_ORDERS + " "
            + PREFIX_FILEPATH + "E:/out/orders.csv";

    public static final String MESSAGE_USAGE = MESSAGE_USAGE_ITEMS + "\n"
            + MESSAGE_USAGE_SALES + "\n"
            + MESSAGE_USAGE_STAFFS + "\n"
            + MESSAGE_USAGE_PURCHASE_ORDERS;

    public static final String MESSAGE_SUCCESS_ITEMS = "Item list is exporting to %s";

    public static final String MESSAGE_SUCCESS_SALES = "Sale list is exporting to %s";

    public static final String MESSAGE_SUCCESS_STAFFS = "Staff list is exporting to %s";

    public static final String MESSAGE_SUCCESS_PURCHASE_ORDERS = "Purchase order list is exporting to %s";

    public static final String MESSAGE_INVALID_CSV_FILEPATH = "%s is not a valid csv file path";

    public static final String MESSAGE_INVALID_COMMAND_WORD = "Command word is invalid";

    private String commandWord;

    private final Path filePath;

    /**
     * Creates an ExportCsvCommand to export the data.
     */
    public ExportCsvCommand(Path filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    public ExportCsvCommand setCommandWord(String commandWord) {
        this.commandWord = commandWord;
        return this;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        requireNonNull(commandWord);

        if (!FileUtil.isValidCsvFile(filePath)) {
            throw new CommandException(String.format(MESSAGE_INVALID_CSV_FILEPATH, filePath));
        }
        switch (commandWord) {
        case COMMAND_WORD_ITEMS:
            model.exportItemList(filePath);
            return new CommandResult(String.format(MESSAGE_SUCCESS_ITEMS, filePath.toAbsolutePath()));
        case COMMAND_WORD_SALES:
            model.exportSaleList(filePath);
            return new CommandResult(String.format(MESSAGE_SUCCESS_SALES, filePath.toAbsolutePath()));
        case COMMAND_WORD_STAFFS:
            model.exportStaffList(filePath);
            return new CommandResult(String.format(MESSAGE_SUCCESS_STAFFS, filePath.toAbsolutePath()));
        case COMMAND_WORD_PURCHASE_ORDERS:
            model.exportPurchaseOrderList(filePath);
            return new CommandResult(String.format(MESSAGE_SUCCESS_PURCHASE_ORDERS, filePath.toAbsolutePath()));
        default:
            throw new CommandException(MESSAGE_INVALID_COMMAND_WORD);

        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCsvCommand // instanceof handles nulls
                && filePath.equals(((ExportCsvCommand) other).filePath));
    }
}
