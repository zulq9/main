package seedu.inventory.commons.events.ui;

import seedu.inventory.commons.events.BaseEvent;

/**
 * An event to show staff table view.
 */
public class ShowStaffTableViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
