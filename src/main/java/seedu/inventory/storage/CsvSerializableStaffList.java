package seedu.inventory.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.StaffList;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.staff.Staff;

/**
 * An immutable sale list that is serializable to CSV format
 */
public class CsvSerializableStaffList implements CsvSerializableData {
    public static final String DATA_TYPE = "Staff";
    public static final String[] FIELDS = {"username", "password", "staffName", "role"};
    public static final String MESSAGE_DUPLICATE_STAFF = "Staff list contains duplicate staff(s).";

    private List<CsvAdaptedStaff> staffs;
    private List<List<String>> contents;

    /**
     * Creates an empty CsvSerializableStaffList.
     */
    public CsvSerializableStaffList() {
        staffs = new ArrayList<>();
        contents = getContentsFromStaffList(staffs);
    }

    /**
     * Creates CsvSerializableStaffList from a list of content.
     */
    public CsvSerializableStaffList(List<List<String>> contents) {
        this.contents = contents;
    }

    /**
     * Creates CsvSerializableStaffList from CsvSerializableData.
     */
    public CsvSerializableStaffList(CsvSerializableData data) {
        this.contents = data.getContents();
    }

    /**
     * Conversion
     */
    public CsvSerializableStaffList(ReadOnlyStaffList src) {
        staffs = src.getStaffList().stream().map(CsvAdaptedStaff::new).collect(Collectors.toList());
        contents = getContentsFromStaffList(staffs);
    }

    /**
     * Converts this staff list into the model's {@code StaffList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code CsvAdaptedStaff}.
     */
    public StaffList toModelType() throws IllegalValueException {
        staffs = splitContentsToStaffList(contents);
        StaffList staffList = new StaffList();

        for (CsvAdaptedStaff p : staffs) {
            Staff staff = p.toModelType();
            if (staffList.hasStaff(staff)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STAFF);
            }
            staffList.addStaff(staff);
        }
        return staffList;
    }

    /**
     * Combine a list of Csv-friendly adapted staff into a list of list of string representing the contents.
     *
     * @param staffs A list of Csv-friendly staff
     * @return contents A list of list of string representing the contents.
     */
    public static List<List<String>> getContentsFromStaffList(List<CsvAdaptedStaff> staffs) {
        return staffs.stream().map(CsvAdaptedStaff::getContentFromStaff).collect(Collectors.toList());
    }

    /**
     * Split a list of list of string representing the contents of staff list into a list of Csv-friendly adapted staff
     *
     * @param contents A list of list of string representing the contents of staff list
     * @return A list of Csv-friendly adapted staff containing the content of the list.
     */
    public static List<CsvAdaptedStaff> splitContentsToStaffList(List<List<String>> contents)
            throws IllegalValueException {
        List<CsvAdaptedStaff> staffs = new ArrayList<>();
        for (List<String> content : contents) {
            staffs.add(CsvAdaptedStaff.splitContentToStaff(content));
        }
        return staffs;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CsvSerializableStaffList)) {
            return false;
        }
        return staffs.equals(((CsvSerializableStaffList) other).staffs);
    }

    @Override
    public List<List<String>> getContents() {
        return contents;
    }

    @Override
    public String getDataType() {
        return DATA_TYPE;
    }

    @Override
    public String[] getDataFields() {
        return FIELDS;
    }

    @Override
    public CsvSerializableData createInstance(List<List<String>> contents) {
        return new CsvSerializableStaffList(contents);
    }
}
