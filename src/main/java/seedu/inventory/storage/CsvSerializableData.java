package seedu.inventory.storage;

import java.util.List;

/**
 * A class to access data stored as an csv file on the hard disk.
 */
public interface CsvSerializableData {
    List<List<String>> getContents();

    String getDataType();

    String[] getDataFields();

    CsvSerializableData getInstance();

    CsvSerializableData createInstance(List<List<String>> contents);
}
