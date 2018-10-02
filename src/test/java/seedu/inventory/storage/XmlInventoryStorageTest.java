package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.inventory.testutil.TypicalItems.ALICE;
import static seedu.inventory.testutil.TypicalItems.HOON;
import static seedu.inventory.testutil.TypicalItems.IDA;
import static seedu.inventory.testutil.TypicalItems.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.ReadOnlyInventory;

public class XmlInventoryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlInventoryStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyInventory> readAddressBook(String filePath) throws Exception {
        return new XmlInventoryStorage(Paths.get(filePath)).readInventory(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidPersonAddressBook.xml");
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidPersonAddressBook.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        Inventory original = getTypicalAddressBook();
        XmlInventoryStorage xmlInventoryStorage = new XmlInventoryStorage(filePath);

        //Save in new file and read back
        xmlInventoryStorage.saveInventory(original, filePath);
        ReadOnlyInventory readBack = xmlInventoryStorage.readInventory(filePath).get();
        assertEquals(original, new Inventory(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlInventoryStorage.saveInventory(original, filePath);
        readBack = xmlInventoryStorage.readInventory(filePath).get();
        assertEquals(original, new Inventory(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlInventoryStorage.saveInventory(original); //file path not specified
        readBack = xmlInventoryStorage.readInventory().get(); //file path not specified
        assertEquals(original, new Inventory(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyInventory addressBook, String filePath) {
        try {
            new XmlInventoryStorage(Paths.get(filePath))
                    .saveInventory(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new Inventory(), null);
    }


}
