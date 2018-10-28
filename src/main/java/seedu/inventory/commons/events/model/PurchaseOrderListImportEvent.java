package seedu.inventory.commons.events.model;

import java.nio.file.Path;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.ReadOnlyInventory;


/**
 * Indicates the purchase order list in the model need to be imported
 */
public class PurchaseOrderListImportEvent extends BaseEvent {

    public final ReadOnlyInventory inventory;
    public final Path filePath;

    public PurchaseOrderListImportEvent(ReadOnlyInventory inventory, Path filePath) {
        this.inventory = inventory;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Purchase order list is importing from " + filePath;
    }

}
