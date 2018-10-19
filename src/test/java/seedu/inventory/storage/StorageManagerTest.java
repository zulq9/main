package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;
import static seedu.inventory.testutil.TypicalStaffs.getTypicalStaffList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.inventory.commons.events.model.InventoryChangedEvent;
import seedu.inventory.commons.events.model.StaffListChangedEvent;
import seedu.inventory.commons.events.storage.DataSavingExceptionEvent;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.model.StaffList;
import seedu.inventory.model.UserPrefs;
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
        storageManager = new StorageManager(inventoryManagerStorage, userPrefsStorage, saleListStorage);
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
                Paths.get("dummy"), Paths.get("staffDummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")),
                                             new XmlSaleListStorage());
        storage.handleInventoryChangedEvent(new InventoryChangedEvent(new Inventory()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void staffListReadSave() throws Exception {
        StaffList original = getTypicalStaffList();
        storageManager.saveStaffList(original);
        ReadOnlyStaffList retrieved = storageManager.readStaffList().get();
        assertEquals(original, new StaffList(retrieved));
    }

    @Test
    public void getStaffListFilePath() {
        assertNotNull(storageManager.getStaffListFilePath());
    }

    @Test
    public void handleStaffListChangedEvent_exceptionThrown_eventRaised() {
        Storage storage = new StorageManager(new XmlInventoryStorage(Paths.get("dummy"), Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new XmlSaleListStorage());
        storage.handleStaffListChangedEvent(new StaffListChangedEvent(new Inventory()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlInventoryStorageExceptionThrowingStub extends XmlInventoryStorage {

        public XmlInventoryStorageExceptionThrowingStub(Path filePath, Path staffFilePath) {
            super(filePath, staffFilePath);
        }

        @Override
        public void saveInventory(ReadOnlyInventory inventory, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

}
