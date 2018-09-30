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
            new Item(new Name("Alex Yeoh"), new Quantity("87438807"), new Sku("alexyeoh@example.com"),
                new Image("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Item(new Name("Bernice Yu"), new Quantity("99272758"), new Sku("berniceyu@example.com"),
                new Image("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Item(new Name("Charlotte Oliveiro"), new Quantity("93210283"), new Sku("charlotte@example.com"),
                new Image("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Item(new Name("David Li"), new Quantity("91031282"), new Sku("lidavid@example.com"),
                new Image("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Item(new Name("Irfan Ibrahim"), new Quantity("92492021"), new Sku("irfan@example.com"),
                new Image("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Item(new Name("Roy Balakrishnan"), new Quantity("92624417"), new Sku("royb@example.com"),
                new Image("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
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
