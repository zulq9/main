package seedu.inventory.commons.events.ui;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.staff.Staff;

/**
 * Represents a selection change in the Staff List Panel
 */
public class StaffPanelSelectionChangedEvent extends BaseEvent {

    private final Staff newSelection;

    public StaffPanelSelectionChangedEvent(Staff newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Staff getNewSelection() {
        return newSelection;
    }

}
