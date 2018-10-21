package seedu.inventory.storage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.inventory.testutil.TypicalItems;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class CsvSerializableItemListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvSerializableItemListTest");
    private static final Path TYPICAL_ITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalItemList.csv");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalItemsFile_success() throws Exception {
        CsvReportingStorage.saveItemListToFile(TYPICAL_ITEMS_FILE, new CsvSerializableItemList(TypicalItems.getTypicalInventory()));
        CsvSerializableItemList list = CsvReportingStorage.loadItemListFromFile(TYPICAL_ITEMS_FILE);
        assertEquals(list.toModelType().getItemList(), TypicalItems.getTypicalInventory().getItemList());
    }


}
