package seedu.inventory.commons.events.model;

import java.nio.file.Path;

import seedu.inventory.commons.events.BaseEvent;

/**
 * Indicates an exception during a file saving
 */
public class ItemListImportEvent extends BaseEvent {

    public final Path filePath;

    public ItemListImportEvent(Path filePath) {
        this.filePath = filePath;

    }

    @Override
    public String toString() {
        return "Data exporting successful.";
    }

}
