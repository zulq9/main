package seedu.inventory.logic.commands.csv;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.nio.file.Path;
import java.util.Objects;

import seedu.inventory.commons.util.FileUtil;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;

/**
 * Import the item list to a Csv file.
 */
public class ImportCsvCommand extends Command {

    public static final String COMMAND_WORD_ITEMS = "import-csv-items";
    public static final String COMMAND_WORD_SALES = "import-csv-sales";
    public static final String COMMAND_WORD_STAFFS = "import-csv-staffs";
    public static final String COMMAND_WORD_PURCHASE_ORDERS = "import-csv-orders";

    public static final String MESSAGE_USAGE_ITEMS = COMMAND_WORD_ITEMS + ": Import the item list from a Csv file. "
            + "Parameters: "
            + PREFIX_FILEPATH + "FILEPATH \n"
            + "Example: " + COMMAND_WORD_ITEMS + " "
            + PREFIX_FILEPATH + "E:/in/items.csv";

    public static final String MESSAGE_USAGE_SALES = COMMAND_WORD_SALES + ": Import the sale list from a Csv file. "
            + "Parameters: "
            + PREFIX_FILEPATH + "FILEPATH \n"
            + "Example: " + COMMAND_WORD_SALES + " "
            + PREFIX_FILEPATH + "E:/in/sales.csv";

    public static final String MESSAGE_USAGE_STAFFS = COMMAND_WORD_STAFFS + ": Import the sale list from a Csv file. "
            + "Parameters: "
            + PREFIX_FILEPATH + "FILEPATH \n"
            + "Example: " + COMMAND_WORD_STAFFS + " "
            + PREFIX_FILEPATH + "E:/in/staff.csv";

    public static final String MESSAGE_USAGE_PURCHASE_ORDERS = COMMAND_WORD_PURCHASE_ORDERS
            + ": Import the purchase order list from a Csv file. "
            + "Parameters: "
            + PREFIX_FILEPATH + "FILEPATH \n"
            + "Example: " + COMMAND_WORD_PURCHASE_ORDERS + " "
            + PREFIX_FILEPATH + "E:/in/orders.csv";

    public static final String MESSAGE_USAGE = MESSAGE_USAGE_ITEMS + "\n"
            + MESSAGE_USAGE_SALES + "\n"
            + MESSAGE_USAGE_STAFFS + "\n"
            + MESSAGE_USAGE_PURCHASE_ORDERS;

    public static final String MESSAGE_SUCCESS_ITEMS = "Item list is importing from %s";

    public static final String MESSAGE_SUCCESS_SALES = "Sale list is importing from %s";

    public static final String MESSAGE_SUCCESS_STAFFS = "Staff list is importing from %s";

    public static final String MESSAGE_SUCCESS_PURCHASE_ORDERS = "Purchase order list is importing from %s";

    public static final String MESSAGE_INVALID_CSV_FILEPATH = "%s is not a valid csv file path";

    public static final String MESSAGE_INVALID_COMMAND_WORD = "Command word is invalid";

    public static final String MESSAGE_IMPORT = "Data is importing";

    private String commandWord;

    private final Path filePath;

    /**
     * Creates an ImportCsvCommand to import the data.
     */
    public ImportCsvCommand(Path filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    public ImportCsvCommand setCommandWord(String commandWord) {
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
            model.importItemList(filePath);
            model.commitInventory();
            return new CommandResult(String.format(MESSAGE_SUCCESS_ITEMS, filePath.toAbsolutePath()));
        case COMMAND_WORD_SALES:
            model.importSaleList(filePath);
            model.commitInventory();
            return new CommandResult(String.format(MESSAGE_SUCCESS_SALES, filePath.toAbsolutePath()));
        case COMMAND_WORD_STAFFS:
            model.importStaffList(filePath);
            model.commitInventory();
            return new CommandResult(String.format(MESSAGE_SUCCESS_STAFFS, filePath.toAbsolutePath()));
        case COMMAND_WORD_PURCHASE_ORDERS:
            model.importPurchaseOrderList(filePath);
            model.commitInventory();
            return new CommandResult(String.format(MESSAGE_SUCCESS_PURCHASE_ORDERS, filePath.toAbsolutePath()));
        default:
            throw new CommandException(MESSAGE_INVALID_COMMAND_WORD);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImportCsvCommand)) {
            return false;
        }
        ImportCsvCommand that = (ImportCsvCommand) o;
        if (this.commandWord == null) {
            return that.commandWord == null
                    && Objects.equals(filePath, that.filePath);
        }
        return Objects.equals(commandWord, that.commandWord)
                && Objects.equals(filePath, that.filePath);
    }
}
