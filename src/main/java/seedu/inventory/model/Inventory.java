package seedu.inventory.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.UniqueItemList;

/**
 * Wraps all data at the inventory-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Inventory implements ReadOnlyInventory {

    private final UniqueItemList persons;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueItemList();
    }

    public Inventory() {}

    /**
     * Creates an Inventory using the Persons in the {@code toBeCopied}
     */
    public Inventory(ReadOnlyInventory toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the item list with {@code items}.
     * {@code items} must not contain duplicate items.
     */
    public void setPersons(List<Item> items) {
        this.persons.setPersons(items);
    }

    /**
     * Resets the existing data of this {@code Inventory} with {@code newData}.
     */
    public void resetData(ReadOnlyInventory newData) {
        requireNonNull(newData);

        setPersons(newData.getItemList());
    }

    //// item-level operations

    /**
     * Returns true if a item with the same identity as {@code item} exists in the inventory book.
     */
    public boolean hasPerson(Item item) {
        requireNonNull(item);
        return persons.contains(item);
    }

    /**
     * Adds a item to the inventory book.
     * The item must not already exist in the inventory book.
     */
    public void addPerson(Item p) {
        persons.add(p);
    }

    /**
     * Replaces the given item {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in the inventory book.
     * The item identity of {@code editedItem} must not be the same as another existing item in the inventory book.
     */
    public void updatePerson(Item target, Item editedItem) {
        requireNonNull(editedItem);

        persons.setPerson(target, editedItem);
    }

    /**
     * Removes {@code key} from this {@code Inventory}.
     * {@code key} must exist in the inventory book.
     */
    public void removePerson(Item key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Item> getItemList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Inventory // instanceof handles nulls
                && persons.equals(((Inventory) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
