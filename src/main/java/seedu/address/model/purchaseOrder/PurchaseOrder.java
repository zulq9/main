package seedu.address.model.purchaseOrder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.tag.Tag;

/**
 * Represents a purchase order entity.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class PurchaseOrder {

    // Identity field
    //private final Item item - To be updated when merged

    // Data fields
    private final PoQuantity quantity;
    private final RequiredDate reqDate;
    private final Supplier supplier;
    private final Tag tag;

    /**
     * Every field must be present and not null.
     */
    public PurchaseOrder(PoQuantity quantity, RequiredDate reqDate, Supplier supplier, Tag tag) {
        requireAllNonNull(quantity, reqDate, supplier, tag);
        this.quantity = quantity;
        this.reqDate = reqDate;
        this.supplier = supplier;
        this.tag = tag;
    }

    public PoQuantity getPoQuantity() {
        return quantity;
    }

    public RequiredDate getReqDate() {
        return reqDate;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Tag getTag() {
        return tag;
    }

    /**
     * Returns true if both purchase order have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PurchaseOrder)) {
            return false;
        }

        PurchaseOrder otherPsOdr = (PurchaseOrder) other;
        return otherPsOdr.getPoQuantity().equals(getPoQuantity())
                && otherPsOdr.getReqDate().equals(getReqDate())
                && otherPsOdr.getSupplier().equals(getSupplier())
                && otherPsOdr.getTag().equals(getTag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, reqDate, supplier, tag);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Quantity: ")
                .append(getPoQuantity())
                .append(" Date Required: ")
                .append(getReqDate())
                .append(" Supplier: ")
                .append(getSupplier())
                .append(" Description: ")
                .append(getTag());
        return builder.toString();
    }

}

