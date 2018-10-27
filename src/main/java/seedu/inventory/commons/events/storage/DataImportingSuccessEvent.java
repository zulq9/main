package seedu.inventory.commons.events.storage;

import seedu.inventory.commons.events.BaseEvent;

/**
 * Indicates successful data importing
 */
public class DataImportingSuccessEvent extends BaseEvent {

    @Override
    public String toString() {
        return "Data importing successful.";
    }

}
