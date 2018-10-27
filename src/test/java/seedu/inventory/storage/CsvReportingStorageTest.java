package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.NOKIA;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.ItemList;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlyItemList;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;
import seedu.inventory.testutil.TypicalItems;

public class CsvReportingStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvReportingStorageTest");

    private static ReadOnlyInventory inventory;

    private static SaleId saleIdOne = new SaleId("1");
    private static SaleId saleIdTwo = new SaleId("2");
    private static Item item = IPHONE;
    private static Quantity quantity = new Quantity("1");
    private static SaleDate saleDate = new SaleDate("2018-08-01");
    private static Sale saleOne = new Sale(saleIdOne, item, quantity, saleDate);
    private static Sale saleTwo = new Sale(saleIdTwo, item, quantity, saleDate);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        inventory = getTypicalInventory();
    }

    //==================== Item List =========================================
    @Test
    public void importItemList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        importItemList(null);
    }

    private java.util.Optional<ReadOnlyItemList> importItemList(String filePath) throws Exception {
        return new CsvReportingStorage().importItemList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void importItemList_missingFile_emptyResult() throws Exception {
        assertFalse(importItemList("nonExistentFile.csv").isPresent());
    }

    @Test
    public void importItemList_unrecognizable_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        importItemList("notRecognizableItemList.csv");
    }

    @Test
    public void importItemList_invalidItemList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        importItemList("invalidItemList.csv");
    }

    @Test
    public void importItemList_invalidAndValidItemList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        importItemList("invalidAndValidItemList.csv");
    }

    @Test
    public void importAndExportItemList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("tempItemList.csv");
        ItemList original = new ItemList(getTypicalInventory());
        CsvReportingStorage csvReportingStorage = new CsvReportingStorage();

        //Save in new file and read back
        csvReportingStorage.exportItemList(original, filePath);
        ReadOnlyItemList readBack = csvReportingStorage.importItemList(filePath).get();
        assertEquals(original, readBack);

        //Modify data, overwrite exiting file, and read back
        original.addItem(NOKIA);
        original.removeItem(IPHONE);
        csvReportingStorage.exportItemList(original, filePath);
        readBack = csvReportingStorage.importItemList(filePath).get();
        assertEquals(original, readBack);
    }

    @Test
    public void exportItemList_nullItemList_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        exportItemList(null, "someFile.csv");
    }

    /**
     * Export sale list at the specified {@code filePath}.
     */
    private void exportItemList(ReadOnlyItemList itemList, String filePath) {
        try {
            new CsvReportingStorage().exportItemList(itemList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void exportItemList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        exportItemList(new Inventory(), null);
    }

    //==================== Sale List =========================================
    @Test
    public void importSaleList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        importSaleList(inventory, null);
    }

    private java.util.Optional<ReadOnlySaleList> importSaleList(ReadOnlyInventory inventory, String filePath)
            throws Exception {
        return new CsvReportingStorage().importSaleList(inventory, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void importSaleList_missingFile_emptyResult() throws Exception {
        assertFalse(importSaleList(inventory, "nonExistentFile.csv").isPresent());
    }

    @Test
    public void importSaleList_unrecognizable_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        importSaleList(inventory, "notRecognizableSaleList.csv");
    }

    @Test
    public void importSaleList_invalidSaleList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        importSaleList(inventory, "invalidSaleList.csv");
    }

    @Test
    public void importSaleList_invalidAndValidSaleList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        importSaleList(inventory, "invalidAndValidSaleList.csv");
    }

    @Test
    public void importAndExportSaleList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("tempSaleList.csv");
        SaleList original = new SaleList();
        original.addSale(saleOne);
        CsvReportingStorage csvReportingStorage = new CsvReportingStorage();

        //Save in new file and read back
        csvReportingStorage.exportSaleList(original, filePath);
        ReadOnlySaleList readBack = csvReportingStorage.importSaleList(inventory, filePath).get();
        assertEquals(original, readBack);

        //Modify data, overwrite exiting file, and read back
        original.addSale(saleTwo);
        csvReportingStorage.exportSaleList(original, filePath);
        readBack = csvReportingStorage.importSaleList(inventory, filePath).get();
        assertEquals(original, readBack);
    }

    @Test
    public void exportSaleList_nullSaleList_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        exportSaleList(null, "someFile.csv");
    }

    /**
     * Export sale list at the specified {@code filePath}.
     */
    private void exportSaleList(ReadOnlySaleList saleList, String filePath) {
        try {
            new CsvReportingStorage().exportSaleList(saleList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void exportSaleList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        exportSaleList(new SaleList(), null);
    }


}
