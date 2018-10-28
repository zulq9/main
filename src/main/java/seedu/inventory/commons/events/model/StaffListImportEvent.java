package seedu.inventory.commons.events.model;

import java.nio.file.Path;

import seedu.inventory.commons.events.BaseEvent;

/**
 * Indicates the staff list in the model need to be imported
 */
public class StaffListImportEvent extends BaseEvent {

    public final Path filePath;

    public StaffListImportEvent(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Sale list is importing from " + filePath;
    }

}
