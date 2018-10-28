package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.inventory.storage.CsvAdaptedPurchaseOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.inventory.testutil.TypicalItems.LG;
import static seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder.LGPO;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.purchaseorder.RequiredDate;
import seedu.inventory.model.purchaseorder.Supplier;
import seedu.inventory.testutil.Assert;
import seedu.inventory.testutil.TypicalItems;

public class CsvAdaptedPurchaseOrderTest {

    private static final String INVALID_QUANTITY = "32_+14";
    private static final String INVALID_SKU = "exam12.test";
    private static final String INVALID_REQUIRED_DATE = "21-asd-2";
    private static final String INVALID_SUPPLIER = "";
    private static final String INVALID_STATUS = "OK CAN";

    private static final String VALID_QUANTITY = LG.getQuantity().toString();
    private static final String VALID_SKU = LG.getSku().toString();
    private static final String VALID_REQUIRED_DATE = LGPO.getReqDate().toString();
    private static final String VALID_SUPPLIER = LGPO.getSupplier().toString();
    private static final String VALID_STATUS = LGPO.getStatus().toString();

    private static final String NOT_FOUND_SKU = "ABCDEFG";

    private Inventory inventory = TypicalItems.getTypicalInventory();

    @Test
    public void toModelType_validPurchaseOrderDetails_returnsPurchaseOrder() throws Exception {
        CsvAdaptedPurchaseOrder po = new CsvAdaptedPurchaseOrder(LGPO);
        assertEquals(LGPO.toString(), po.toModelType(inventory).toString());
    }

    @Test
    public void toModelType_invalidSku_throwsIllegalValueException() {
        CsvAdaptedPurchaseOrder po = new CsvAdaptedPurchaseOrder(INVALID_SKU, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = Sku.MESSAGE_SKU_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> po.toModelType(inventory));
    }

    @Test
    public void toModelType_nullSku_throwsIllegalValueException() {
        CsvAdaptedPurchaseOrder po = new CsvAdaptedPurchaseOrder(null, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Sku.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> po.toModelType(inventory));
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        CsvAdaptedPurchaseOrder po = new CsvAdaptedPurchaseOrder(VALID_SKU, INVALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = Quantity.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> po.toModelType(inventory));
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        CsvAdaptedPurchaseOrder po = new CsvAdaptedPurchaseOrder(VALID_SKU, null,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> po.toModelType(inventory));
    }

    @Test
    public void toModelType_invalidRequiredDate_throwsIllegalValueException() {
        CsvAdaptedPurchaseOrder po = new CsvAdaptedPurchaseOrder(VALID_SKU, VALID_QUANTITY,
                INVALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = RequiredDate.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> po.toModelType(inventory));
    }

    @Test
    public void toModelType_nullRequiredDate_throwsIllegalValueException() {
        CsvAdaptedPurchaseOrder po = new CsvAdaptedPurchaseOrder(VALID_SKU, VALID_QUANTITY,
                null, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RequiredDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> po.toModelType(inventory));
    }
    @Test
    public void toModelType_invalidSupplier_throwsIllegalValueException() {
        CsvAdaptedPurchaseOrder po = new CsvAdaptedPurchaseOrder(VALID_SKU, VALID_QUANTITY,
                VALID_REQUIRED_DATE, INVALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = Supplier.MESSAGE_SUPPLIER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> po.toModelType(inventory));
    }

    @Test
    public void toModelType_nullSupplier_throwsIllegalValueException() {
        CsvAdaptedPurchaseOrder po = new CsvAdaptedPurchaseOrder(VALID_SKU, VALID_QUANTITY,
                VALID_REQUIRED_DATE, null, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Supplier.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> po.toModelType(inventory));
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        CsvAdaptedPurchaseOrder po = new CsvAdaptedPurchaseOrder(VALID_SKU, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, INVALID_STATUS);
        String expectedMessage = PurchaseOrder.Status.MESSAGE_STATUS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> po.toModelType(inventory));
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        CsvAdaptedPurchaseOrder po = new CsvAdaptedPurchaseOrder(VALID_SKU, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                PurchaseOrder.Status.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> po.toModelType(inventory));
    }

    @Test
    public void toModelType_nullInventory_throwsIllegalValueException() {
        CsvAdaptedPurchaseOrder po = new CsvAdaptedPurchaseOrder(VALID_SKU, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        Assert.assertThrows(NullPointerException.class, () -> po.toModelType((Inventory) null));
    }

    @Test
    public void toModelType_itemNotFound_throwsIllegalValueException() {
        CsvAdaptedPurchaseOrder po = new CsvAdaptedPurchaseOrder(NOT_FOUND_SKU, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        String expectedMessage = CsvAdaptedPurchaseOrder.MISSING_ITEM;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> po.toModelType(inventory));
    }

    @Test
    public void splitContentToSale_validContent() throws Exception {
        List<String> validContent = new ArrayList<>();
        validContent.add(VALID_SKU);
        validContent.add(VALID_QUANTITY);
        validContent.add(VALID_REQUIRED_DATE);
        validContent.add(VALID_SUPPLIER);
        validContent.add(VALID_STATUS);
        CsvAdaptedPurchaseOrder validPo = new CsvAdaptedPurchaseOrder(VALID_SKU, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);

        assertEquals(CsvAdaptedPurchaseOrder.splitContentToPurchaseOrder(validContent), validPo);
    }

    @Test
    public void splitContentToSale_invalidContent_illegalValueException() throws Exception {
        List<String> invalidContent = new ArrayList<>();
        invalidContent.add(VALID_SKU);
        invalidContent.add(VALID_QUANTITY);
        invalidContent.add(VALID_REQUIRED_DATE);
        invalidContent.add(VALID_SUPPLIER);
        Assert.assertThrows(IllegalValueException.class, () -> CsvAdaptedPurchaseOrder
                .splitContentToPurchaseOrder(invalidContent));
    }

    @Test
    public void getContentFromItem() throws Exception {
        List<String> validContent = new ArrayList<>();
        validContent.add(VALID_SKU);
        validContent.add(VALID_QUANTITY);
        validContent.add(VALID_REQUIRED_DATE);
        validContent.add(VALID_SUPPLIER);
        validContent.add(VALID_STATUS);
        CsvAdaptedPurchaseOrder validPo = new CsvAdaptedPurchaseOrder(VALID_SKU, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);

        assertEquals(CsvAdaptedPurchaseOrder.getContentFromPurchaseOrder(validPo), validContent);
    }

    @Test
    public void equals() {
        CsvAdaptedPurchaseOrder validPo = new CsvAdaptedPurchaseOrder(VALID_SKU, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        CsvAdaptedPurchaseOrder anotherValidPo = new CsvAdaptedPurchaseOrder(VALID_SKU, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);
        CsvAdaptedPurchaseOrder invalidPo = new CsvAdaptedPurchaseOrder(INVALID_SKU, VALID_QUANTITY,
                VALID_REQUIRED_DATE, VALID_SUPPLIER, VALID_STATUS);

        assertEquals(validPo, anotherValidPo);
        assertEquals(validPo, validPo);
        assertNotEquals(validPo, CsvAdaptedPurchaseOrder.getContentFromPurchaseOrder(validPo));
        assertNotEquals(validPo, invalidPo);
    }
}
