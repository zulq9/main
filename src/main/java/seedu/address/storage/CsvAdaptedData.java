package seedu.address.storage;

import java.util.List;

/**
 * A class to access data stored as an csv file on the hard disk.
 */
public interface CsvAdaptedData {

    /**
     * Get the data type of the CSV adapted data.
     */
    String getDataType();

    /**
     * Get the fields of the CSV adapted data.
     */
    List<String> getDataFields();
}
