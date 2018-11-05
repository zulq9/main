package seedu.inventory.storage.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.ItemList;
import seedu.inventory.model.ReadOnlyItemList;
import seedu.inventory.model.item.Item;

/**
 * An immutable item list that is serializable to CSV format
 */
public class CsvSerializableItemList implements CsvSerializableData {
    public static final String DATA_TYPE = "Item";
    public static final String[] FIELDS = {"name", "price", "quantity", "sku", "image", "tags"};
    public static final String MESSAGE_DUPLICATE_ITEM = "Item list contains duplicate item(s).";

    private List<CsvAdaptedItem> items;
    private List<List<String>> contents;

    /**
     * Creates an empty CsvSerializableItemList.
     */
    public CsvSerializableItemList() {
        items = new ArrayList<>();
        this.contents = getContentsFromItemList(items);
    }

    /**
     * Creates CsvSerializableItemList from a list of content.
     */
    public CsvSerializableItemList(List<List<String>> contents) {
        this.contents = contents;
    }

    /**
     * Creates CsvSerializableItemList from CsvSerializableData.
     */
    public CsvSerializableItemList(CsvSerializableData data) {
        this.contents = data.getContents();
    }

    /**
     * Conversion
     */
    public CsvSerializableItemList(ReadOnlyItemList src) {
        items = src.getItemList().stream().map(CsvAdaptedItem::new).collect(Collectors.toList());
        contents = getContentsFromItemList(items);
    }

    /**
     * Converts this item list into the model's {@code ItemList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code CsvAdaptedItem}.
     */
    public ItemList toModelType() throws IllegalValueException {
        items = splitContentsToItemList(contents);
        ItemList itemlist = new ItemList();

        for (CsvAdaptedItem p : items) {
            Item item = p.toModelType();
            if (itemlist.hasItem(item)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            itemlist.addItem(item);
        }
        return itemlist;
    }

    /**
     * Combine a list of Csv-friendly adapted item into a list of list of string representing the contents.
     *
     * @param items A list of Csv-friendly item
     * @return contents A list of list of string representing the contents.
     */
    public static List<List<String>> getContentsFromItemList(List<CsvAdaptedItem> items) {
        return items.stream().map(CsvAdaptedItem::getContentFromItem).collect(Collectors.toList());
    }

    /**
     * Split a list of list of string representing the contents of item list into a list of Csv-friendly adapted item
     *
     * @param contents A list of list of string representing the contents of item list
     * @return A list of Csv-friendly adapted item containing the content of the list.
     */
    public static List<CsvAdaptedItem> splitContentsToItemList(List<List<String>> contents)
            throws IllegalValueException {
        List<CsvAdaptedItem> items = new ArrayList<>();
        for (List<String> content : contents) {
            items.add(CsvAdaptedItem.splitContentToItem(content));
        }
        return items;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CsvSerializableItemList)) {
            return false;
        }
        return contents.equals(((CsvSerializableItemList) other).contents);
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
        return new CsvSerializableItemList(contents);
    }
}
