package seedu.inventory.commons.events.model;

import java.nio.file.Path;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.ReadOnlyStaffList;

/**
 * Indicates the staff list in the model need to be exported
 */
public class StaffListExportEvent extends BaseEvent {
    public final ReadOnlyStaffList data;
    public final Path filePath;

    public StaffListExportEvent(ReadOnlyStaffList data, Path filePath) {
        this.data = data;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "number of staffs " + data.getStaffList().size() + " to file:" + filePath.toString();
    }
}
