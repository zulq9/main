package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.NOKIA;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.ItemList;
import seedu.inventory.model.ReadOnlyItemList;

public class CsvReportingStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvReportingStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

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
    public void importAndExportInventory_allInOrder_success() throws Exception {
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
    public void saveInventory_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        exportItemList(new Inventory(), null);
    }


}
