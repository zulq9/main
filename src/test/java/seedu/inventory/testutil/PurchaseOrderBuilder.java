package seedu.inventory.testutil;

import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.purchaseorder.Quantity;
import seedu.inventory.model.purchaseorder.RequiredDate;
import seedu.inventory.model.purchaseorder.Supplier;

/**
 * A utility class to help with building Purchase order objects.
 */
public class PurchaseOrderBuilder {

    public static final String DEFAULT_QUANTITY = "10";
    public static final String DEFAULT_REQUIRED_DATE = "2018-12-31";
    public static final String DEFAULT_SUPPLIER = "Apple Inc.";
    public static final PurchaseOrder.Status DEFAULT_STATUS = PurchaseOrder.Status.PENDING;
    public static final Item DEFAULT_ITEM = TypicalItems.IPHONE;

    private Item item;
    private Quantity quantity;
    private RequiredDate reqDate;
    private Supplier supplier;
    private PurchaseOrder.Status status;

    public PurchaseOrderBuilder() {
        item = DEFAULT_ITEM;
        quantity = new Quantity(DEFAULT_QUANTITY);
        reqDate = new RequiredDate(DEFAULT_REQUIRED_DATE);
        supplier = new Supplier(DEFAULT_SUPPLIER);
        status = DEFAULT_STATUS;
    }

    /**
     * Initializes the PurchaseOrderBuilder with the data of {@code purchaseOrder}
     *
     * @param poToCopy the Purchase order to be copied
     */
    public PurchaseOrderBuilder(PurchaseOrder poToCopy) {
        item = poToCopy.getItem();
        quantity = poToCopy.getQuantity();
        reqDate = poToCopy.getReqDate();
        supplier = poToCopy.getSupplier();
        status = poToCopy.getStatus();
    }

    /**
     * Sets the {@code Item} of the {@code PurchaseOrder} that we are building.
     */
    public PurchaseOrderBuilder withItem(Item item) {
        this.item = item;
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code PurchaseOrder} that we are building.
     */
    public PurchaseOrderBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code RequiredDate} of the {@code PurchaseOrder} that we are building.
     */
    public PurchaseOrderBuilder withRequiredDate(String reqDate) {
        this.reqDate = new RequiredDate(reqDate);
        return this;
    }

    /**
     * Sets the {@code Supplier} of the {@code PurchaseOrder} that we are building.
     */
    public PurchaseOrderBuilder withSupplier(String supplier) {
        this.supplier = new Supplier(supplier);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code PurchaseOrder} that we are building.
     */
    public PurchaseOrderBuilder withStatus(PurchaseOrder.Status status) {
        this.status = status;
        return this;
    }

    public PurchaseOrder build() {
        return new PurchaseOrder(item, quantity, reqDate, supplier, status);
    }
}
