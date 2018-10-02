package seedu.address.storage;

import java.util.List;

/**
 * A class to access data stored as an csv file on the hard disk.
 */
public class CsvAdaptedData {
    public final List<List<String>> contents;

    private String dataType = "Raw Data";
    private String[] fields = {"name"};

    public CsvAdaptedData(List<List<String>> contents) {
        this.contents = contents;

    }

    public String getDataType() {
        return dataType;
    }

    public String[] getDataFields() {
        return fields;
    }
}
