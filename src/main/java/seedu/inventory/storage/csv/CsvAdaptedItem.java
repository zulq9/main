package seedu.inventory.storage.csv;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.item.Image;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Price;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.tag.Tag;

/**
 * Csv-friendly adapted version of the Item.
 */
public class CsvAdaptedItem {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Item's %s field is missing!";
    public static final String MISSING_FIELD_MESSAGE = "Item's field is missing!";

    private String name;
    private String price;
    private String quantity;
    private String sku;
    private String image;

    private List<CsvAdaptedTag> tags = new ArrayList<>();
    private String combinedTags;

    /**
     * Constructs an {@code CsvAdaptedItem} with the given item details.
     */
    public CsvAdaptedItem(String name, String price, String quantity, String sku,
                          String image, List<CsvAdaptedTag> tags) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sku = sku;
        this.image = image;
        if (tags != null) {
            this.tags = new ArrayList<>(tags);
        }
        this.combinedTags = CsvAdaptedTag.combineTags(tags);
    }

    /**
     * Constructs an {@code CsvAdaptedItem} with the given item details.
     */
    public CsvAdaptedItem(String name, String price, String quantity, String sku,
                          String image, String combinedTags) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sku = sku;
        this.image = image;
        this.tags = CsvAdaptedTag.splitToTags(combinedTags);
        this.combinedTags = combinedTags;
    }

    /**
     * Converts a given Item into this class for Csv use.
     *
     * @param source future changes to this will not affect the created CsvAdaptedItem
     */
    public CsvAdaptedItem(Item source) {
        name = source.getName().fullName;
        price = source.getPrice().value;
        quantity = source.getQuantity().value;
        sku = source.getSku().value;
        image = source.getImage().value;
        tags = source.getTags().stream()
                .map(CsvAdaptedTag::new)
                .collect(Collectors.toList());
        combinedTags = CsvAdaptedTag.combineTags(tags);
    }

    /**
     * Converts this Csv-friendly adapted item object into the model's Item object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item
     */
    public Item toModelType() throws IllegalValueException {
        final List<Tag> itemTags = new ArrayList<>();
        for (CsvAdaptedTag tag : tags) {
            itemTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (price == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName())
            );
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        if (quantity == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName())
            );
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (sku == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sku.class.getSimpleName()));
        }
        if (!Sku.isValidSku(sku)) {
            throw new IllegalValueException(Sku.MESSAGE_SKU_CONSTRAINTS);
        }
        final Sku modelSku = new Sku(sku);

        if (image == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Image.class.getSimpleName()));
        }
        if (!Image.isValidImage(image)) {
            throw new IllegalValueException(Image.MESSAGE_IMAGE_CONSTRAINTS);
        }
        final Image modelImage = new Image(image);

        final Set<Tag> modelTags = new HashSet<>(itemTags);

        return new Item(modelName, modelPrice, modelQuantity, modelSku, modelImage, modelTags);
    }

    /**
     * Combine a Csv-friendly adapted item into a list of string representing the content.
     *
     * @param item A Csv-friendly item
     * @return content A list of string representing the content.
     */
    public static List<String> getContentFromItem(CsvAdaptedItem item) {
        List<String> content = new ArrayList<>();
        content.add(item.name);
        content.add(item.price);
        content.add(item.quantity);
        content.add(item.sku);
        content.add(item.image);
        content.add(item.combinedTags);
        return content;
    }

    /**
     * Split a list of string representing the content of item into the Csv-friendly adapted item
     *
     * @param content A list of string representing the content of item
     * @return The Csv-friendly adapted item containing the content of the list.
     */
    public static CsvAdaptedItem splitContentToItem(List<String> content) throws IllegalValueException {
        if (content.size() < 6) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE);
        }
        String name = content.get(0);
        String price = content.get(1);
        String quantity = content.get(2);
        String sku = content.get(3);
        String image = content.get(4);
        String combinedTags = content.get(5);
        return new CsvAdaptedItem(name, price, quantity, sku, image, combinedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CsvAdaptedItem)) {
            return false;
        }

        CsvAdaptedItem otherItem = (CsvAdaptedItem) other;
        return Objects.equals(name, otherItem.name)
                && Objects.equals(price, otherItem.price)
                && Objects.equals(quantity, otherItem.quantity)
                && Objects.equals(sku, otherItem.sku)
                && Objects.equals(image, otherItem.image)
                && tags.equals(otherItem.tags);
    }
}
