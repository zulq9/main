package seedu.inventory.commons.events.storage;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.ReadOnlyPurchaseOrderList;

/**
 * Indicates an update of purchase order list.
 */
public class PurchaseOrderListUpdateEvent extends BaseEvent {

    public final ReadOnlyPurchaseOrderList purchaseOrderList;

    public PurchaseOrderListUpdateEvent(ReadOnlyPurchaseOrderList purchaseOrderList) {
        this.purchaseOrderList = purchaseOrderList;
    }

    @Override
    public String toString() {
        return "Purchase order List need update.";
    }

}
