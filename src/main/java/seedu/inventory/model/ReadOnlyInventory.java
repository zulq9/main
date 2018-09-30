package seedu.inventory.model;

import javafx.collections.ObservableList;
import seedu.inventory.model.item.Item;

/**
 * Unmodifiable view of an inventory book
 */
public interface ReadOnlyInventory {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Item> getItemList();

}
