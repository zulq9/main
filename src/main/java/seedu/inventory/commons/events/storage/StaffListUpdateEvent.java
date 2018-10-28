package seedu.inventory.commons.events.storage;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.ReadOnlyStaffList;

/**
 * Indicates an update of staff list.
 */
public class StaffListUpdateEvent extends BaseEvent {

    public final ReadOnlyStaffList staffList;

    public StaffListUpdateEvent(ReadOnlyStaffList staffList) {
        this.staffList = staffList;
    }

    @Override
    public String toString() {
        return "Staff List need update.";
    }

}
