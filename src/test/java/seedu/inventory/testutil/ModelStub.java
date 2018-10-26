package seedu.inventory.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.inventory.commons.events.storage.ItemListUpdateEvent;
import seedu.inventory.commons.events.storage.PurchaseOrderListUpdateEvent;
import seedu.inventory.commons.events.storage.SaleListUpdateEvent;
import seedu.inventory.commons.events.storage.StaffListUpdateEvent;
import seedu.inventory.model.Model;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlyItemList;
import seedu.inventory.model.ReadOnlyPurchaseOrderList;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.staff.Staff;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void viewItem() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addItem(Item item) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resetData(ReadOnlyInventory newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resetItemList(ReadOnlyItemList newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resetSaleList(ReadOnlySaleList newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resetStaffList(ReadOnlyStaffList newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resetPurchaseOrderList(ReadOnlyPurchaseOrderList newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyInventory getInventory() {
        throw new AssertionError("This method should not be called.");
    }

    //===================== Report ===============================
    @Override
    public <T> FilteredList<T> getAccessedList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void exportItemList(Path filePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void importItemList(Path filePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void exportSaleList(Path filePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void importSaleList(Path filePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void exportStaffList(Path filePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void importStaffList(Path filePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void exportPurchaseOrderList(Path filePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void importPurchaseOrderList(Path filePath) {
        throw new AssertionError("This method should not be called.");
    }

    //========================= Item ======================================
    @Override
    public boolean hasItem(Item item) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteItem(Item target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateItem(Item target, Item editedItem) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Item> getFilteredItemList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredItemList(Predicate<Item> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canUndoInventory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedoInventory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void undoInventory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void redoInventory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void commitInventory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void viewPurchaseOrder() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPurchaseOrder(PurchaseOrder po) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPurchaseOrder(Item item) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePurchaseOrder(PurchaseOrder target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePurchaseOrder(Item target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPurchaseOrder(PurchaseOrder purchaseOrder) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updatePurchaseOrder(PurchaseOrder target, PurchaseOrder editedPurchaseOrder) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPurchaseOrderList(Predicate<PurchaseOrder> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<PurchaseOrder> getFilteredPurchaseOrderList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Sale> getObservableSaleList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addSale(Sale sale) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteSale(Sale sale) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void listSales() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlySaleList getSaleList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStaff(Staff staff) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStaff(Staff staff) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void viewStaff() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStaff(Staff target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void editStaff(Staff target, Staff editedStaff) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Staff> getFilteredStaffList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredStaffList(Predicate<Staff> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void authenticateUser(Staff toLogin) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void handleItemListUpdateEvent(ItemListUpdateEvent iue) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void handleSaleListUpdateEvent(SaleListUpdateEvent sue) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void handleStaffListUpdateEvent(StaffListUpdateEvent sue) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void handlePurchaseOrderListUpdateEvent(PurchaseOrderListUpdateEvent sue) {
        throw new AssertionError("This method should not be called.");
    }

}
