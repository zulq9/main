package seedu.inventory.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.item.Item;

/**
 * An immutable item list that is serializable to CSV format
 */
public class CsvSerializableItemList implements CsvSerializableData {
    public static final String DATA_TYPE = "Item";
    public static final String[] FIELDS = {"name", "price", "quantity", "sku", "image", "tags"};
    public static final String MESSAGE_DUPLICATE_ITEM = "Inventory list contains duplicate item(s).";

    private List<CsvAdaptedItem> items;
    private List<List<String>> contents;

    /**
     * Creates an empty CsvSerializableItemList.
     */
    public CsvSerializableItemList() {
        items = new ArrayList<>();
    }

    /**
     * Creates CsvSerializableItemList from a list of content.
     */
    public CsvSerializableItemList(List<List<String>> contents) {
        this.contents = contents;
    }

    /**
     * Conversion
     */
    public CsvSerializableItemList(ReadOnlyInventory src) {
        items = src.getItemList().stream().map(CsvAdaptedItem::new).collect(Collectors.toList());
    }

    /**
     * Converts this inventory into the model's {@code Inventory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code CsvAdaptedItem}.
     */
    public Inventory toModelType() throws IllegalValueException {
        Inventory inventory = new Inventory();

        for (CsvAdaptedItem p : items) {
            Item item = p.toModelType();
            if (inventory.hasItem(item)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            inventory.addItem(item);
        }
        return inventory;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CsvSerializableItemList)) {
            return false;
        }
        return items.equals(((CsvSerializableItemList) other).items);
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
    public CsvSerializableData getInstance() {
        return new CsvSerializableItemList();
    }

    @Override
    public CsvSerializableData createInstance(List<List<String>> contents) {
        return new CsvSerializableItemList(contents);
    }
}
