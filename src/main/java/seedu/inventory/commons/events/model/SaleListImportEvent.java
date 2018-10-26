package seedu.inventory.commons.events.model;

import java.nio.file.Path;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.ReadOnlyInventory;


/**
 * Indicates the sale list in the model need to be imported
 */
public class SaleListImportEvent extends BaseEvent {

    public final ReadOnlyInventory inventory;
    public final Path filePath;

    public SaleListImportEvent(ReadOnlyInventory inventory, Path filePath) {
        this.inventory = inventory;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Sale list is importing from " + filePath;
    }

}
