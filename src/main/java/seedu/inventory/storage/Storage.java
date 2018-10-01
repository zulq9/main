package seedu.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.inventory.commons.events.model.InventoryChangedEvent;
import seedu.inventory.commons.events.storage.DataSavingExceptionEvent;
import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends InventoryStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getInventoryFilePath();

    @Override
    Optional<ReadOnlyInventory> readInventory() throws DataConversionException, IOException;

    @Override
    void saveInventory(ReadOnlyInventory addressBook) throws IOException;

    /**
     * Saves the current version of the Image Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(InventoryChangedEvent abce);
}
