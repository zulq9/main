package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.nio.file.Path;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;


/**
 * Import the item list from a Csv file.
 */
public class ImportCsvItemsCommand extends Command {

    public static final String COMMAND_WORD = "import-csv-items";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import the item list from a Csv file. "
            + "Parameters: "
            + PREFIX_FILEPATH + "FILEPATH \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILEPATH + "E:/in/items.csv";

    public static final String MESSAGE_SUCCESS = "Item list is importing from %s";

    private final Path filePath;

    /**
     * Creates an AddCommand to add the specified {@code Item}
     */
    public ImportCsvItemsCommand(Path filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.importItemList(filePath);

        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCsvItemsCommand // instanceof handles nulls
                && filePath.equals(((ImportCsvItemsCommand) other).filePath));
    }
}
