package seedu.inventory.commons.events.model;

import seedu.inventory.commons.events.BaseEvent;

/**
 * Indicates a request for UI to change to staff list
 */
public class AccessStaffEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
