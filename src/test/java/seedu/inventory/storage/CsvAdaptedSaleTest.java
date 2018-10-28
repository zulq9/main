package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.inventory.storage.CsvAdaptedSale.MISSING_FIELD_MESSAGE_FORMAT;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;
import seedu.inventory.testutil.Assert;
import seedu.inventory.testutil.TypicalItems;

public class CsvAdaptedSaleTest {
    private static final String INVALID_SALE_ID = "ABC";
    private static final String INVALID_SALE_SKU = "ABC.123";
    private static final String INVALID_SALE_QUANTITY = "+651234";
    private static final String INVALID_SALE_DATE = "01/01/1990";

    private static final String VALID_SALE_ID = "1";
    private static final String VALID_SALE_SKU = TypicalItems.IPHONE.getSku().toString();
    private static final String VALID_SALE_QUANTITY = "1";
    private static final String VALID_SALE_DATE_ONE = "2018-08-01";
    private static final String VALID_SALE_DATE_TWO = "2018/08/01";

    private static final String NOT_FOUND_SKU = "ABCDEFG";

    private static SaleId saleId = new SaleId("1");
    private static Item item = TypicalItems.IPHONE;
    private static Quantity quantity = new Quantity("1");
    private static SaleDate saleDate = new SaleDate("2018-08-01");

    @Test
    public void toModelType_validSaleDetails_returnsSale() throws Exception {
        Sale sale = new Sale(saleId, item, quantity, saleDate);
        Inventory inventory = new Inventory();
        inventory.addItem(item);
        CsvAdaptedSale adaptedSale = new CsvAdaptedSale(sale);
        assertEquals(sale, adaptedSale.toModelType(inventory));
        adaptedSale = new CsvAdaptedSale(VALID_SALE_ID, VALID_SALE_SKU, VALID_SALE_QUANTITY, VALID_SALE_DATE_ONE);
        assertEquals(sale, adaptedSale.toModelType(inventory));
        adaptedSale = new CsvAdaptedSale(VALID_SALE_ID, VALID_SALE_SKU, VALID_SALE_QUANTITY, VALID_SALE_DATE_TWO);
        assertEquals(sale, adaptedSale.toModelType(inventory));

    }

    @Test
    public void toModelType_invalidSaleID_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);
        CsvAdaptedSale adaptedSale =
                new CsvAdaptedSale(INVALID_SALE_ID, VALID_SALE_SKU, VALID_SALE_QUANTITY, VALID_SALE_DATE_ONE);
        String expectedMessage = SaleId.MESSAGE_ID_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_nullSaleID_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);
        CsvAdaptedSale adaptedSale =
                new CsvAdaptedSale(null, VALID_SALE_SKU, VALID_SALE_QUANTITY, VALID_SALE_DATE_ONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, SaleId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_invalidSaleSku_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);
        CsvAdaptedSale adaptedSale =
                new CsvAdaptedSale(VALID_SALE_ID, INVALID_SALE_SKU, VALID_SALE_QUANTITY, VALID_SALE_DATE_ONE);
        String expectedMessage = Sku.MESSAGE_SKU_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_nullSaleSku_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);
        CsvAdaptedSale adaptedSale =
                new CsvAdaptedSale(VALID_SALE_ID, null, VALID_SALE_QUANTITY, VALID_SALE_DATE_ONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Sku.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_invalidSaleQuantity_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);
        CsvAdaptedSale adaptedSale =
                new CsvAdaptedSale(VALID_SALE_ID, VALID_SALE_SKU, INVALID_SALE_QUANTITY, VALID_SALE_DATE_ONE);
        String expectedMessage = Quantity.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_nullSaleQuantity_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);
        CsvAdaptedSale adaptedSale =
                new CsvAdaptedSale(VALID_SALE_ID, VALID_SALE_SKU, null, VALID_SALE_DATE_ONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_invalidSaleDate_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);
        CsvAdaptedSale adaptedSale =
                new CsvAdaptedSale(VALID_SALE_ID, VALID_SALE_SKU, VALID_SALE_QUANTITY, INVALID_SALE_DATE);
        String expectedMessage = SaleDate.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_nullSaleDate_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);
        CsvAdaptedSale adaptedSale =
                new CsvAdaptedSale(VALID_SALE_ID, VALID_SALE_SKU, VALID_SALE_QUANTITY, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, SaleDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_itemNotFound_throwsIllegalValueException() {
        Inventory inventory = new Inventory();
        inventory.addItem(item);
        CsvAdaptedSale adaptedSale =
                new CsvAdaptedSale(VALID_SALE_ID, NOT_FOUND_SKU, VALID_SALE_QUANTITY, INVALID_SALE_DATE);
        String expectedMessage = CsvAdaptedSale.MISSING_ITEM;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> adaptedSale.toModelType(inventory));
    }

    @Test
    public void toModelType_nullInventory_throwsIllegalValueException() {
        CsvAdaptedSale adaptedSale =
                new CsvAdaptedSale(VALID_SALE_ID, VALID_SALE_SKU, VALID_SALE_QUANTITY, VALID_SALE_DATE_ONE);
        Assert.assertThrows(NullPointerException.class, () -> adaptedSale.toModelType(null));
    }

    @Test
    public void splitContentToSale_validContent() throws Exception {
        List<String> validContent = new ArrayList<>();
        validContent.add(VALID_SALE_ID);
        validContent.add(VALID_SALE_SKU);
        validContent.add(VALID_SALE_QUANTITY);
        validContent.add(VALID_SALE_DATE_ONE);
        CsvAdaptedSale validSale =
                new CsvAdaptedSale(VALID_SALE_ID, VALID_SALE_SKU, VALID_SALE_QUANTITY, VALID_SALE_DATE_ONE);

        assertEquals(CsvAdaptedSale.splitContentToSale(validContent), validSale);
    }

    @Test
    public void splitContentToSale_invalidContent_illegalValueException() throws Exception {
        List<String> invalidContent = new ArrayList<>();
        invalidContent.add(VALID_SALE_ID);
        invalidContent.add(VALID_SALE_SKU);
        invalidContent.add(VALID_SALE_QUANTITY);
        Assert.assertThrows(IllegalValueException.class, () -> CsvAdaptedSale.splitContentToSale(invalidContent));
    }

    @Test
    public void getContentFromItem() throws Exception {
        List<String> validContent = new ArrayList<>();
        validContent.add(VALID_SALE_ID);
        validContent.add(VALID_SALE_SKU);
        validContent.add(VALID_SALE_QUANTITY);
        validContent.add(VALID_SALE_DATE_ONE);
        CsvAdaptedSale validSale =
                new CsvAdaptedSale(VALID_SALE_ID, VALID_SALE_SKU, VALID_SALE_QUANTITY, VALID_SALE_DATE_ONE);

        assertEquals(CsvAdaptedSale.getContentFromSale(validSale), validContent);
    }

    @Test
    public void equals() {
        CsvAdaptedSale validSale =
                new CsvAdaptedSale(VALID_SALE_ID, VALID_SALE_SKU, VALID_SALE_QUANTITY, VALID_SALE_DATE_ONE);
        CsvAdaptedSale anotherValidSale =
                new CsvAdaptedSale(VALID_SALE_ID, VALID_SALE_SKU, VALID_SALE_QUANTITY, VALID_SALE_DATE_TWO);
        CsvAdaptedSale invalidSale =
                new CsvAdaptedSale(INVALID_SALE_ID, VALID_SALE_SKU, VALID_SALE_QUANTITY, VALID_SALE_DATE_TWO);

        assertEquals(validSale, anotherValidSale);
        assertEquals(validSale, validSale);
        assertNotEquals(validSale, CsvAdaptedSale.getContentFromSale(validSale));
        assertNotEquals(validSale, invalidSale);
    }
}
