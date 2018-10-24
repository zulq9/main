package seedu.inventory.model;

import javafx.collections.ObservableList;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * Unmodifiable view of an inventory
 */
public interface ReadOnlyPurchaseOrderList extends ReadOnlyItemList {

    /**
     * Returns an unmodifiable view of the purchase order list.
     */
    ObservableList<PurchaseOrder> getPurchaseOrderList();


}
