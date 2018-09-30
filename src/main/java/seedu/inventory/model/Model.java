package seedu.inventory.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.inventory.model.item.Item;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Item> PREDICATE_SHOW_ALL_ITEMS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyInventory newData);

    /** Returns the Inventory */
    ReadOnlyInventory getAddressBook();

    /**
     * Returns true if a item with the same identity as {@code item} exists in the inventory book.
     */
    boolean hasPerson(Item item);

    /**
     * Deletes the given item.
     * The item must exist in the inventory book.
     */
    void deletePerson(Item target);

    /**
     * Adds the given item.
     * {@code item} must not already exist in the inventory book.
     */
    void addPerson(Item item);

    /**
     * Replaces the given item {@code target} with {@code editedItem}.
     * {@code target} must exist in the inventory book.
     * The item identity of {@code editedItem} must not be the same as another existing item in the inventory book.
     */
    void updatePerson(Item target, Item editedItem);

    /** Returns an unmodifiable view of the filtered item list */
    ObservableList<Item> getFilteredPersonList();

    /**
     * Updates the filter of the filtered item list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Item> predicate);

    /**
     * Returns true if the model has previous inventory book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone inventory book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's inventory book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's inventory book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current inventory book state for undo/redo.
     */
    void commitAddressBook();
}
