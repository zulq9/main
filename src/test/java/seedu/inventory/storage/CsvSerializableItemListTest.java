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
import seedu.inventory.model.ItemList;
import seedu.inventory.storage.csv.CsvSerializableItemList;
import seedu.inventory.storage.csv.CsvSerializableSaleList;
import seedu.inventory.testutil.TypicalItems;

public class CsvSerializableItemListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvSerializableItemListTest");
    private static final Path TYPICAL_ITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalItemList.csv");
    private static final Path INVALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("invalidItemList.csv");
    private static final Path DUPLICATE_ITEM_FILE = TEST_DATA_FOLDER.resolve("duplicateItemList.csv");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalItemsFile_success() throws Exception {
        CsvSerializableItemList dataFromFile = new CsvSerializableItemList(CsvUtil.getDataFromFile(TYPICAL_ITEMS_FILE,
                new CsvSerializableItemList()));
        ItemList itemListFromFile = dataFromFile.toModelType();
        ItemList typicalItemList = new ItemList(TypicalItems.getTypicalInventory());
        assertEquals(itemListFromFile, typicalItemList);
        assertEquals(dataFromFile, new CsvSerializableItemList(TypicalItems.getTypicalInventory()));
    }

    @Test
    public void toModelType_invalidItemFile_throwsIllegalValueException() throws Exception {
        CsvSerializableItemList dataFromFile = new CsvSerializableItemList(CsvUtil.getDataFromFile(INVALID_ITEM_FILE,
                new CsvSerializableItemList()));
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateItems_throwsIllegalValueException() throws Exception {
        CsvSerializableItemList dataFromFile = new CsvSerializableItemList(CsvUtil.getDataFromFile(DUPLICATE_ITEM_FILE,
                new CsvSerializableItemList()));
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(CsvSerializableItemList.MESSAGE_DUPLICATE_ITEM);
        dataFromFile.toModelType();
    }

    @Test
    public void equals() {
        CsvSerializableItemList itemList = new CsvSerializableItemList();
        assertEquals(itemList, itemList);
        assertNotEquals(itemList, new CsvSerializableSaleList());
    }

}
