package seedu.inventory.model;

import seedu.inventory.model.item.Item;

/**
 * Unmodifiable view of an inventory
 */

public interface ReadOnlyInventory extends ReadOnlyItemList, ReadOnlyPurchaseOrderList, ReadOnlyStaffList {


    /**
     * Returns the specific item by Sku.
     */
    Item getItemBySku(String sku);
}
