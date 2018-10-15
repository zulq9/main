package seedu.inventory.storage;

import java.util.List;

/**
 * A class to access item list data stored as an csv file on the hard disk.
 */
public class CsvAdaptedItemList implements CsvAdaptedData {
    public static final String DATA_TYPE = "Item";
    public static final String[] FIELDS = {"name", "price", "quantity", "sku", "image", "tags"};

    private List<List<String>> contents;

    public CsvAdaptedItemList(List<List<String>> contents) {
        this.contents = contents;
    }

    private CsvAdaptedItemList() {}

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
    public CsvAdaptedData getInstance() {
        return new CsvAdaptedItemList();
    }

    @Override
    public CsvAdaptedData createInstance(List<List<String>> contents) {
        return new CsvAdaptedItemList(contents);
    }
}
