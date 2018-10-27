package seedu.inventory.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.inventory.model.purchaseorder.NonUniquePurchaseOrderList;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * Wraps all data at the PurchaseOrderList level
 * Duplicates are allowed
 */
public class PurchaseOrderList implements ReadOnlyPurchaseOrderList {

    private final NonUniquePurchaseOrderList purchaseOrders;

    public PurchaseOrderList() {
        purchaseOrders = new NonUniquePurchaseOrderList();
    }

    /**
     * Creates a PurchaseOrderList using the PurchaseOrders in the {@code toBeCopied}.
     *
     * @param toBeCopied the ReadOnlyPurchaseOrderList to be copied into the PurchaseOrder list
     */
    public PurchaseOrderList(ReadOnlyPurchaseOrderList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the PurchaseOrder list with {@code PurchaseOrders}.
     * {@code PurchaseOrders} must not contain duplicate PurchaseOrders.
     *
     * @param PurchaseOrders the PurchaseOrders to be set into the UniquePurchaseOrderList.
     */
    public void setPurchaseOrders(List<PurchaseOrder> PurchaseOrders) {
        this.purchaseOrders.setPurchaseOrders(PurchaseOrders);
    }

    /**
     * Resets the existing data of this {@code PurchaseOrderList} with {@code newData}.
     *
     * @param newData the ReadOnlyPurchaseOrderList to be reset as a new data into the PurchaseOrder list.
     */
    public void resetData(ReadOnlyPurchaseOrderList newData) {
        requireNonNull(newData);

        setPurchaseOrders(newData.getPurchaseOrderList());
    }



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
