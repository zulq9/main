package seedu.inventory.storage;

import java.util.List;

/**
 * A class to access item list data stored as an csv file on the hard disk.
 */
public class CsvSerializableSalesList implements CsvSerializableData {
    public static final String DATA_TYPE = "Sales";
    public static final String[] FIELDS = {"saleProduct", "saleQuantity", "saleDate"};

    private List<List<String>> contents;

    public CsvSerializableSalesList(List<List<String>> contents) {
        this.contents = contents;
    }

    private CsvSerializableSalesList() {}

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
    public CsvSerializableData getInstance() {
        return new CsvSerializableSalesList();
    }

    @Override
    public CsvSerializableData createInstance(List<List<String>> contents) {
        return new CsvSerializableSalesList(contents);
    }
}
