package seedu.inventory.commons.events.storage;

import seedu.inventory.commons.events.BaseEvent;

/**
 * Indicates an exception during a file saving
 */
public class DataExportingSuccessEvent extends BaseEvent {

    public DataExportingSuccessEvent() {

    }

    @Override
    public String toString() {
        return "Data exporting successful.";
    }

}
