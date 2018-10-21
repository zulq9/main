package seedu.inventory.model;

import javafx.collections.ObservableList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.UniqueItemList;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.UniqueSaleList;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the sale-list level
 */
public class ItemList implements ReadOnlyItemList {

    private final UniqueItemList items = new UniqueItemList();

    public ItemList() {}

    /**
     * Creates a UniqueSaleList using the sales in the {@code toBeCopied}
     */
    public ItemList(ReadOnlyItemList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the item list with {@code items}.
     * {@code items} must not contain duplicate items.
     */
    public void setItems(List<Item> items) {
        this.items.setItems(items);
    }


    /**
     * Resets the existing data of this {@code UniqueItemList} with {@code newData}.
     */
    public void resetData(ReadOnlyItemList newData) {
        requireNonNull(newData);

        setItems(newData.getItemList());
    }

    //===================== item-level operations ==========================================

    /**
     * Returns true if a item with the same identity as {@code item} exists in the inventory.
     */
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return items.contains(item);
    }

    /**
     * Adds an item to the inventory.
     * The item must not already exist in the inventory.
     */
    public void addItem(Item p) {
        items.add(p);
    }

    /**
     * Replaces the given item {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in the inventory.
     * The item identity of {@code editedItem} must not be the same as another existing item in the inventory.
     */
    public void updateItem(Item target, Item editedItem) {
        requireNonNull(editedItem);

        items.setItem(target, editedItem);
    }

    /**
     * Removes {@code key} from this {@code Inventory}.
     * {@code key} must exist in the inventory.
     */
    public void removeItem(Item key) {
        items.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return items.asUnmodifiableObservableList().size() + " items.";
    }

    @Override
    public ObservableList<Item> getItemList() {
        return items.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemList // instanceof handles nulls
                && items.equals(((ItemList) other).items));
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
