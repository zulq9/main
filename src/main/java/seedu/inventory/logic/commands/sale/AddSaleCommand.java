package seedu.inventory.logic.commands.sale;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;

/**
 * Adds a sale to the sale list.
 */
public class AddSaleCommand extends Command {
    public static final String COMMAND_WORD = "add-sale";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a sale to the system. "
            + "Parameters: "
            + PREFIX_SKU + "SKU "
            + PREFIX_QUANTITY + "QUANTITY";
    public static final String MESSAGE_ITEM_NOT_FOUND = "Sale creation failed. SKU %1$s not found.";
    public static final String MESSAGE_QUANTITY_INSUFFICIENT = "Quantity insufficient. Only %1$s available.";
    public static final String MESSAGE_SUCCESS = "New sale created, sale ID %1$s, item %2$s.";

    private final Sku sku;
    private final Quantity quantity;

    /**
     * Creates an AddSaleCommand to add the specified {@code Item}
     */
    public AddSaleCommand(Sku sku, Quantity quantity) {
        requireAllNonNull(sku, quantity);

        this.sku = sku;
        this.quantity = quantity;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        SaleId saleId = new SaleId(model.getSaleList().getNextSaleId());
        Item item = model.getInventory().getItemBySku(sku.toString());
        SaleDate saleDate = new SaleDate(formatter.format(date));

        // Check if item exists
        if (item == null) {
            throw new CommandException(String.format(MESSAGE_ITEM_NOT_FOUND, sku.toString()));
        }

        // Check if item quantity enough
        if (Integer.parseInt(item.getQuantity().toString()) < Integer.parseInt(quantity.toString())) {
            throw new CommandException(String.format(MESSAGE_QUANTITY_INSUFFICIENT, item.getQuantity()));
        }

        Sale sale = new Sale(saleId, item, quantity, saleDate);

        model.addSale(sale);

        int newIntQuantity = Integer.parseInt(item.getQuantity().toString()) - Integer.parseInt(quantity.toString());
        Quantity newQuantity = new Quantity(Integer.toString(newIntQuantity));

        // Deduct item quantity
        model.updateItem(item, new Item(item.getName(), item.getPrice(), newQuantity, item.getSku(),
                item.getImage(), item.getTags()));

        return new CommandResult(String.format(MESSAGE_SUCCESS, saleId.toString(), item.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddSaleCommand // instanceof handles nulls
                && sku.equals(((AddSaleCommand) other).sku)
                && quantity.equals(((AddSaleCommand) other).quantity));
    }
}
