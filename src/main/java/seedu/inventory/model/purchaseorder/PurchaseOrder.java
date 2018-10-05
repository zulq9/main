package seedu.inventory.model.purchaseorder;

import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.inventory.model.item.Item;

/**
 * Represents a purchase order entity.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class PurchaseOrder {

    // Identity field
    private final Item item;

    // Data fields
    private final Quantity quantity;
    private final RequiredDate reqDate;
    private final Supplier supplier;
    private final Status status;

    /**
     * Status that can be applied.
     */
    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }


    /**
     * Every field must be present and not null.
     */
    public PurchaseOrder(Item item, Quantity quantity, RequiredDate reqDate, Supplier supplier, Status status) {
        requireAllNonNull(item, quantity, reqDate, supplier, status);
        this.item = item;
        this.quantity = quantity;
        this.reqDate = reqDate;
        this.supplier = supplier;
        this.status = status;
    }

    public Item getItem() {
        return item;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public RequiredDate getReqDate() {
        return reqDate;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, reqDate, supplier);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" SKU: ")
                .append(getItem().getSku())
                .append(" Item name: ")
                .append(getItem().getName())
                .append(" Quantity: ")
                .append(getQuantity())
                .append(" Date Required: ")
                .append(getReqDate())
                .append(" Supplier: ")
                .append(getSupplier())
                .append(" Status: ")
                .append(getStatus());
        return builder.toString();
    }

}
