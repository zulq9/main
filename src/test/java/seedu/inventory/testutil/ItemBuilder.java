package seedu.inventory.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.inventory.model.item.Image;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.tag.Tag;
import seedu.inventory.model.util.SampleDataUtil;

/**
 * A utility class to help with building Item objects.
 */
public class ItemBuilder {

    public static final String DEFAULT_NAME = "iPhone XS";
    public static final String DEFAULT_QUANTITY = "30";
    public static final String DEFAULT_SKU = "apple-iphone-xs";
    public static final String DEFAULT_IMAGE = "docs/images/iphone.jpg";

    private Name name;
    private Quantity quantity;
    private Sku sku;
    private Image image;
    private Set<Tag> tags;

    public ItemBuilder() {
        name = new Name(DEFAULT_NAME);
        quantity = new Quantity(DEFAULT_QUANTITY);
        sku = new Sku(DEFAULT_SKU);
        image = new Image(DEFAULT_IMAGE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ItemBuilder with the data of {@code itemToCopy}.
     */
    public ItemBuilder(Item itemToCopy) {
        name = itemToCopy.getName();
        quantity = itemToCopy.getQuantity();
        sku = itemToCopy.getSku();
        image = itemToCopy.getImage();
        tags = new HashSet<>(itemToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Item} that we are building.
     */
    public ItemBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Item} that we are building.
     */
    public ItemBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Image} of the {@code Item} that we are building.
     */
    public ItemBuilder withImage(String image) {
        this.image = new Image(image);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Item} that we are building.
     */
    public ItemBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code Sku} of the {@code Item} that we are building.
     */
    public ItemBuilder withSku(String sku) {
        this.sku = new Sku(sku);
        return this;
    }

    public Item build() {
        return new Item(name, quantity, sku, image, tags);
    }

}
