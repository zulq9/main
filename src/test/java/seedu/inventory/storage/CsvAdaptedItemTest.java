package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.inventory.storage.CsvAdaptedItem.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.inventory.testutil.TypicalItems.SAMSUNG;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.item.Image;
import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Price;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.testutil.Assert;


public class CsvAdaptedItemTest {
    private static final String INVALID_IMAGE = " ";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PRICE = "99.";
    private static final String INVALID_QUANTITY = "+651234";
    private static final String INVALID_SKU = "example.test";
    private static final String INVALID_TAG = "#gadget";

    private static final String VALID_IMAGE = SAMSUNG.getImage().toString();
    private static final String VALID_NAME = SAMSUNG.getName().toString();
    private static final String VALID_PRICE = SAMSUNG.getPrice().toString();
    private static final String VALID_QUANTITY = SAMSUNG.getQuantity().toString();
    private static final String VALID_SKU = SAMSUNG.getSku().toString();
    private static final List<CsvAdaptedTag> VALID_TAGS = SAMSUNG.getTags().stream()
            .map(CsvAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_COMBINED_TAGS = "samsung,smartphone";
    private static final String INVALID_COMBINED_TAGS = "samsung,smartphone,#gadget";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_validItem_returnsItem() throws Exception {
        CsvAdaptedItem item = new CsvAdaptedItem(SAMSUNG);
        assertEquals(SAMSUNG, item.toModelType());
    }

    @Test
    public void toModelType_validItemDetails_returnsItem() throws Exception {
        CsvAdaptedItem item =
                new CsvAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, VALID_SKU, VALID_IMAGE, VALID_TAGS);
        assertEquals(SAMSUNG, item.toModelType());
        item = new CsvAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, VALID_SKU, VALID_IMAGE, VALID_COMBINED_TAGS);
        assertEquals(SAMSUNG, item.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        CsvAdaptedItem item =
                new CsvAdaptedItem(INVALID_NAME, VALID_PRICE, VALID_QUANTITY, VALID_SKU, VALID_IMAGE, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        CsvAdaptedItem item = new CsvAdaptedItem(null, VALID_PRICE, VALID_QUANTITY, VALID_SKU,
                VALID_IMAGE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        CsvAdaptedItem item =
                new CsvAdaptedItem(VALID_NAME, INVALID_PRICE, VALID_QUANTITY, VALID_SKU, VALID_IMAGE, VALID_TAGS);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        CsvAdaptedItem item = new CsvAdaptedItem(VALID_NAME, null, VALID_QUANTITY, VALID_SKU,
                VALID_IMAGE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        CsvAdaptedItem item =
                new CsvAdaptedItem(VALID_NAME, VALID_PRICE, INVALID_QUANTITY, VALID_SKU, VALID_IMAGE, VALID_TAGS);
        String expectedMessage = Quantity.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        CsvAdaptedItem item = new CsvAdaptedItem(VALID_NAME, VALID_PRICE, null, VALID_SKU,
                VALID_IMAGE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidSku_throwsIllegalValueException() {
        CsvAdaptedItem item =
                new CsvAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, INVALID_SKU, VALID_IMAGE, VALID_TAGS);
        String expectedMessage = Sku.MESSAGE_SKU_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullSku_throwsIllegalValueException() {
        CsvAdaptedItem item = new CsvAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, null,
                VALID_IMAGE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Sku.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidImage_throwsIllegalValueException() {
        CsvAdaptedItem item =
                new CsvAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, VALID_SKU, INVALID_IMAGE, VALID_TAGS);
        String expectedMessage = Image.MESSAGE_IMAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullImage_throwsIllegalValueException() {
        CsvAdaptedItem item = new CsvAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, VALID_SKU, null,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Image.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<CsvAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new CsvAdaptedTag(INVALID_TAG));
        CsvAdaptedItem item =
                new CsvAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, VALID_SKU, VALID_IMAGE, invalidTags);
        Assert.assertThrows(IllegalValueException.class, item::toModelType);
    }

    @Test
    public void toModelType_invalidCombinedTags_throwsIllegalValueException() {
        CsvAdaptedItem item =
               new CsvAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY,
                        VALID_SKU, VALID_IMAGE, INVALID_COMBINED_TAGS);
        Assert.assertThrows(IllegalValueException.class, item::toModelType);
    }

    @Test
    public void equals() {
        CsvAdaptedItem validItem =
                new CsvAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, VALID_SKU, VALID_IMAGE, VALID_TAGS);
        CsvAdaptedItem anotherValidItem =
                new CsvAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY,
                        VALID_SKU, VALID_IMAGE, VALID_COMBINED_TAGS);
        CsvAdaptedItem inValidItem =
                new CsvAdaptedItem(INVALID_NAME, VALID_PRICE, VALID_QUANTITY,
                        VALID_SKU, VALID_IMAGE, VALID_COMBINED_TAGS);

        assertEquals(validItem, anotherValidItem);
        assertEquals(validItem, validItem);
        assertNotEquals(validItem, CsvAdaptedItem.getContentList(validItem));
        assertNotEquals(validItem, inValidItem);
    }

    @Test
    public void splitContentToItem_validContent() throws Exception {
        List<String> validContent = new ArrayList<>();
        validContent.add(VALID_NAME);
        validContent.add(VALID_PRICE);
        validContent.add(VALID_QUANTITY);
        validContent.add(VALID_SKU);
        validContent.add(VALID_IMAGE);
        validContent.add(VALID_COMBINED_TAGS);
        CsvAdaptedItem validItem =
                new CsvAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, VALID_SKU, VALID_IMAGE, VALID_TAGS);

        assertEquals(CsvAdaptedItem.splitContentToItem(validContent), validItem);
    }

    @Test
    public void splitContentToItem_invalidContent_illegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        List<String> invalidContent = new ArrayList<>();
        invalidContent.add(VALID_NAME);
        invalidContent.add(VALID_PRICE);
        invalidContent.add(VALID_QUANTITY);
        invalidContent.add(VALID_SKU);
        CsvAdaptedItem.splitContentToItem(invalidContent);
    }

    @Test
    public void getContentList() throws Exception {
        List<String> validContent = new ArrayList<>();
        validContent.add(VALID_NAME);
        validContent.add(VALID_PRICE);
        validContent.add(VALID_QUANTITY);
        validContent.add(VALID_SKU);
        validContent.add(VALID_IMAGE);
        validContent.add(VALID_COMBINED_TAGS);
        CsvAdaptedItem validItem =
                new CsvAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, VALID_SKU, VALID_IMAGE, VALID_TAGS);

        assertEquals(CsvAdaptedItem.getContentList(validItem), validContent);
    }

}
