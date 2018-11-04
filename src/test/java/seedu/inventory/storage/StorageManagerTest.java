package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;
import static seedu.inventory.testutil.staff.TypicalStaffs.getTypicalStaffList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.inventory.commons.events.model.InventoryChangedEvent;
import seedu.inventory.commons.events.model.ItemListExportEvent;
import seedu.inventory.commons.events.model.ItemListImportEvent;
import seedu.inventory.commons.events.model.PurchaseOrderListExportEvent;
import seedu.inventory.commons.events.model.PurchaseOrderListImportEvent;
import seedu.inventory.commons.events.model.SaleListExportEvent;
import seedu.inventory.commons.events.model.SaleListImportEvent;
import seedu.inventory.commons.events.model.StaffListExportEvent;
import seedu.inventory.commons.events.model.StaffListImportEvent;
import seedu.inventory.commons.events.storage.DataExportingExceptionEvent;
import seedu.inventory.commons.events.storage.DataImportingExceptionEvent;
import seedu.inventory.commons.events.storage.DataSavingExceptionEvent;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.ItemList;
import seedu.inventory.model.PurchaseOrderList;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlyItemList;
import seedu.inventory.model.ReadOnlyPurchaseOrderList;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.StaffList;
import seedu.inventory.model.UserPrefs;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;
import seedu.inventory.storage.csv.CsvReportingStorage;
import seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder;
import seedu.inventory.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlInventoryStorage inventoryManagerStorage = new XmlInventoryStorage(
                getTempFilePath("ab"), getTempFilePath("cd"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        XmlSaleListStorage saleListStorage = new XmlSaleListStorage();
        CsvReportingStorage reportingStorage = new CsvReportingStorage();
        storageManager = new StorageManager(inventoryManagerStorage, userPrefsStorage, saleListStorage,
                reportingStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void inventoryReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlInventoryStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlInventoryStorageTest} class.
         */
        Inventory original = getTypicalInventory();
        storageManager.saveInventory(original);
        ReadOnlyInventory retrieved = storageManager.readInventory().get();
        assertEquals(original, new Inventory(retrieved));
    }

    @Test
    public void getInventoryFilePath() {
        assertNotNull(storageManager.getInventoryFilePath());
    }

    @Test
    public void handleInventoryChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlInventoryStorageExceptionThrowingStub(
                Paths.get("dummy"), Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")),
                                             new XmlSaleListStorage(),
                                             new CsvReportingStorage());
        storage.handleInventoryChangedEvent(new InventoryChangedEvent(new Inventory()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void staffListReadSave() throws Exception {
        ReadOnlyStaffList original = getTypicalStaffList();
        storageManager.saveStaffList(original);
        ReadOnlyStaffList retrieved = storageManager.readStaffList().get();
        Inventory expected = new Inventory();
        expected.resetData(retrieved);
        assertEquals(original, expected);
    }

    @Test
    public void itemListImportExport() throws Exception {
        Path tempFile = getTempFilePath("tempItemList.csv");
        ItemList original = new ItemList(getTypicalInventory());
        storageManager.exportItemList(original, tempFile);
        ReadOnlyItemList retrieved = storageManager.importItemList(tempFile).get();
        assertEquals(original, retrieved);
    }

    @Test
    public void saleListImportExport() throws Exception {
        Path tempFile = getTempFilePath("tempSaleList.csv");
        SaleList original = new SaleList();
        original.addSale(new Sale(new SaleId(("1")), IPHONE, IPHONE.getQuantity(), new SaleDate("2018-02-02")));
        storageManager.exportSaleList(original, tempFile);
        ReadOnlySaleList retrieved = storageManager.importSaleList(getTypicalInventory(), tempFile).get();
        assertEquals(original, retrieved);
    }

    @Test
    public void staffListImportExport() throws Exception {
        Path tempFile = getTempFilePath("tempStaffList.csv");
        StaffList original = new StaffList(getTypicalStaffList());
        storageManager.exportStaffList(original, tempFile);
        ReadOnlyStaffList retrieved = storageManager.importStaffList(tempFile).get();
        assertEquals(original, retrieved);
    }

    @Test
    public void purchaseOrderListImportExport() throws Exception {
        Path tempFile = getTempFilePath("tempPurchaseOrderList.csv");
        PurchaseOrderList original = new PurchaseOrderList(TypicalPurchaseOrder.getTypicalInventory());
        storageManager.exportPurchaseOrderList(original, tempFile);
        ReadOnlyPurchaseOrderList retrieved = storageManager
                .importPurchaseOrderList(getTypicalInventory(), tempFile).get();
        assertEquals(original, retrieved);
    }


    @Test
    public void getStaffListFilePath() {
        assertNotNull(storageManager.getStaffListFilePath());
    }

    @Test
    public void handleItemListExportEvent_exceptionThrown_eventRaised() {
        Storage storage = new StorageManager(new XmlInventoryStorage(Paths.get("dummy"), Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new XmlSaleListStorage(),
                new CsvReportingStorageExceptionThrowingStub());
        storage.handleItemListExportEvent(new ItemListExportEvent(new ItemList(), Paths.get("dummy")));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataExportingExceptionEvent);
    }

    @Test
    public void handleItemListImportEvent_exceptionThrown_eventRaised() {
        Storage storage = new StorageManager(new XmlInventoryStorage(Paths.get("dummy"), Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new XmlSaleListStorage(),
                new CsvReportingStorageExceptionThrowingStub());
        storage.handleItemListImportEvent(new ItemListImportEvent(Paths.get("dummy")));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataImportingExceptionEvent);
    }

    @Test
    public void handleStaffListExportEvent_exceptionThrown_eventRaised() {
        Storage storage = new StorageManager(new XmlInventoryStorage(Paths.get("dummy"), Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new XmlSaleListStorage(),
                new CsvReportingStorageExceptionThrowingStub());
        storage.handleStaffListExportEvent(new StaffListExportEvent(new StaffList(), Paths.get("dummy")));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataExportingExceptionEvent);
    }

    @Test
    public void handleStaffListImportEvent_exceptionThrown_eventRaised() {
        Storage storage = new StorageManager(new XmlInventoryStorage(Paths.get("dummy"), Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new XmlSaleListStorage(),
                new CsvReportingStorageExceptionThrowingStub());
        storage.handleStaffListImportEvent(new StaffListImportEvent(Paths.get("dummy")));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataImportingExceptionEvent);
    }


    @Test
    public void handleSaleListExportEvent_exceptionThrown_eventRaised() {
        Storage storage = new StorageManager(new XmlInventoryStorage(Paths.get("dummy"), Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new XmlSaleListStorage(),
                new CsvReportingStorageExceptionThrowingStub());
        storage.handleSaleListExportEvent(new SaleListExportEvent(new SaleList(), Paths.get("dummy")));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataExportingExceptionEvent);
    }

    @Test
    public void handleSaleListImportEvent_exceptionThrown_eventRaised() {
        Storage storage = new StorageManager(new XmlInventoryStorage(Paths.get("dummy"), Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new XmlSaleListStorage(),
                new CsvReportingStorageExceptionThrowingStub());
        storage.handleSaleListImportEvent(new SaleListImportEvent(new Inventory(), Paths.get("dummy")));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataImportingExceptionEvent);
    }

    @Test
    public void handlePurchaseOrderListExportEvent_exceptionThrown_eventRaised() {
        Storage storage = new StorageManager(new XmlInventoryStorage(Paths.get("dummy"), Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new XmlSaleListStorage(),
                new CsvReportingStorageExceptionThrowingStub());
        storage.handlePurchaseOrderListExportEvent(new PurchaseOrderListExportEvent(new PurchaseOrderList(),
                Paths.get("dummy")));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataExportingExceptionEvent);
    }

    @Test
    public void handlePurchaseOrderListImportEvent_exceptionThrown_eventRaised() {
        Storage storage = new StorageManager(new XmlInventoryStorage(Paths.get("dummy"), Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new XmlSaleListStorage(),
                new CsvReportingStorageExceptionThrowingStub());
        storage.handlePurchaseOrderListImportEvent(new PurchaseOrderListImportEvent(new Inventory(),
                Paths.get("dummy")));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataImportingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlInventoryStorageExceptionThrowingStub extends XmlInventoryStorage {

        public XmlInventoryStorageExceptionThrowingStub(Path filePath, Path path) {
            super(filePath, path);
        }

        @Override
        public void saveInventory(ReadOnlyInventory inventory, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the export method is called
     */
    class CsvReportingStorageExceptionThrowingStub extends CsvReportingStorage {

        @Override
        public void exportItemList(ReadOnlyItemList itemList, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }

        @Override
        public void exportSaleList(ReadOnlySaleList saleList, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }

        @Override
        public void exportStaffList(ReadOnlyStaffList staffList, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }

        @Override
        public void exportPurchaseOrderList(ReadOnlyPurchaseOrderList purchaseOrderList, Path filePath)
                throws IOException {
            throw new IOException("dummy exception");
        }
    }
}
