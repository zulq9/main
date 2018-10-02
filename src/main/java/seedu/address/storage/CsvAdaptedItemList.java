package seedu.address.storage;

import java.util.List;

/**
 * A class to access item list data stored as an csv file on the hard disk.
 */
public class CsvAdaptedItemList extends CsvAdaptedData {
    public static final String DATA_TYPE = "Item";
    public static final String[] FIELDS = {"name", "id"};

    CsvAdaptedItemList(List<List<String>> object) {
        super(object);
    }

    @Override
    public String getDataType() {
        return DATA_TYPE;
    }

    @Override
    public String[] getDataFields() {
        return FIELDS;
    }
}
