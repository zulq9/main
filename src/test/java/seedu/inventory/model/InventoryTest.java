package seedu.inventory.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_IMAGE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_SMARTPHONE;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.NOKIA;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;
import static seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder.IPHONEPO;
import static seedu.inventory.testutil.staff.TypicalStaffs.ZUL;
import static seedu.inventory.testutil.staff.TypicalStaffs.getTypicalStaffList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.exceptions.DuplicateItemException;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.exceptions.DuplicateStaffException;
import seedu.inventory.testutil.ItemBuilder;
import seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder;
import seedu.inventory.testutil.staff.StaffBuilder;

public class InventoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Inventory inventory = new Inventory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), inventory.getItemList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        inventory.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyInventory_replacesData() {
        Inventory newData = getTypicalInventory();
        inventory.resetData(newData);
        assertEquals(newData, inventory);
    }

    @Test
    public void resetItemList_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        inventory.resetItemList(null);
    }

    @Test
    public void resetItemList_withValidReadOnlyInventory_replacesData() {
        ReadOnlyItemList itemList = getTypicalInventory();
        inventory.resetItemList(itemList);
        assertEquals(itemList, inventory);
    }

    @Test
    public void resetStaffList_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        inventory.resetStaffList(null);
    }

    @Test
    public void resetStaffList_withValidReadOnlyInventory_replacesData() {
        ReadOnlyStaffList staffList = getTypicalStaffList();
        inventory.resetStaffList(staffList);
        assertEquals(staffList, inventory);
    }

    @Test
    public void resetPurchaseOrderList_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        inventory.resetPurchaseOrderList(null);
    }

    @Test
    public void resetPurchaseOrderList_withValidReadOnlyInventory_replacesData() {
        ReadOnlyPurchaseOrderList purchaseOrderList = TypicalPurchaseOrder.getTypicalInventory();
        inventory.resetPurchaseOrderList(purchaseOrderList);
        ReadOnlyItemList itemList = getTypicalInventory();
        inventory.resetItemList(itemList);
        assertEquals(purchaseOrderList, inventory);
    }

    ////===================== Item ==================================================================

    @Test
    public void resetData_withDuplicateItems_throwsDuplicateItemException() {
        // Two items with the same identity fields
        Item editedAlice = new ItemBuilder(IPHONE).withImage(VALID_IMAGE_SONY).withTags(VALID_TAG_SMARTPHONE)
                .build();
        List<Item> newItems = Arrays.asList(IPHONE, editedAlice);
        List<PurchaseOrder> newPurchaseOrder = Arrays.asList(IPHONEPO);
        List<Staff> newStaff = Arrays.asList(ZUL);
        InventoryStub newData = new InventoryStub(newItems, newPurchaseOrder, newStaff);

        thrown.expect(DuplicateItemException.class);
        inventory.resetData(newData);
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        inventory.hasItem(null);
    }

    @Test
    public void hasItem_itemNotInInventory_returnsFalse() {
        assertFalse(inventory.hasItem(IPHONE));
    }

    @Test
    public void hasItem_itemInInventory_returnsTrue() {
        inventory.addItem(IPHONE);
        assertTrue(inventory.hasItem(IPHONE));
    }

    @Test
    public void hasItem_itemWithSameIdentityFieldsInInventory_returnsTrue() {
        inventory.addItem(IPHONE);
        Item editedAlice = new ItemBuilder(IPHONE).withImage(VALID_IMAGE_SONY).withTags(VALID_TAG_SMARTPHONE)
                .build();
        assertTrue(inventory.hasItem(editedAlice));
    }

    @Test
    public void getItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        inventory.getItemList().remove(0);
    }

    @Test
    public void removeItem() {
        assertFalse(inventory.hasItem(IPHONE));
        inventory.addItem(IPHONE);
        assertTrue(inventory.hasItem(IPHONE));
        inventory.removeItem(IPHONE);
        assertFalse(inventory.hasItem(IPHONE));
    }

    @Test
    public void updateItem() {
        inventory.addItem(IPHONE);
        assertTrue(inventory.hasItem(IPHONE));
        assertFalse(inventory.hasItem(NOKIA));
        inventory.updateItem(IPHONE, NOKIA);
        assertFalse(inventory.hasItem(IPHONE));
        assertTrue(inventory.hasItem(NOKIA));
    }

    ////===================== Purchase Order ==================================================================

    @Test
    public void hasPurchaseOrder_nullPurchaseOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        inventory.hasPurchaseOrder((PurchaseOrder) null);
    }

    @Test
    public void hasPurchaseOrder_purchaseOrderNotInInventory_returnsFalse() {
        assertFalse(inventory.hasPurchaseOrder(IPHONEPO));
    }

    @Test
    public void hasPurchaseOrder_purchaseOrderInInventory_returnsFalse() {
        inventory.addPurchaseOrder(IPHONEPO);
        assertFalse(inventory.hasPurchaseOrder(IPHONEPO));
    }

    @Test
    public void hasPurchaseOrder_purchaseOrderInInventory_returnsTrue() {
        inventory.addItem(IPHONE);
        inventory.addPurchaseOrder(IPHONEPO);
        assertTrue(inventory.hasPurchaseOrder(IPHONEPO));
        assertTrue(inventory.hasPurchaseOrder(IPHONE));
    }

    @Test
    public void getPurchaseOrderList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        inventory.getPurchaseOrderList().remove(0);
    }

    @Test
    public void removePurchaseOrder() {
        inventory.addItem(IPHONE);
        assertFalse(inventory.hasPurchaseOrder(IPHONEPO));
        inventory.addPurchaseOrder(IPHONEPO);
        assertTrue(inventory.hasPurchaseOrder(IPHONEPO));
        inventory.removePurchaseOrder(0);
        assertFalse(inventory.hasPurchaseOrder(IPHONEPO));
    }


    ////===================== Staff ==================================================================

    @Test
    public void resetData_withDuplicateStaffs_throwsDuplicateStaffException() {
        // Two items with the same identity fields
        Staff editedZul = new StaffBuilder(ZUL).withName(VALID_NAME_ZUL).withRole(Staff.Role.user)
                .build();
        List<Item> newItems = Arrays.asList(IPHONE);
        List<PurchaseOrder> newPurchaseOrder = Arrays.asList(IPHONEPO);
        List<Staff> newStaffs = Arrays.asList(ZUL, editedZul);
        InventoryStub newData = new InventoryStub(newItems, newPurchaseOrder, newStaffs);

        thrown.expect(DuplicateStaffException.class);
        inventory.resetData(newData);
    }

    @Test
    public void hasStaff_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        inventory.hasStaff(null);
    }

    @Test
    public void hasStaff_staffNotInInventory_returnsFalse() {
        assertFalse(inventory.hasStaff(ZUL));
    }

    @Test
    public void hasStaff_staffInInventory_returnsTrue() {
        inventory.addStaff(ZUL);
        assertTrue(inventory.hasStaff(ZUL));
    }

    @Test
    public void hasStaff_staffWithSameIdentityFieldsInInventory_returnsTrue() {
        inventory.addStaff(ZUL);
        Staff editedZul = new StaffBuilder(ZUL).withName(VALID_NAME_ZUL).withRole(Staff.Role.user)
                .build();
        assertTrue(inventory.hasStaff(editedZul));
    }

    @Test
    public void getStaffList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        inventory.getStaffList().remove(0);
    }

    /**
     * A stub ReadOnlyInventory whose items list can violate interface constraints.
     */
    private static class InventoryStub implements ReadOnlyInventory {
        private final ObservableList<Item> items = FXCollections.observableArrayList();
        private final ObservableList<PurchaseOrder> purchaseOrders = FXCollections.observableArrayList();
        private final ObservableList<Staff> staffs = FXCollections.observableArrayList();
        private final ObservableList<Sale> sales = FXCollections.observableArrayList();

        InventoryStub(Collection<Item> items, Collection<PurchaseOrder> purchaseOrders, Collection<Staff> staffs) {
            this.items.setAll(items);
            this.purchaseOrders.setAll(purchaseOrders);
            this.staffs.setAll(staffs);
        }

        @Override
        public ObservableList<Item> getItemList() {
            return items;
        }

        @Override
        public ObservableList<PurchaseOrder> getPurchaseOrderList() {
            return purchaseOrders;
        }

        public ObservableList<Staff> getStaffList() {
            return staffs;
        }

        public String getNextSaleId() {
            return "";
        }

        @Override
        public ObservableList<Sale> getSaleList() {
            return sales;
        }

        @Override
        public Item getItemBySku(String sku) {
            return null;
        }
    }

}
