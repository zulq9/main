package seedu.inventory.commons.events.storage;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.ReadOnlyItemList;

/**
 * Indicates an exception during a file saving
 */
public class ItemListUpdateEvent extends BaseEvent {

    public final ReadOnlyItemList itemList;

    public ItemListUpdateEvent(ReadOnlyItemList itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "Data exporting successful.";
    }

}
