package seedu.inventory.commons.events.ui;

import seedu.inventory.commons.events.BaseEvent;

/**
 * An event to show purchase order table view.
 */
public class ShowPurchaseOrderTableViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
