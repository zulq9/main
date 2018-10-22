package seedu.inventory.commons.events.ui;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.sale.Sale;

/**
 * Represents a selection change in the Sale List Panel
 */
public class SalePanelSelectionChangedEvent extends BaseEvent {


    private final Sale newSelection;

    public SalePanelSelectionChangedEvent(Sale newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Sale getNewSelection() {
        return newSelection;
    }
}
