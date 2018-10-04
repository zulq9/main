package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.commons.util.XmlUtil;
import seedu.inventory.model.Inventory;
import seedu.inventory.testutil.TypicalItems;

public class XmlSerializableInventoryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableInventoryTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalItemsInventory.xml");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidItemInventory.xml");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicateItemInventory.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableInventory dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableInventory.class);
        Inventory inventoryFromFile = dataFromFile.toModelType();
        Inventory typicalPersonsInventory = TypicalItems.getTypicalInventory();
        assertEquals(inventoryFromFile, typicalPersonsInventory);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableInventory dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableInventory.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableInventory dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE,
                XmlSerializableInventory.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableInventory.MESSAGE_DUPLICATE_ITEM);
        dataFromFile.toModelType();
    }

}
