package seedu.inventory.model;

import javafx.collections.ObservableList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * Unmodifiable view of an inventory
 */
public interface ReadOnlyInventory {

    /**
     * Returns an unmodifiable view of the items list.
     * This list will not contain any duplicate items.
     */
    ObservableList<Item> getItemList();

    /**
     * Returns an unmodifiable view of the purchase order list.
     */
    ObservableList<PurchaseOrder> getPurchaseOrderList();

    /**
     * Returns the specific item by Sku.
     */
    Item getItemBySku(String sku);

}
