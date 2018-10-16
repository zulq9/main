package seedu.inventory.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.UniqueItemList;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.UniqueStaffList;

/**
 * Wraps all data at the inventory-list level
 * Duplicates are not allowed (by .isSameItem comparison)
 */
public class Inventory implements ReadOnlyInventory, ReadOnlyStaffList {

    private final UniqueItemList items = new UniqueItemList();
    private final UniqueStaffList staffs = new UniqueStaffList();

    public Inventory() {}

    /**
     * Creates an Inventory using the Items in the {@code toBeCopied}
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
    public void setItems(List<Item> items) {
        this.items.setItems(items);
    }

    /**
     * Resets the existing data of this {@code Inventory} with {@code newData}.
     */
    public void resetData(ReadOnlyInventory newData) {
        requireNonNull(newData);

        setItems(newData.getItemList());
    }

    //// item-level operations

    /**
     * Returns true if a item with the same identity as {@code item} exists in the inventory.
     */
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return items.contains(item);
    }

    /**
     * Adds a item to the inventory.
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

    // staff-level

    /**
     * Replaces the contents of the staff list with {@code staffs}
     *
     * @param staffs must not contain duplicated staffs.
     */
    public void setStaffs(List<Staff> staffs) {
        this.staffs.setStaffs(staffs);
    }

    /**
     * Resets the existing data of this {@code Inventory} with {@code newStaffData}.
     */
    public void resetStaffData(ReadOnlyStaffList newStaffData) {
        requireNonNull(newStaffData);

        setStaffs(newStaffData.getStaffList());
    }

    /**
     * Returns true if a staff with the same identity as {@code staff} exists in the inventory.
     * @return true if staff found
     */
    public boolean hasStaff(Staff staff) {
        requireNonNull(staff);
        return staffs.contains(staff);
    }

    /**
     * Adds a staff to the inventory management system.
     * @param s the staff to be added
     */
    public void addStaff(Staff s) {
        staffs.add(s);
    }

    /**
     * Updates the given staff {@code target} in the list with {@code editedStaff}.
     * {@code target} must exist in the inventory management system.
     */
    public void updateStaff(Staff target, Staff editedStaff) {
        requireNonNull(editedStaff);

        staffs.setStaff(target, editedStaff);
    }

    /**
     * Removes the provided staff {@code key} from the inventory manager.
     */
    public void removeStaff(Staff key) {
        staffs.remove(key);
    }

    //

    //// util methods

    @Override
    public String toString() {
        return items.asUnmodifiableObservableList().size() + " items\n" +
                staffs.asUnmodifiableObservableList().size() + "staffs\n";
        // TODO: refine later
    }

    @Override
    public ObservableList<Item> getItemList() {
        return items.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Staff> getStaffList() {
        return staffs.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Inventory // instanceof handles nulls
                && items.equals(((Inventory) other).items)
                && staffs.equals(((Inventory) other).staffs));
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
