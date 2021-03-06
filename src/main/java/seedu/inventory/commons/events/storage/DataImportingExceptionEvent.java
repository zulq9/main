package seedu.inventory.commons.events.storage;

import seedu.inventory.commons.events.BaseEvent;

/**
 * Indicates an exception during data importing
 */
public class DataImportingExceptionEvent extends BaseEvent {

    public final Exception exception;

    public DataImportingExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return exception.toString();
    }

}
