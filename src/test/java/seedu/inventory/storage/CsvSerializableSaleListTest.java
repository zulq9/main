package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.commons.util.CsvUtil;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;
import seedu.inventory.testutil.TypicalItems;

public class CsvSerializableSaleListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvSerializableSaleListTest");
    private static final Path TYPICAL_SALES_FILE = TEST_DATA_FOLDER.resolve("typicalSaleList.csv");
    private static final Path INVALID_SALE_FILE = TEST_DATA_FOLDER.resolve("invalidSaleList.csv");
    private static final Path DUPLICATE_SALE_FILE = TEST_DATA_FOLDER.resolve("duplicateSaleList.csv");

    private static SaleId saleId = new SaleId("1");
    private static Item item = TypicalItems.IPHONE;
    private static Quantity quantity = new Quantity("1");
    private static SaleDate saleDate = new SaleDate("2018-08-01");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SaleList typicalSaleList = new SaleList();
    private Inventory typicalInventory = TypicalItems.getTypicalInventory();

    @Before
    public void setUp() {
        typicalSaleList.addSale(new Sale(saleId, item, quantity, saleDate));
    }

    @Test
    public void toModelType_typicalSalesFile_success() throws Exception {
        CsvSerializableSaleList dataFromFile = new CsvSerializableSaleList(CsvUtil.getDataFromFile(TYPICAL_SALES_FILE,
                new CsvSerializableSaleList()));
        dataFromFile.setInventory(typicalInventory);
        SaleList saleListFromFile = dataFromFile.toModelType();
        assertEquals(saleListFromFile, typicalSaleList);
        assertEquals(dataFromFile, new CsvSerializableSaleList(typicalSaleList));
    }

    @Test
    public void toModelType_invalidSaleFile_throwsIllegalValueException() throws Exception {
        CsvSerializableSaleList dataFromFile = new CsvSerializableSaleList(CsvUtil.getDataFromFile(INVALID_SALE_FILE,
                new CsvSerializableSaleList()));
        dataFromFile.setInventory(typicalInventory);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateSales_throwsIllegalValueException() throws Exception {
        CsvSerializableSaleList dataFromFile = new CsvSerializableSaleList(CsvUtil.getDataFromFile(DUPLICATE_SALE_FILE,
                new CsvSerializableSaleList()));
        dataFromFile.setInventory(typicalInventory);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(CsvSerializableSaleList.MESSAGE_DUPLICATE_SALE);
        dataFromFile.toModelType();
    }

    @Test
    public void equals() {
        CsvSerializableSaleList saleList = new CsvSerializableSaleList();
        assertEquals(saleList, saleList);
        assertNotEquals(saleList, new CsvSerializableItemList());
    }
}
