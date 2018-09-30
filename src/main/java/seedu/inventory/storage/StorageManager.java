package seedu.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.inventory.commons.core.ComponentManager;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.events.model.InventoryChangedEvent;
import seedu.inventory.commons.events.storage.DataSavingExceptionEvent;
import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.UserPrefs;

/**
 * Manages storage of Inventory data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Inventory methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyInventory> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyInventory> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyInventory addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyInventory addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(InventoryChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
