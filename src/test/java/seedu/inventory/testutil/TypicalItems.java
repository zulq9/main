package seedu.inventory.testutil;

import static seedu.inventory.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.inventory.model.Inventory;
import seedu.inventory.model.item.Item;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalItems {

    public static final Item IPHONE = new ItemBuilder().withName("iPhone XR")
            .withImage("docs/images/iphone.jpg").withSku("apple-iphone-xr")
            .withQuantity("30")
            .withTags("apple").build();
    public static final Item SAMSUNG = new ItemBuilder().withName("Samsung S9")
            .withImage("docs/images/samsung.jpg")
            .withSku("samsung-s9").withQuantity("100")
            .withTags("samsung", "smartphone").build();
    public static final Item GOOGLE = new ItemBuilder().withName("Google Pixel XL").withQuantity("3")
            .withSku("google-pixel-xl").withImage("docs/images/google.jpg").build();
    public static final Item LG = new ItemBuilder().withName("LG G7").withQuantity("20")
            .withSku("lg-67").withImage("docs/images/lg.jpg").withTags("lg").build();
    public static final Item HUAWEI = new ItemBuilder().withName("Hua Wei P20").withQuantity("1000")
            .withSku("huawei-p20").withImage("docs/images/huawei.jpg").build();
    public static final Item ONEPLUS = new ItemBuilder().withName("OnePlus 6").withQuantity("10")
            .withSku("oneplus-6").withImage("docs/images/oneplus.jpg").build();
    public static final Item HTC = new ItemBuilder().withName("HTC U6").withQuantity("75")
            .withSku("htc-u6").withImage("docs/images/htc.jpg").build();

    // Manually added
    public static final Item HOON = new ItemBuilder().withName("Hoon Meier").withQuantity("8482424")
            .withSku("stefan@example.com").withImage("little india").build();
    public static final Item IDA = new ItemBuilder().withName("Ida Mueller").withQuantity("8482131")
            .withSku("hans@example.com").withImage("chicago ave").build();

    // Manually added - Item's details found in {@code CommandTestUtil}
    public static final Item AMY = new ItemBuilder().withName(VALID_NAME_AMY).withQuantity(VALID_PHONE_AMY)
            .withSku(VALID_EMAIL_AMY).withImage(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Item BOB = new ItemBuilder().withName(VALID_NAME_BOB).withQuantity(VALID_PHONE_BOB)
            .withSku(VALID_EMAIL_BOB).withImage(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalItems() {} // prevents instantiation

    /**
     * Returns an {@code Inventory} with all the typical persons.
     */
    public static Inventory getTypicalAddressBook() {
        Inventory ab = new Inventory();
        for (Item item : getTypicalPersons()) {
            ab.addPerson(item);
        }
        return ab;
    }

    public static List<Item> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(IPHONE, SAMSUNG, GOOGLE, LG, HUAWEI, ONEPLUS, HTC));
    }
}
