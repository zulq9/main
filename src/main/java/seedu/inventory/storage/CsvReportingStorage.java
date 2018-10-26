package seedu.inventory.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.commons.exceptions.UnrecognizableDataException;
import seedu.inventory.commons.util.CsvUtil;
import seedu.inventory.commons.util.FileUtil;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlyItemList;
import seedu.inventory.model.ReadOnlyPurchaseOrderList;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.ReadOnlyStaffList;


/**
 * Represents a storage for reporting module in Csv file.
 */
public class CsvReportingStorage implements ReportingStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlInventoryStorage.class);

    @Override
    public Optional<ReadOnlyItemList> importItemList(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Item list file " + filePath + " not found");
            return Optional.empty();
        }
        try {
            CsvSerializableItemList items = new CsvSerializableItemList(CsvUtil.getDataFromFile(filePath,
                    new CsvSerializableItemList()));
            return Optional.of(items.toModelType());
        } catch (UnrecognizableDataException ude) {
            logger.info("Data in " + filePath + " can not be recognized");
            throw new DataConversionException(ude);
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void exportItemList(ReadOnlyItemList itemList, Path filePath) throws IOException {
        requireNonNull(itemList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        CsvUtil.saveDataToFile(filePath, new CsvSerializableItemList(itemList));
    }

    @Override
    public Optional<ReadOnlySaleList> importSaleList(ReadOnlyInventory inventory, Path filePath)
            throws DataConversionException, IOException {
        requireNonNull(inventory);
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Sale list file " + filePath + " not found");
            return Optional.empty();
        }
        try {
            CsvSerializableSaleList sales = new CsvSerializableSaleList(CsvUtil.getDataFromFile(filePath,
                    new CsvSerializableSaleList()));
            sales.setInventory(inventory);
            return Optional.of(sales.toModelType());
        } catch (UnrecognizableDataException ude) {
            logger.info("Data in " + filePath + " can not be recognized");
            throw new DataConversionException(ude);
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void exportSaleList(ReadOnlySaleList saleList, Path filePath) throws IOException {
        requireNonNull(saleList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        CsvUtil.saveDataToFile(filePath, new CsvSerializableSaleList(saleList));
    }

    @Override
    public Optional<ReadOnlyStaffList> importStaffList(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Staff list file " + filePath + " not found");
            return Optional.empty();
        }
        try {
            CsvSerializableStaffList staffs = new CsvSerializableStaffList(CsvUtil.getDataFromFile(filePath,
                    new CsvSerializableStaffList()));
            return Optional.of(staffs.toModelType());
        } catch (UnrecognizableDataException ude) {
            logger.info("Data in " + filePath + " can not be recognized");
            throw new DataConversionException(ude);
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void exportStaffList(ReadOnlyStaffList staffList, Path filePath) throws IOException {
        requireNonNull(staffList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        CsvUtil.saveDataToFile(filePath, new CsvSerializableStaffList(staffList));
    }

    @Override
    public Optional<ReadOnlyPurchaseOrderList> importPurchaseOrderList(ReadOnlyInventory inventory, Path filePath)
            throws DataConversionException, IOException {
        requireNonNull(inventory);
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Sale list file " + filePath + " not found");
            return Optional.empty();
        }
        try {
            CsvSerializablePurchaseOrderList purchaseOrders = new CsvSerializablePurchaseOrderList(CsvUtil
                    .getDataFromFile(filePath, new CsvSerializablePurchaseOrderList()));
            purchaseOrders.setInventory(inventory);
            return Optional.of(purchaseOrders.toModelType());
        } catch (UnrecognizableDataException ude) {
            logger.info("Data in " + filePath + " can not be recognized");
            throw new DataConversionException(ude);
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void exportPurchaseOrderList(ReadOnlyPurchaseOrderList purchaseOrderList, Path filePath) throws IOException {
        requireNonNull(purchaseOrderList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        CsvUtil.saveDataToFile(filePath, new CsvSerializablePurchaseOrderList(purchaseOrderList));
    }

}
