package seedu.inventory.commons.events.model;

import java.nio.file.Path;

import seedu.inventory.commons.events.BaseEvent;

/**
 * Indicates the item list in the model need to be imported
 */
public class ItemListImportEvent extends BaseEvent {

    public final Path filePath;

    public ItemListImportEvent(Path filePath) {
        this.filePath = filePath;

    }

    @Override
    public String toString() {
        return "Item list is importing from " + filePath;
    }

}
