package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.commons.util.CsvUtil;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.PurchaseOrderList;
import seedu.inventory.testutil.TypicalItems;
import seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder;

public class CsvSerializablePurchaseOrderListTest {

    private static final Path TEST_DATA_FOLDER = Paths
            .get("src", "test", "data", "CsvSerializablePurchaseOrderListTest");
    private static final Path TYPICAL_PURCHASE_ORDER_FILE = TEST_DATA_FOLDER.resolve("typicalPurchaseOrderList.csv");
    private static final Path INVALID_PURCHASE_ORDER_FILE = TEST_DATA_FOLDER.resolve("invalidPurchaseOrderList.csv");
    private static final Path DUPLICATE_PURCHASE_ORDER_FILE =
            TEST_DATA_FOLDER.resolve("duplicatePurchaseOrderList.csv");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Inventory typicalInventory = TypicalItems.getTypicalInventory();
    private Inventory typicalPurchaseOrderList = TypicalPurchaseOrder.getTypicalInventory();

    @Test
    public void toModelType_typicalPurchaseOrdersFile_success() throws Exception {
        CsvSerializablePurchaseOrderList dataFromFile = new CsvSerializablePurchaseOrderList(
                CsvUtil.getDataFromFile(TYPICAL_PURCHASE_ORDER_FILE, new CsvSerializablePurchaseOrderList()));
        dataFromFile.setInventory(typicalInventory);
        PurchaseOrderList inventoryFromFile = dataFromFile.toModelType();
        assertEquals(inventoryFromFile.getPurchaseOrderList(), typicalPurchaseOrderList.getPurchaseOrderList());
    }

    @Test
    public void toModelType_invalidSaleFile_throwsIllegalValueException() throws Exception {
        CsvSerializablePurchaseOrderList dataFromFile = new CsvSerializablePurchaseOrderList(
                CsvUtil.getDataFromFile(INVALID_PURCHASE_ORDER_FILE, new CsvSerializablePurchaseOrderList()));
        dataFromFile.setInventory(typicalInventory);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateSales_success() throws Exception {
        typicalPurchaseOrderList.addPurchaseOrder(TypicalPurchaseOrder.SAMSUNGNOTEPO);
        CsvSerializablePurchaseOrderList dataFromFile = new CsvSerializablePurchaseOrderList(
                CsvUtil.getDataFromFile(DUPLICATE_PURCHASE_ORDER_FILE, new CsvSerializablePurchaseOrderList()));
        dataFromFile.setInventory(typicalInventory);
        PurchaseOrderList inventoryFromFile = dataFromFile.toModelType();
        assertEquals(inventoryFromFile.getPurchaseOrderList(), typicalPurchaseOrderList.getPurchaseOrderList());
    }

    @Test
    public void equals() {
        CsvSerializablePurchaseOrderList purchaseOrderList = new CsvSerializablePurchaseOrderList();
        assertEquals(purchaseOrderList, purchaseOrderList);
        assertNotEquals(purchaseOrderList, new CsvSerializablePurchaseOrderList(typicalPurchaseOrderList));
    }
}
