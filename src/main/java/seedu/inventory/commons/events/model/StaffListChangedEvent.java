package seedu.inventory.commons.events.model;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlyStaffList;

/**
 * Indicates the StaffList in the model has changed.
 */
public class StaffListChangedEvent extends BaseEvent {

    public final ReadOnlyStaffList data;

    /**
     * Constructs the StaffListChangedEvent.
     * @param data the staff list
     */
    public StaffListChangedEvent(ReadOnlyInventory data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of staffs " + data.getStaffList().size();
    }
}
