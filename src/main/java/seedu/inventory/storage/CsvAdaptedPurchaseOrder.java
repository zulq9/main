package seedu.inventory.storage;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.purchaseorder.RequiredDate;
import seedu.inventory.model.purchaseorder.Supplier;

/**
 * Csv-friendly adapted version of the Sale.
 */
public class CsvAdaptedPurchaseOrder {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Purchase Order's %s field is missing!";
    public static final String MISSING_FIELD_MESSAGE = "Purchase Order's field is missing!";
    public static final String MISSING_ITEM = "Purchase Order item cannot be found!";

    private String sku;
    private String quantity;
    private String reqDate;
    private String supplier;
    private String status;


    /**
     * Constructs an {@code CsvAdaptedPurchaseOrder} with the given order details.
     */
    public CsvAdaptedPurchaseOrder(String sku, String quantity, String reqDate, String supplier,
                                   String status) {
        this.quantity = quantity;
        this.sku = sku;
        this.reqDate = reqDate == null ? null : reqDate.replaceAll("/", "-");
        this.supplier = supplier;
        this.status = status;
    }


    /**
     * Converts a given PurchaseOrder into this class for Csv use.
     *
     * @param source future changes to this will not affect the created CsvAdaptedPurchaseOrder
     */
    public CsvAdaptedPurchaseOrder(PurchaseOrder source) {
        sku = source.getSku().value;
        quantity = source.getQuantity().value;
        reqDate = source.getReqDate().requiredDate;
        supplier = source.getSupplier().supplierName;
        status = source.getStatus().name();
    }

    /**
     * Converts this Csv-friendly adapted purchase order object into the model's PurchaseOrder object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted purchase order.
     */
    public PurchaseOrder toModelType(ReadOnlyInventory inventory) throws IllegalValueException {
        requireNonNull(inventory);

        if (sku == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sku.class.getSimpleName()));
        }
        if (!Sku.isValidSku(sku)) {
            throw new IllegalValueException(Sku.MESSAGE_SKU_CONSTRAINTS);
        }
        final Sku modelSku = new Sku(sku);

        Item modelItem = inventory.getItemBySku(sku);

        if (modelItem == null) {
            throw new IllegalValueException(MISSING_ITEM);
        }

        if (quantity == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName())
            );
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (reqDate == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, RequiredDate.class.getSimpleName())
            );
        }
        if (!RequiredDate.isValidDate(reqDate)) {
            throw new IllegalValueException(RequiredDate.MESSAGE_DATE_CONSTRAINTS);
        }
        final RequiredDate modelRequiredDate = new RequiredDate(reqDate);

        if (supplier == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Supplier.class.getSimpleName())
            );
        }
        if (!Supplier.isValidSupplier(supplier)) {
            throw new IllegalValueException(Supplier.MESSAGE_SUPPLIER_CONSTRAINTS);
        }
        final Supplier modelSupplier = new Supplier(supplier);

        if (status == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, PurchaseOrder.Status.class.getSimpleName())
            );
        }
        if (!PurchaseOrder.Status.isValidStatus(status)) {
            throw new IllegalValueException(PurchaseOrder.Status.MESSAGE_STATUS_CONSTRAINTS);
        }
        final PurchaseOrder.Status modelStatus = PurchaseOrder.Status.valueOf(status);

        return new PurchaseOrder(modelSku, modelQuantity, modelRequiredDate, modelSupplier, modelStatus);
    }



    /**
     * Combine a Csv-friendly adapted purchase order into a list of string representing the content.
     *
     * @param purchaseOrder A Csv-friendly purchase order
     * @return content A list of string representing the content.
     */
    public static List<String> getContentFromPurchaseOrder(CsvAdaptedPurchaseOrder purchaseOrder) {
        List<String> content = new ArrayList<>();
        content.add(purchaseOrder.sku);
        content.add(purchaseOrder.quantity);
        content.add(purchaseOrder.reqDate);
        content.add(purchaseOrder.supplier);
        content.add(purchaseOrder.status);
        return content;
    }

    /**
     * Split a list of string representing the content of staff into the Csv-friendly adapted purchase order
     *
     * @param content A list of string representing the content of purchase order
     * @return The Csv-friendly adapted purchase order containing the content of the list.
     */
    public static CsvAdaptedPurchaseOrder splitContentToPurchaseOrder(List<String> content)
            throws IllegalValueException {
        if (content.size() < 5) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE);
        }
        String sku = content.get(0);
        String quantity = content.get(1);
        String reqDate = content.get(2);
        String supplier = content.get(3);
        String status = content.get(4);
        return new CsvAdaptedPurchaseOrder(sku, quantity, reqDate, supplier, status);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CsvAdaptedPurchaseOrder)) {
            return false;
        }

        CsvAdaptedPurchaseOrder otherPurchaseOrder = (CsvAdaptedPurchaseOrder) other;
        return Objects.equals(sku, otherPurchaseOrder.sku)
                && Objects.equals(quantity, otherPurchaseOrder.quantity)
                && Objects.equals(reqDate, otherPurchaseOrder.reqDate)
                && Objects.equals(supplier, otherPurchaseOrder.supplier)
                && Objects.equals(status, otherPurchaseOrder.status);
    }
}
