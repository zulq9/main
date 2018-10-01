package seedu.inventory.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.inventory.model.Inventory;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.item.*;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Inventory} with sample data.
 */
public class SampleDataUtil {
    public static Item[] getSamplePersons() {
        return new Item[] {
            new Item(new Name("iPhone XR"), new Quantity("30"), new Sku("apple-iphone-xr"),
                new Image("docs/images/iphone.jpg"),
                getTagSet("apple", "iphone")),
            new Item(new Name("iPhone X"), new Quantity("100"), new Sku("apple-iphone-x"),
                new Image("docs/images/iphone.jpg"),
                getTagSet("apple", "iphone")),
            new Item(new Name("Samsung S9"), new Quantity("14"), new Sku("samsung-s9"),
                new Image("docs/images/samsung.jpg"),
                getTagSet("samsung", "smartphone")),
            new Item(new Name("Samsung Note 9"), new Quantity("88"), new Sku("samsung-note-9"),
                new Image("docs/images/samsung.jpg"),
                getTagSet("samsung", "phablet")),
            new Item(new Name("Google Pixel 3"), new Quantity("3"), new Sku("google-pixel-3"),
                new Image("docs/images/google.jpg"),
                getTagSet("google")),
            new Item(new Name("iPhone XS"), new Quantity("999"), new Sku("apple-iphone-xs"),
                new Image("docs/images/iphone.jpg"),
                getTagSet("apple", "iphone"))
        };
    }

    public static ReadOnlyInventory getSampleAddressBook() {
        Inventory sampleAb = new Inventory();
        for (Item sampleItem : getSamplePersons()) {
            sampleAb.addPerson(sampleItem);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
