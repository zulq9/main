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
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.testutil.staff.TypicalStaffs;

public class CsvSerializableStaffListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvSerializableStaffListTest");
    private static final Path TYPICAL_STAFFS_FILE = TEST_DATA_FOLDER.resolve("typicalStaffList.csv");
    private static final Path INVALID_STAFF_FILE = TEST_DATA_FOLDER.resolve("invalidStaffList.csv");
    private static final Path DUPLICATE_STAFF_FILE = TEST_DATA_FOLDER.resolve("duplicateStaffList.csv");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalStaffsFile_success() throws Exception {
        CsvSerializableStaffList dataFromFile = new CsvSerializableStaffList(CsvUtil
                .getDataFromFile(TYPICAL_STAFFS_FILE, new CsvSerializableStaffList()));
        ReadOnlyStaffList staffListFromFile = dataFromFile.toModelType();
        ReadOnlyStaffList typicalStaffsStaffList = TypicalStaffs.getTypicalStaffList();
        assertEquals(staffListFromFile.getStaffList(), typicalStaffsStaffList.getStaffList());
    }

    @Test
    public void toModelType_invalidStaffFile_throwsIllegalValueException() throws Exception {
        CsvSerializableStaffList dataFromFile = new CsvSerializableStaffList(CsvUtil
                .getDataFromFile(INVALID_STAFF_FILE, new CsvSerializableStaffList()));
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateStaffs_throwsIllegalValueException() throws Exception {
        CsvSerializableStaffList dataFromFile = new CsvSerializableStaffList(CsvUtil
                .getDataFromFile(DUPLICATE_STAFF_FILE, new CsvSerializableStaffList()));
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(CsvSerializableStaffList.MESSAGE_DUPLICATE_STAFF);
        dataFromFile.toModelType();
    }

    @Test
    public void equals() {
        CsvSerializableStaffList staffList = new CsvSerializableStaffList();
        assertEquals(staffList, staffList);
        assertNotEquals(staffList, new CsvSerializableItemList());
        assertNotEquals(staffList, new CsvSerializableStaffList(TypicalStaffs.getTypicalStaffList()));
    }
}
