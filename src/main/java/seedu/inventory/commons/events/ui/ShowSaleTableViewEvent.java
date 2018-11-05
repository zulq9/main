package seedu.inventory.commons.events.ui;

import seedu.inventory.commons.events.BaseEvent;

/**
 * An event to show sale table view.
 */
public class ShowSaleTableViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
