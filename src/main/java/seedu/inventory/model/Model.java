package seedu.inventory.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Item> PREDICATE_SHOW_ALL_ITEMS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<PurchaseOrder> PREDICATE_SHOW_ALL_PURCHASE_ORDER = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyInventory newData);

    /** Returns the Inventory */
    ReadOnlyInventory getInventory();

    //=========== Item API =============================================================

    /**
     * Returns true if a item with the same identity as {@code item} exists in the inventory.
     */
    boolean hasItem(Item item);

    /**
     * Deletes the given item.
     * The item must exist in the inventory.
     */
    void deleteItem(Item target);

    /**
     * Adds the given item.
     * {@code item} must not already exist in the inventory.
     */
    void addItem(Item item);

    /**
     * Replaces the given item {@code target} with {@code editedItem}.
     * {@code target} must exist in the inventory.
     * The item identity of {@code editedItem} must not be the same as another existing item in the inventory.
     */
    void updateItem(Item target, Item editedItem);

    /** Returns an unmodifiable view of the filtered item list */
    ObservableList<Item> getFilteredItemList();

    /**
     * Updates the filter of the filtered item list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredItemList(Predicate<Item> predicate);

    //=========== Purchase Order API =============================================================

    /**
     * Deletes the given purchase order.
     * The purchase order must exist in the inventory.
     */
    void deletePurchaseOrder(PurchaseOrder target);

    /**
     * Adds the given purchase order.
     * {@code purchaseOrder} must not already exist in the inventory.
     */
    void addPurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * Replaces the given purchaseOrder {@code target} with {@code editedPurchaseOrder}.
     * {@code target} must exist in the inventory.
     */
    void updatePurchaseOrder(PurchaseOrder target, PurchaseOrder editedPurchaseOrder);

    /** Returns an unmodifiable view of the filtered purchase order list */
    ObservableList<PurchaseOrder> getFilteredPurchaseOrderList();

    /**
     * Updates the filter of the filtered purchase order list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPurchaseOrderList(Predicate<PurchaseOrder> predicate);


    //=========== Undo/Redo Api =============================================================

    /**
     * Returns true if the model has previous inventory states to restore.
     */
    boolean canUndoInventory();

    /**
     * Returns true if the model has undone inventory states to restore.
     */
    boolean canRedoInventory();

    /**
     * Restores the model's inventory to its previous state.
     */
    void undoInventory();

    /**
     * Restores the model's inventory to its previously undone state.
     */
    void redoInventory();

    /**
     * Saves the current inventory state for undo/redo.
     */
    void commitInventory();
}
