package seedu.inventory.storage;

import java.util.List;

/**
 * A class to access data stored as an csv file on the hard disk.
 */
public class CsvAdaptedData {
    private List<List<String>> contents;

    private String dataType = "Raw Data";
    private String[] fields = {"name"};

    public CsvAdaptedData(List<List<String>> contents) {
        this.contents = contents;

    }

    public CsvAdaptedData() {

    }

    public List<List<String>> getContents() {
        return contents;
    }

    public String getDataType() {
        return dataType;
    }

    public String[] getDataFields() {
        return fields;
    }
}
