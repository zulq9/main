package seedu.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.ReadOnlyInventory;

/**
 * Represents a storage for {@link Inventory}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns Inventory data as a {@link ReadOnlyInventory}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyInventory> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyInventory> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyInventory} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyInventory addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyInventory)
     */
    void saveAddressBook(ReadOnlyInventory addressBook, Path filePath) throws IOException;

}
