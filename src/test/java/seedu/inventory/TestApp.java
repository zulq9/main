package seedu.inventory;

import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PASSWORD_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_USERNAME_ZUL;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.inventory.commons.core.Config;
import seedu.inventory.commons.core.GuiSettings;
import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.commons.util.FileUtil;
import seedu.inventory.commons.util.XmlUtil;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.UserPrefs;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.storage.UserPrefsStorage;
import seedu.inventory.storage.XmlSerializableInventory;
import seedu.inventory.storage.XmlSerializableStaffList;
import seedu.inventory.testutil.TestUtil;
import seedu.inventory.testutil.staff.StaffBuilder;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    public static final Path SAVE_LOCATION_FOR_STAFF =
            TestUtil.getFilePathInSandboxFolder("sampleStaffData.xml");
    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyInventory> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;
    protected Path saveStaffFileLocation = SAVE_LOCATION_FOR_STAFF;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyInventory> initialDataSupplier, Path saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableInventory(this.initialDataSupplier.get()),
                    this.saveFileLocation);
            createDataFileWithData(new XmlSerializableStaffList(this.initialDataSupplier.get()),
                    this.saveStaffFileLocation);
        }
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setInventoryFilePath(saveFileLocation);
        userPrefs.setStaffListFilePath(saveStaffFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the inventory data stored inside the storage file.
     */
    public Inventory readStorageInventory() {
        try {
            Inventory inventory = new Inventory(storage.readInventory().get());
            inventory.resetStaffList(readStorageStaffList());
            return inventory;
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the Inventory format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getInventoryFilePath();
    }

    /**
     * Returns a defensive copy of the staff list data stored inside the storage file.
     * @return the staff list
     */
    public ReadOnlyStaffList readStorageStaffList() {
        try {
            return storage.readStaffList().get();
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the StaffList format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList());
        ModelHelper.setFilteredList(copy, model.getFilteredItemList());
        ModelHelper.setStaffFilteredList(copy, model.getFilteredStaffList());
        Staff staff = new StaffBuilder().withUsername(VALID_USERNAME_ZUL)
                .withPassword(VALID_PASSWORD_ZUL).build();
        model.authenticateUser(staff);
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, Path filePath) {
        try {
            FileUtil.createIfMissing(filePath);
            XmlUtil.saveDataToFile(filePath, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
