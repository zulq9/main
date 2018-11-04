package seedu.inventory.logic.commands.sale;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.sale.Sale;

/**
 * Deletes a sale identified using it's displayed index from the inventory.
 */
public class DeleteSaleCommand extends Command {

    public static final String COMMAND_WORD = "delete-sale";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the sale identified by the sale ID.\n"
            + "Parameters: SALE ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SALE_NOT_FOUND = "Sale ID %1$s cannot be found.";
    public static final String MESSAGE_DELETE_SALE_SUCCESS = "Deleted Sale ID: %1$s";

    private final String saleId;

    public DeleteSaleCommand(String saleId) {
        requireAllNonNull(saleId);

        this.saleId = saleId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Optional<Sale> deleteSale = model.getObservableSaleList().stream().filter(sale -> sale.getSaleId().toString()
                .equalsIgnoreCase(saleId)).findFirst();

        Sale sale = deleteSale.orElse(null);

        if (sale == null) {
            throw new CommandException(String.format(MESSAGE_SALE_NOT_FOUND, saleId));
        }

        Item item = sale.getItem();

        // Add quantity only if item still exists
        Item searchedItem = model.getInventory().getItemBySku(item.getSku().toString());

        if (searchedItem != null) {
            int newIntQuantity = Integer.parseInt(searchedItem.getQuantity().toString())
                    + Integer.parseInt(sale.getSaleQuantity().toString());
            Quantity newQuantity = new Quantity(Integer.toString(newIntQuantity));

            // Add item quantity
            model.updateItem(searchedItem, new Item(item.getName(), item.getPrice(), newQuantity, item.getSku(),
                    item.getImage(), item.getTags()));
        }

        // Delete sale
        model.deleteSale(sale);

        model.commitInventory();

        return new CommandResult(String.format(MESSAGE_DELETE_SALE_SUCCESS, saleId));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSaleCommand // instanceof handles nulls
                && saleId.equals(((DeleteSaleCommand) other).saleId)); // state check
    }
}
