package seedu.inventory.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.inventory.model.item.*;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.tag.Tag;
import seedu.inventory.model.util.SampleDataUtil;

/**
 * A utility class to help with building Item objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Quantity quantity;
    private Sku sku;
    private Image image;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        quantity = new Quantity(DEFAULT_PHONE);
        sku = new Sku(DEFAULT_EMAIL);
        image = new Image(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code itemToCopy}.
     */
    public PersonBuilder(Item itemToCopy) {
        name = itemToCopy.getName();
        quantity = itemToCopy.getQuantity();
        sku = itemToCopy.getSku();
        image = itemToCopy.getImage();
        tags = new HashSet<>(itemToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Item} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Item} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Image} of the {@code Item} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.image = new Image(address);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Item} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.quantity = new Quantity(phone);
        return this;
    }

    /**
     * Sets the {@code Sku} of the {@code Item} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.sku = new Sku(email);
        return this;
    }

    public Item build() {
        return new Item(name, quantity, sku, image, tags);
    }

}
