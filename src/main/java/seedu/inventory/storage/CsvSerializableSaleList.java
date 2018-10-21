package seedu.inventory.storage;

import java.util.List;

/**
 * An immutable sale list that is serializable to CSV format
 */
public class CsvSerializableSaleList implements CsvSerializableData {
    public static final String DATA_TYPE = "Sales";
    public static final String[] FIELDS = {"saleProduct", "saleQuantity", "saleDate"};

    private List<List<String>> contents;

    public CsvSerializableSaleList(List<List<String>> contents) {
        this.contents = contents;
    }

    private CsvSerializableSaleList() {}

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
        return new CsvSerializableSaleList(contents);
    }
}
