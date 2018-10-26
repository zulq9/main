package seedu.inventory.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import seedu.inventory.model.purchaseorder.NonUniquePurchaseOrderList;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * Wraps all data at the PurchaseOrderList level
 * Duplicates are allowed
 */
public class PurchaseOrderList implements ReadOnlyPurchaseOrderList {

    private final NonUniquePurchaseOrderList purchaseOrders = new NonUniquePurchaseOrderList();

    /**
     * Returns true if a purchase order with the same identity as {@code po} exists in the purchase order list.
     */
    public boolean hasPurchaseOrder(PurchaseOrder po) {
        requireNonNull(po);
        return purchaseOrders.contains(po);
    }

    /**
     * Adds a purchase order to the purchase order list.
     */
    public void addPurchaseOrder(PurchaseOrder po) {
        requireNonNull(po);
        purchaseOrders.add(po);
    }

    /**
     * Replaces the given item {@code target} in the list with {@code editedPurchaseOrder}.
     * {@code target} must exist in the purchase order list.
     */
    public void updatePurchaseOrder(PurchaseOrder target, PurchaseOrder editedPurchaseOrder) {
        requireNonNull(editedPurchaseOrder);

        purchaseOrders.setPurchaseOrder(target, editedPurchaseOrder);
    }

    /**
     * Removes {@code key} from this {@code PurchaseOrderList}.
     * {@code key} must exist in the purchase order list.
     */
    public void removePurchaseOrder(PurchaseOrder key) {
        purchaseOrders.remove(key);
    }

    @Override
    public ObservableList<PurchaseOrder> getPurchaseOrderList() {
        return purchaseOrders.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PurchaseOrderList
                        && purchaseOrders.equals(((PurchaseOrderList) other).purchaseOrders));
    }
}
