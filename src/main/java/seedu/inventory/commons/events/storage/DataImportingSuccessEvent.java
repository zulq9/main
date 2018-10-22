package seedu.inventory.commons.events.storage;

import seedu.inventory.commons.events.BaseEvent;

/**
 * Indicates an exception during a file saving
 */
public class DataImportingSuccessEvent extends BaseEvent {

    @Override
    public String toString() {
        return "Data exporting successful.";
    }

}
