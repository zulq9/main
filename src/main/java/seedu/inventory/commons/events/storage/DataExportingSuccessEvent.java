package seedu.inventory.commons.events.storage;

import seedu.inventory.commons.events.BaseEvent;

/**
 * Indicates successful data exporting
 */
public class DataExportingSuccessEvent extends BaseEvent {

    @Override
    public String toString() {
        return "Data exporting successful.";
    }

}
