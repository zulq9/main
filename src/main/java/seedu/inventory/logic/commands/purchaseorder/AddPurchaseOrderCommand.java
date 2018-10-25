package seedu.inventory.logic.commands.purchaseorder;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_REQDATE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SUPPLIER;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * Add a purchase order for an item.
 */

public class AddPurchaseOrderCommand extends Command {

    public static final String COMMAND_WORD = "add-po";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a purchase order for an item. "
            + "Parameters: "
            + PREFIX_SKU + "SKU "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_REQDATE + "REQUIRED_DATE "
            + PREFIX_SUPPLIER + "SUPPLIER "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SKU + "apple-iphone-xr "
            + PREFIX_QUANTITY + "32 "
            + PREFIX_REQDATE + "2018-12-12 "
            + PREFIX_SUPPLIER + "Apple Inc. ";

    public static final String MESSAGE_SUCCESS = "New purchase order Added: %1$s";
    public static final String MESSAGE_ITEM_NOT_FOUND = "Purchase order creation failed. SKU %1$s not found.";

    private final PurchaseOrder toAdd;

    /**
     * Creates an AddPurchaseOrderCommand to add the specified {@code PurchseOrder}
     */
    public AddPurchaseOrderCommand(PurchaseOrder po) {
        requireNonNull(po);
        toAdd = po;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Item item = model.getInventory().getItemBySku(toAdd.getSku().toString());

        if (item == null) {
            throw new CommandException(String.format(MESSAGE_ITEM_NOT_FOUND, toAdd.getSku().toString()));
        }

        model.addPurchaseOrder(toAdd);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPurchaseOrderCommand // instanceof handles nulls
                && toAdd.equals(((AddPurchaseOrderCommand) other).toAdd));
    }
}
