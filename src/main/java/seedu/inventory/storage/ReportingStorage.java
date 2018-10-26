package seedu.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlyItemList;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.ReadOnlyStaffList;

/**
 * Represents a storage for reporting module.
 */
public interface ReportingStorage {

    /**
     * Import the item list from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyItemList> importItemList(Path filePath) throws DataConversionException, IOException;

    /**
     * Export the given item list to the file.
     * @param itemList cannot be null.
     * @param filePath cannot be null.
     * @throws IOException if there was any problem when writing to the file.
     */
    void exportItemList(ReadOnlyItemList itemList, Path filePath) throws IOException;

    /**
     * Import the sale list from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySaleList> importSaleList(ReadOnlyInventory inventory, Path filePath)
            throws DataConversionException, IOException;

    /**
     * Export the given sale list to the file.
     * @param saleList cannot be null.
     * @param filePath cannot be null.
     * @throws IOException if there was any problem when writing to the file.
     */
    void exportSaleList(ReadOnlySaleList saleList, Path filePath) throws IOException;

    /**
     * Import the staff list from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStaffList> importStaffList(Path filePath)
            throws DataConversionException, IOException;

    /**
     * Export the given staff list to the file.
     * @param staffList cannot be null.
     * @param filePath cannot be null.
     * @throws IOException if there was any problem when writing to the file.
     */
    void exportStaffList(ReadOnlyStaffList staffList, Path filePath) throws IOException;

}
