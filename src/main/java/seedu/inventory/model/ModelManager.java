package seedu.inventory.model;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.inventory.commons.core.ComponentManager;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.events.model.InventoryChangedEvent;
import seedu.inventory.model.item.Item;

/**
 * Represents the in-memory model of the inventory book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedInventory versionedInventory;
    private final FilteredList<Item> filteredItems;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyInventory addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with inventory book: " + addressBook + " and user prefs " + userPrefs);

        versionedInventory = new VersionedInventory(addressBook);
        filteredItems = new FilteredList<>(versionedInventory.getItemList());
    }

    public ModelManager() {
        this(new Inventory(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyInventory newData) {
        versionedInventory.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyInventory getAddressBook() {
        return versionedInventory;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new InventoryChangedEvent(versionedInventory));
    }

    @Override
    public boolean hasPerson(Item item) {
        requireNonNull(item);
        return versionedInventory.hasPerson(item);
    }

    @Override
    public void deletePerson(Item target) {
        versionedInventory.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Item item) {
        versionedInventory.addPerson(item);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_ITEMS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Item target, Item editedItem) {
        requireAllNonNull(target, editedItem);

        versionedInventory.updatePerson(target, editedItem);
        indicateAddressBookChanged();
    }

    //=========== Filtered Item List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Item} backed by the internal list of
     * {@code versionedInventory}
     */
    @Override
    public ObservableList<Item> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredItems);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Item> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedInventory.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedInventory.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedInventory.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedInventory.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedInventory.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedInventory.equals(other.versionedInventory)
                && filteredItems.equals(other.filteredItems);
    }

}
