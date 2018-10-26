package seedu.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.inventory.commons.events.model.InventoryChangedEvent;
import seedu.inventory.commons.events.model.ItemListExportEvent;
import seedu.inventory.commons.events.model.ItemListImportEvent;
import seedu.inventory.commons.events.model.SaleListChangedEvent;
import seedu.inventory.commons.events.model.SaleListExportEvent;
import seedu.inventory.commons.events.model.SaleListImportEvent;
import seedu.inventory.commons.events.model.StaffListChangedEvent;
import seedu.inventory.commons.events.model.StaffListExportEvent;
import seedu.inventory.commons.events.model.StaffListImportEvent;
import seedu.inventory.commons.events.storage.DataSavingExceptionEvent;
import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends InventoryStorage, SaleListStorage, UserPrefsStorage, ReportingStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getInventoryFilePath();

    @Override
    Optional<ReadOnlyInventory> readInventory() throws DataConversionException, IOException;

    @Override
    void saveInventory(ReadOnlyInventory inventory) throws IOException;

    @Override
    Path getStaffListFilePath();

    @Override
    Optional<ReadOnlyStaffList> readStaffList() throws DataConversionException, IOException;

    @Override
    void saveStaffList(ReadOnlyStaffList staffList) throws IOException;
    // Sale List Storage
    @Override
    Path getSaleListFilePath();

    @Override
    Optional<ReadOnlySaleList> readSaleList(ReadOnlyInventory inventory) throws DataConversionException, IOException;

    @Override
    void saveSaleList(ReadOnlySaleList saleList) throws IOException;

    /**
     * Saves the current version of the Inventory List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleInventoryChangedEvent(InventoryChangedEvent abce);

    /**
     * Saves the current version of the Sale List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleSaleListChangedEvent(SaleListChangedEvent abce);

    /**
     * Saves the current version of the Staff List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleStaffListChangedEvent(StaffListChangedEvent abce);

    /**
     * Export the current version of the Item List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises DataExportingExceptionEvent if there was an error during exporting.
     */
    void handleItemListExportEvent(ItemListExportEvent ilee);

    /**
     * Import the Item List from the hard disk.
     * Raises DataImportingExceptionEvent if there was an error during importing.
     */
    void handleItemListImportEvent(ItemListImportEvent iie);

    /**
     * Export the current version of the Sale List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises DataExportingExceptionEvent if there was an error during exporting.
     */
    void handleSaleListExportEvent(SaleListExportEvent ilee);

    /**
     * Import the Sale List from the hard disk.
     * Raises DataImportingExceptionEvent if there was an error during importing.
     */
    void handleSaleListImportEvent(SaleListImportEvent iie);

    /**
     * Export the current version of the Staff List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises DataExportingExceptionEvent if there was an error during exporting.
     */
    void handleStaffListExportEvent(StaffListExportEvent ilee);

    /**
     * Import the Staff List from the hard disk.
     * Raises DataImportingExceptionEvent if there was an error during importing.
     */
    void handleStaffListImportEvent(StaffListImportEvent iie);
}
