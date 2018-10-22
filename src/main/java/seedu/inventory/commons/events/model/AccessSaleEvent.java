package seedu.inventory.commons.events.model;

import seedu.inventory.commons.events.BaseEvent;

/**
 * Indicates a request for UI to change to sale list
 */
public class AccessSaleEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
