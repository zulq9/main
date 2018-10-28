package seedu.inventory.testutil.purchaseorder;

import seedu.inventory.logic.commands.purchaseorder.EditPurchaseOrderCommand.EditPoDescriptor;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.purchaseorder.RequiredDate;
import seedu.inventory.model.purchaseorder.Supplier;

/**
 * An utility class to help with building EditPurchaseOrderDescriptor objects.
 */
public class EditPurchaseOrderDescriptorBuilder {
    private EditPoDescriptor descriptor;

    public EditPurchaseOrderDescriptorBuilder() {
        this.descriptor = new EditPoDescriptor();
    }

    public EditPurchaseOrderDescriptorBuilder(EditPoDescriptor descriptor) {
        this.descriptor = new EditPoDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPurchaseOrderDescriptor} with fields containing {@code purchaseOrder}'s details
     */
    public EditPurchaseOrderDescriptorBuilder(PurchaseOrder purchaseOrder) {
        descriptor = new EditPoDescriptor();
        descriptor.setQuantity(purchaseOrder.getQuantity());
        descriptor.setReqDate(purchaseOrder.getReqDate());
        descriptor.setSupplier(purchaseOrder.getSupplier());
    }

    /**
     * Sets the {@code Quantity} of the {@code EditPurchaseOrderDescriptor} that we are building.
     */
    public EditPurchaseOrderDescriptorBuilder withQuantity(String qty) {
        descriptor.setQuantity(new Quantity(qty));
        return this;
    }

    /**
     * Sets the {@code RequiredDate} of the {@code EditPurchaseOrderDescriptor} that we are building.
     */
    public EditPurchaseOrderDescriptorBuilder withRequiredDate(String reqDate) {
        descriptor.setReqDate(new RequiredDate(reqDate));
        return this;
    }

    /**
     * Sets the {@code Supplier} of the {@code EditPurchaseOrderDescriptor} that we are building.
     */
    public EditPurchaseOrderDescriptorBuilder withSupplier(String supplier) {
        descriptor.setSupplier(new Supplier(supplier));
        return this;
    }

    public EditPoDescriptor build() {
        return descriptor;
    }


}
