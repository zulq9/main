package seedu.inventory.logic.commands.purchaseorder;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_REQDATE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SUPPLIER;
import static seedu.inventory.model.Model.PREDICATE_SHOW_ALL_PURCHASE_ORDER;

import java.util.List;
import java.util.Optional;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.commons.util.CollectionUtil;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.EditItemCommand;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.purchaseorder.RequiredDate;
import seedu.inventory.model.purchaseorder.Supplier;

/**
 *
 */
public class EditPurchaseOrderCommand extends Command {

    public static final String COMMAND_WORD = "edit-po";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the purchase order identified "
            + "by the index number used in the displayed purchase order list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters that is editable: INDEX (must be a positive integer) "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_REQDATE + "REQUIRED DATE] "
            + "[" + PREFIX_SUPPLIER + "SUPPLIER]\n "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUANTITY + "100 "
            + PREFIX_REQDATE + "2018-12-12 "
            + PREFIX_SUPPLIER + "Apple Inc.";

    public static final String MESSAGE_EDIT_PO_SUCCESS = "Edited Purchase Order: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditPurchaseOrderCommand.EditPoDescriptor editPoDescriptor;

    /**
     * @param index            of the purchase order in the filtered purchase order list to edit
     * @param editPoDescriptor details to edit the purchase order with
     */
    public EditPurchaseOrderCommand(Index index, EditPurchaseOrderCommand.EditPoDescriptor editPoDescriptor) {
        requireNonNull(index);
        requireNonNull(editPoDescriptor);

        this.index = index;
        this.editPoDescriptor = new EditPurchaseOrderCommand.EditPoDescriptor(editPoDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<PurchaseOrder> lastShownList = model.getFilteredPurchaseOrderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PO_DISPLAYED_INDEX);
        }

        PurchaseOrder purchaseOrderToEdit = lastShownList.get(index.getZeroBased());

        if (!purchaseOrderToEdit.getStatus().equals(PurchaseOrder.Status.PENDING)) {
            throw new CommandException(Messages.MESSAGE_SELECT_PENDING);
        }

        PurchaseOrder editedPurchaseOrder = createEditedPo(purchaseOrderToEdit, editPoDescriptor);

        model.updatePurchaseOrder(purchaseOrderToEdit, editedPurchaseOrder);
        model.updateFilteredPurchaseOrderList(PREDICATE_SHOW_ALL_PURCHASE_ORDER);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_EDIT_PO_SUCCESS, editedPurchaseOrder));
    }

    /**
     * Creates and returns a {@code PurchaseOrder} with the details of {@code poToEdit}
     * edited with {@code editPoDescriptor}.
     */
    private static PurchaseOrder createEditedPo(PurchaseOrder poToEdit,
                                                EditPurchaseOrderCommand.EditPoDescriptor editPoDescriptor) {
        assert poToEdit != null;

        Quantity updatedQuantity = editPoDescriptor.getQuantity().orElse(poToEdit.getQuantity());
        RequiredDate updatedReqDate = editPoDescriptor.getRequiredDate().orElse(poToEdit.getReqDate());
        Supplier updatedSupplier = editPoDescriptor.getSupplier().orElse(poToEdit.getSupplier());

        return new PurchaseOrder(poToEdit.getSku(), updatedQuantity, updatedReqDate, updatedSupplier,
                poToEdit.getStatus());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditItemCommand)) {
            return false;
        }

        // state check
        EditPurchaseOrderCommand e = (EditPurchaseOrderCommand) other;
        return index.equals(e.index)
                && editPoDescriptor.equals(e.editPoDescriptor);
    }

    /**
     * Stores the details to edit the item with. Each non-empty field value will replace the
     * corresponding field value of the item.
     */
    public static class EditPoDescriptor {
        private Quantity quantity;
        private RequiredDate reqDate;
        private Supplier supplier;

        public EditPoDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPoDescriptor(EditPurchaseOrderCommand.EditPoDescriptor toCopy) {
            setQuantity(toCopy.quantity);
            setReqDate(toCopy.reqDate);
            setSupplier(toCopy.supplier);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(quantity, reqDate, supplier);
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public void setReqDate(RequiredDate reqDate) {
            this.reqDate = reqDate;
        }

        public Optional<RequiredDate> getRequiredDate() {
            return Optional.ofNullable(reqDate);
        }

        public void setSupplier(Supplier supplier) {
            this.supplier = supplier;
        }

        public Optional<Supplier> getSupplier() {
            return Optional.ofNullable(supplier);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPurchaseOrderCommand.EditPoDescriptor)) {
                return false;
            }

            // state check
            EditPurchaseOrderCommand.EditPoDescriptor e = (EditPurchaseOrderCommand.EditPoDescriptor) other;

            return getQuantity().equals(e.getQuantity())
                    && getRequiredDate().equals(e.getRequiredDate())
                    && getSupplier().equals(e.getSupplier());
        }
    }
}
