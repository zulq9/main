package seedu.inventory.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.inventory.model.Inventory;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.model.item.Image;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Price;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.StaffName;
import seedu.inventory.model.staff.Username;
import seedu.inventory.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Inventory} with sample data.
 */
public class SampleDataUtil {
    public static Item[] getSampleItems() {
        return new Item[] {
            new Item(new Name("iPhone XR"), new Price("1500"), new Quantity("30"), new Sku("apple-iphone-xr"),
                new Image("/images/iphone.jpg"),
                getTagSet("apple", "iphone")),
            new Item(new Name("LG G7"), new Price("1250.50"), new Quantity("100"), new Sku("lg-g7"),
                new Image("/images/lg.jpg"),
                getTagSet("lg", "smartphone")),
            new Item(new Name("Samsung S9"), new Price("1499.99"), new Quantity("14"), new Sku("samsung-s9"),
                new Image("/images/samsung.jpg"),
                getTagSet("samsung", "smartphone")),
            new Item(new Name("HTC U6 "), new Price("999"), new Quantity("88"), new Sku("htc-u6"),
                new Image("/images/htc.jpg"),
                getTagSet("samsung", "phablet")),
            new Item(new Name("Google Pixel XL"), new Price("1435.9"), new Quantity("3"), new Sku("google-pixel-xl"),
                new Image("/images/google.jpg"),
                getTagSet("google")),
            new Item(new Name("OPPO R8"), new Price("788"), new Quantity("999"), new Sku("oppo-r8"),
                new Image("/images/oppo.jpg"),
                getTagSet("oppo", "smartphone"))
        };
    }

    public static ReadOnlyInventory getSampleInventory() {
        Inventory sampleInventory = new Inventory();
        for (Item sampleItem : getSampleItems()) {
            sampleInventory.addItem(sampleItem);
        }
        return sampleInventory;
    }

    public static Staff[] getSampleStaffs() {
        return new Staff[] {
            new Staff(new Username("zulq9"), new Password(Password.hash("zulq9999")),
                    new StaffName("Muhammad Zulqarnain"), Staff.Role.admin),
            new Staff(new Username("esmondTan"), new Password(Password.hash("esmondtt")),
                    new StaffName("Esmond Tan"), Staff.Role.admin),
            new Staff(new Username("yaotx"), new Password(Password.hash("pcyaotxcfanboi")),
                    new StaffName("Yao TengXiong"), Staff.Role.manager),
            new Staff(new Username("darren96"), new Password(Password.hash("darrenSinglenus")),
                    new StaffName("Darren Ong"), Staff.Role.manager),
            new Staff(new Username("fzdy1914"), new Password(Password.hash("fengzhidaoying")),
                    new StaffName("Wang Chao"), Staff.Role.user),
            new Staff(new Username("admin"), new Password(Password.hash("password")),
                    new StaffName("administrator"), Staff.Role.admin)
        };
    }

    public static ReadOnlyStaffList getSampleStaffList() {
        Inventory sampleStaffList = new Inventory();
        for (Staff sampleStaff : getSampleStaffs()) {
            sampleStaffList.addStaff(sampleStaff);
        }
        return sampleStaffList;
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
