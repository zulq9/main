package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;

/**
 * Adds a sale to the sale list.
 */
public class CreateSaleCommand extends Command {
    public static final String COMMAND_WORD = "createSale";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a sale to the system. "
            + "Parameters: "
            + PREFIX_SKU + "SKU "
            + PREFIX_QUANTITY + "QUANTITY";

    public static final String MESSAGE_SUCCESS = "New sale created: %1$s";

    private final Item saleItem;
    private final Quantity quantity;

    /**
     * Creates an CreateSaleCommand to add the specified {@code Item}
     */
    public CreateSaleCommand(Item saleItem, Quantity quantity) {
        requireAllNonNull(saleItem, quantity);

        this.saleItem = saleItem;
        this.quantity = quantity;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.createSale(saleItem, quantity);

        // TODO: RETURN SALE ID
        return new CommandResult(String.format(MESSAGE_SUCCESS, ""));
    }
}
