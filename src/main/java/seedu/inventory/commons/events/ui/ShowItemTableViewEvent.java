package seedu.inventory.commons.events.ui;

import seedu.inventory.commons.events.BaseEvent;

/**
 * An event to show item table view.
 */
public class ShowItemTableViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
