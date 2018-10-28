package seedu.inventory.commons.events.model;

import java.nio.file.Path;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.ReadOnlyPurchaseOrderList;

/**
 * Indicates the purchase order list in the model need to be exported
 */
public class PurchaseOrderListExportEvent extends BaseEvent {
    public final ReadOnlyPurchaseOrderList data;
    public final Path filePath;

    public PurchaseOrderListExportEvent(ReadOnlyPurchaseOrderList data, Path filePath) {
        this.data = data;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "number of pos " + data.getPurchaseOrderList().size() + " to file:" + filePath.toString();
    }
}
