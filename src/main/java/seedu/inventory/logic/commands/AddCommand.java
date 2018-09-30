package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;

/**
 * Adds a item to the inventory book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to the inventory. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_SKU + "SKU "
            + PREFIX_IMAGE + "IMAGE PATH "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_QUANTITY + "98765432 "
            + PREFIX_SKU + "johnd@example.com "
            + PREFIX_IMAGE + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New item added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This item already exists in the inventory";

    private final Item toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Item}
     */
    public AddCommand(Item item) {
        requireNonNull(item);
        toAdd = item;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
