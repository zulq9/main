package seedu.inventory.commons.events.storage;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.ReadOnlySaleList;

/**
 * Indicates an update of sale list.
 */
public class SaleListUpdateEvent extends BaseEvent {

    public final ReadOnlySaleList saleList;

    public SaleListUpdateEvent(ReadOnlySaleList saleList) {
        this.saleList = saleList;
    }

    @Override
    public String toString() {
        return "Data exporting successful.";
    }

}
