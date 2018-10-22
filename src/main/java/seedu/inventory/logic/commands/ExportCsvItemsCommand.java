package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.nio.file.Path;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;



/**
 * Export the item list to a Csv file.
 */
public class ExportCsvItemsCommand extends Command {

    public static final String COMMAND_WORD = "export-csv-items";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export the item list to a Csv file. "
            + "Parameters: "
            + PREFIX_FILEPATH + "FILEPATH \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILEPATH + "E:/out/items.csv";

    public static final String MESSAGE_SUCCESS = "Item list is exporting to %s";

    private final Path filePath;

    /**
     * Creates an AddCommand to add the specified {@code Item}
     */
    public ExportCsvItemsCommand(Path filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.exportItemList(filePath);

        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCsvItemsCommand // instanceof handles nulls
                && filePath.equals(((ExportCsvItemsCommand) other).filePath));
    }
}
