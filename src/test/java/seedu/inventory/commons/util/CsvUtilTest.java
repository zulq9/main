package seedu.inventory.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.inventory.storage.CsvAdaptedData;
import seedu.inventory.storage.CsvAdaptedSalesList;


public class CsvUtilTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.csv");
    //private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.csv");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validSalesList.csv");
    //private static final Path MISSING_ITEM_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingItemField.csv");
    //private static final Path INVALID_ITEM_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidItemField.csv");
    //private static final Path VALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("validItem.csv");
    private static final CsvAdaptedData DATATYPE_TO_TRANSFER = new CsvAdaptedSalesList(null).getInstance();
    //private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempInventory.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        CsvUtil.getDataFromFile(null, DATATYPE_TO_TRANSFER);
    }

    @Test
    public void getDataFromFile_nullDataType_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        CsvUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void isDataTypeEqual() {
        assertTrue(CsvUtil.isDataTypeEqual(Arrays.asList("Sales"), DATATYPE_TO_TRANSFER));
        assertFalse(CsvUtil.isDataTypeEqual(Arrays.asList("Sales", "Items"), DATATYPE_TO_TRANSFER));
    }

    @Test
    public void isDataFieldsEqual() {
        assertTrue(CsvUtil
                .isDataFieldsEqual(Arrays.asList("saleProduct", "saleQuantity", "saleDate"), DATATYPE_TO_TRANSFER));
        assertFalse(CsvUtil.isDataFieldsEqual(Arrays.asList("saleProduct", "saleDate"), DATATYPE_TO_TRANSFER));
    }

    @Test
    public void isDataHeaderRecognizable() {
        assertFalse(CsvUtil.isDataHeaderRecognizable(VALID_FILE, DATATYPE_TO_TRANSFER));
    }

    @Test
    public void getDataContentFromFile_emptyFile_throwsUnrecognizableDataException() {
        thrown.expect(NullPointerException.class);
        CsvUtil.isDataHeaderRecognizable(EMPTY_FILE, DATATYPE_TO_TRANSFER);
    }
}
