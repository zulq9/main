package seedu.inventory.commons.events.model;

import java.nio.file.Path;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.ReadOnlySaleList;

/**
 * Indicates the sale list in the model need to be exported
 */
public class SaleListExportEvent extends BaseEvent {
    public final ReadOnlySaleList data;
    public final Path filePath;

    public SaleListExportEvent(ReadOnlySaleList data, Path filePath) {
        this.data = data;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "number of sales " + data.getSaleList().size() + " to file:" + filePath.toString();
    }
}
