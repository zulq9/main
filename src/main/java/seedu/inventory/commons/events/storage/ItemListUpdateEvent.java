package seedu.inventory.commons.events.storage;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.ReadOnlyItemList;

/**
 * Indicates an update of item list.
 */
public class ItemListUpdateEvent extends BaseEvent {

    public final ReadOnlyItemList itemList;

    public ItemListUpdateEvent(ReadOnlyItemList itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "Item List need update.";
    }

}
