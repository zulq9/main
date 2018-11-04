package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.NOKIA;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;
import static seedu.inventory.testutil.staff.TypicalStaffs.WANGCHAO;
import static seedu.inventory.testutil.staff.TypicalStaffs.getTypicalStaffList;

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
import seedu.inventory.model.PurchaseOrderList;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlyItemList;
import seedu.inventory.model.ReadOnlyPurchaseOrderList;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.StaffList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;
import seedu.inventory.storage.csv.CsvReportingStorage;
import seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder;

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

    //==================== All need method =========================================
    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
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
     * Export item list at the specified {@code filePath}.
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

    //==================== Staff List =========================================
    @Test
    public void importStaffList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        importStaffList(null);
    }

    private java.util.Optional<ReadOnlyStaffList> importStaffList(String filePath) throws Exception {
        return new CsvReportingStorage().importStaffList(addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void importStaffList_missingFile_emptyResult() throws Exception {
        assertFalse(importStaffList("nonExistentFile.csv").isPresent());
    }

    @Test
    public void importStaffList_unrecognizable_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        importStaffList("notRecognizableStaffList.csv");
    }

    @Test
    public void importStaffList_invalidStaffList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        importStaffList("invalidStaffList.csv");
    }

    @Test
    public void importStaffList_invalidAndValidStaffList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        importStaffList("invalidAndValidStaffList.csv");
    }

    @Test
    public void importAndExportStaffList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("tempStaffList.csv");
        StaffList original = new StaffList(getTypicalStaffList());
        CsvReportingStorage csvReportingStorage = new CsvReportingStorage();

        //Save in new file and read back
        csvReportingStorage.exportStaffList(original, filePath);
        ReadOnlyStaffList readBack = csvReportingStorage.importStaffList(filePath).get();
        assertEquals(original, readBack);

        //Modify data, overwrite exiting file, and read back
        original.removeStaff(WANGCHAO);
        csvReportingStorage.exportStaffList(original, filePath);
        readBack = csvReportingStorage.importStaffList(filePath).get();
        assertEquals(original, readBack);
    }

    @Test
    public void exportStaffList_nullStaffList_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        exportStaffList(null, "someFile.csv");
    }

    /**
     * Export staff list at the specified {@code filePath}.
     */
    private void exportStaffList(ReadOnlyStaffList staffList, String filePath) {
        try {
            new CsvReportingStorage().exportStaffList(staffList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void exportStaffList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        exportStaffList(new Inventory(), null);
    }

    //==================== Purchase Order List =========================================
    @Test
    public void importPurchaseOrderList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        importPurchaseOrderList(inventory, null);
    }

    private java.util.Optional<ReadOnlyPurchaseOrderList> importPurchaseOrderList(
            ReadOnlyInventory inventory, String filePath) throws Exception {
        return new CsvReportingStorage().importPurchaseOrderList(inventory, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void importPurchaseOrderList_missingFile_emptyResult() throws Exception {
        assertFalse(importPurchaseOrderList(inventory, "nonExistentFile.csv").isPresent());
    }

    @Test
    public void importPurchaseOrderList_unrecognizable_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        importPurchaseOrderList(inventory, "notRecognizablePurchaseOrderList.csv");
    }

    @Test
    public void importPurchaseOrderList_invalidPurchaseOrderList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        importPurchaseOrderList(inventory, "invalidPurchaseOrderList.csv");
    }

    @Test
    public void importPurchaseOrderList_invalidAndValidPurchaseOrderList_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        importPurchaseOrderList(inventory, "invalidAndValidPurchaseOrderList.csv");
    }

    @Test
    public void importAndExportPurchaseOrderList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("tempPurchaseOrderList.csv");
        PurchaseOrderList original = new PurchaseOrderList(TypicalPurchaseOrder.getTypicalInventory());
        CsvReportingStorage csvReportingStorage = new CsvReportingStorage();

        //Save in new file and read back
        csvReportingStorage.exportPurchaseOrderList(original, filePath);
        ReadOnlyPurchaseOrderList readBack = csvReportingStorage.importPurchaseOrderList(inventory, filePath).get();
        assertEquals(original, readBack);

        //Modify data, overwrite exiting file, and read back
        original.addPurchaseOrder(TypicalPurchaseOrder.SAMSUNGNOTEPO);
        csvReportingStorage.exportPurchaseOrderList(original, filePath);
        readBack = csvReportingStorage.importPurchaseOrderList(inventory, filePath).get();
        assertEquals(original, readBack);
    }

    @Test
    public void exportPurchaseOrderList_nullPurchaseOrderList_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        exportPurchaseOrderList(null, "someFile.csv");
    }

    /**
     * Export PurchaseOrder list at the specified {@code filePath}.
     */
    private void exportPurchaseOrderList(ReadOnlyPurchaseOrderList purchaseOrderList, String filePath) {
        try {
            new CsvReportingStorage().exportPurchaseOrderList(purchaseOrderList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void exportPurchaseOrderList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        exportPurchaseOrderList(new PurchaseOrderList(), null);
    }

}
