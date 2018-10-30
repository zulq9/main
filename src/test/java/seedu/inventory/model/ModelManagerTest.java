package seedu.inventory.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static seedu.inventory.model.Model.PREDICATE_SHOW_ALL_PURCHASE_ORDER;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.SAMSUNG;
import static seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder.IPHONEPO;
import static seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder.SAMSUNGNOTEPO;
import static seedu.inventory.testutil.staff.TypicalStaffs.ZUL;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.commons.events.model.AccessItemEvent;
import seedu.inventory.commons.events.model.AccessPurchaseOrderEvent;
import seedu.inventory.commons.events.model.AccessSaleEvent;
import seedu.inventory.commons.events.model.AccessStaffEvent;
import seedu.inventory.model.item.NameContainsKeywordsPredicate;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.testutil.InventoryBuilder;
import seedu.inventory.ui.testutil.EventsCollectorRule;

public class ModelManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void resetData_nullItemList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.resetData(null);
    }

    @Test
    public void resetItemList_nullItemList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.resetItemList(null);
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasItem(null);
    }

    @Test
    public void hasStaff_staffNotInInventory_returnsFalse() {
        assertFalse(modelManager.hasStaff(ZUL));
    }

    @Test
    public void hasStaff_staffInInventory_returnsTrue() {
        modelManager.addStaff(ZUL);
        assertTrue(modelManager.hasStaff(ZUL));
    }

    @Test
    public void getFilteredStaffList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredStaffList().remove(0);
    }

    @Test
    public void resetData_nullStaffList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.resetData(null);
    }

    @Test
    public void hasItem_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasStaff(null);
    }

    @Test
    public void hasItem_itemNotInInventory_returnsFalse() {
        assertFalse(modelManager.hasItem(IPHONE));
    }

    @Test
    public void hasItem_itemInInventory_returnsTrue() {
        modelManager.addItem(IPHONE);
        assertTrue(modelManager.hasItem(IPHONE));
    }

    @Test
    public void getFilteredInventoryList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredItemList().remove(0);
    }

    @Test
    public void hasPurchaseOrder_nullPurchaseOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPurchaseOrder((PurchaseOrder) null);
    }

    @Test
    public void hasPurchaseOrder_purchaseOrderNotInInventory_returnsFalse() {
        assertFalse(modelManager.hasPurchaseOrder(IPHONEPO));
    }

    @Test
    public void hasPurchaseOrder_purchaseOrderInInventory_returnsFalse() {
        modelManager.addPurchaseOrder(IPHONEPO);
        assertFalse(modelManager.hasPurchaseOrder(IPHONEPO));
    }

    @Test
    public void hasPurchaseOrder_purchaseOrderInInventory_returnsTrue() {
        modelManager.addItem(IPHONE);
        modelManager.addPurchaseOrder(IPHONEPO);
        assertTrue(modelManager.hasPurchaseOrder(IPHONEPO));
        assertTrue(modelManager.hasPurchaseOrder(IPHONE));
    }

    @Test
    public void deletePurchaseOrder() {
        modelManager.addItem(IPHONE);
        assertFalse(modelManager.hasPurchaseOrder(IPHONEPO));
        modelManager.addPurchaseOrder(IPHONEPO);
        assertTrue(modelManager.hasPurchaseOrder(IPHONEPO));
        modelManager.deletePurchaseOrder(IPHONEPO);
        assertFalse(modelManager.hasPurchaseOrder(IPHONEPO));

        modelManager.addPurchaseOrder(IPHONEPO);
        modelManager.deletePurchaseOrder(IPHONE);
        assertFalse(modelManager.hasPurchaseOrder(IPHONEPO));
    }

    @Test
    public void getFilteredPurchaseOrderList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPurchaseOrderList().remove(0);
    }

    @Test
    public void equals() {
        Inventory inventory = new InventoryBuilder()
                .withItem(IPHONE)
                .withItem(SAMSUNG)
                .withPurchaseOrder(IPHONEPO)
                .withPurchaseOrder(SAMSUNGNOTEPO)
                .build();
        Inventory differentInventory = new Inventory();
        UserPrefs userPrefs = new UserPrefs();
        SaleList saleList = new SaleList();

        // same values -> returns true
        modelManager = new ModelManager(inventory, userPrefs, saleList);
        ModelManager modelManagerCopy = new ModelManager(inventory, userPrefs, saleList);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager == null);

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different inventory -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentInventory, userPrefs, saleList)));

        // different filteredItemList -> returns false
        String[] keywords = IPHONE.getName().fullName.split("\\s+");
        modelManager.updateFilteredItemList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(inventory, userPrefs, saleList)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        modelManager.updateFilteredPurchaseOrderList(PREDICATE_SHOW_ALL_PURCHASE_ORDER);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setInventoryFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(inventory, differentUserPrefs, saleList)));
    }

    //=========== Reporting  ===============================================================================
    @Test
    public void exportItemList() {
        modelManager.exportItemList(Paths.get("dummy"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof AccessItemEvent);
    }

    @Test
    public void importItemList() {
        modelManager.importItemList(Paths.get("dummy"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof AccessItemEvent);
    }

    @Test
    public void exportSaleList() {
        modelManager.exportSaleList(Paths.get("dummy"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof AccessSaleEvent);
    }

    @Test
    public void importSaleList() {
        modelManager.importSaleList(Paths.get("dummy"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof AccessSaleEvent);
    }

    @Test
    public void exportStaffList() {
        modelManager.exportStaffList(Paths.get("dummy"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof AccessStaffEvent);
    }

    @Test
    public void importStaffList() {
        modelManager.importStaffList(Paths.get("dummy"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof AccessStaffEvent);
    }

    @Test
    public void exportPurchaseOrderList() {
        modelManager.exportPurchaseOrderList(Paths.get("dummy"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof AccessPurchaseOrderEvent);
    }

    @Test
    public void importPurchaseOrderList() {
        modelManager.importPurchaseOrderList(Paths.get("dummy"));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof AccessPurchaseOrderEvent);
    }
}
