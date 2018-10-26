package seedu.inventory.storage;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.PurchaseOrderList;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlyPurchaseOrderList;
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.model.StaffList;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.staff.Staff;

/**
 * An immutable purchase order list that is serializable to CSV format
 */
public class CsvSerializablePurchaseOrderList implements CsvSerializableData {
    public static final String DATA_TYPE = "Purchase Order";
    public static final String[] FIELDS = {"sku", "name", "quantity", "requiredDate", "supplier", "status"};

    private ReadOnlyInventory inventory;
    private List<CsvAdaptedPurchaseOrder> purchaseOrders;
    private List<List<String>> contents;


    /**
     * Creates an empty CsvSerializablePurchaseOrderList.
     */
    public CsvSerializablePurchaseOrderList() {
        purchaseOrders = new ArrayList<>();
        contents = getContentsFromPurchaseOrderList(purchaseOrders);
    }

    /**
     * Creates CsvSerializableStaffList from a list of content.
     */
    public CsvSerializablePurchaseOrderList(List<List<String>> contents) {
        this.contents = contents;
    }

    /**
     * Creates CsvSerializableStaffList from CsvSerializableData.
     */
    public CsvSerializablePurchaseOrderList(CsvSerializableData data) {
        this.contents = data.getContents();
    }

    /**
     * Conversion
     */
    public CsvSerializablePurchaseOrderList(ReadOnlyPurchaseOrderList src) {
        purchaseOrders = src.getPurchaseOrderList()
                .stream().map(CsvAdaptedPurchaseOrder::new).collect(Collectors.toList());
        contents = getContentsFromPurchaseOrderList(purchaseOrders);
    }

    /**
     * Set the Inventory of current purchase order list.
     */
    public void setInventory(ReadOnlyInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Converts this purchase order list into the model's {@code PurchaseOrderList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated {@code CsvAdaptedPurchaseOrder}.
     */
    public PurchaseOrderList toModelType() throws IllegalValueException {
        requireNonNull(inventory);

        purchaseOrders = splitContentsToPurchaseOrderList(contents);
        PurchaseOrderList purchaseOrderList = new PurchaseOrderList();

        for (CsvAdaptedPurchaseOrder p : purchaseOrders) {
            PurchaseOrder purchaseOrder = p.toModelType(inventory);
            purchaseOrderList.addPurchaseOrder(purchaseOrder);
        }
        return purchaseOrderList;
    }

    /**
     * Combine a list of Csv-friendly adapted purchase order into a list of list of string representing the contents.
     *
     * @param purchaseOrders A list of Csv-friendly purchase order
     * @return contents A list of list of string representing the contents.
     */
    public static List<List<String>> getContentsFromPurchaseOrderList(List<CsvAdaptedPurchaseOrder> purchaseOrders) {
        return purchaseOrders.stream()
                .map(CsvAdaptedPurchaseOrder::getContentFromPurchaseOrder).collect(Collectors.toList());
    }

    /**
     * Split a list of list of string representing the contents of purchase order list
     * into a list of Csv-friendly adapted purchase order.
     *
     * @param contents A list of list of string representing the contents of purchase order list
     * @return A list of Csv-friendly adapted purchase order containing the content of the list.
     */
    public static List<CsvAdaptedPurchaseOrder> splitContentsToPurchaseOrderList(List<List<String>> contents)
            throws IllegalValueException {
        List<CsvAdaptedPurchaseOrder> purchaseOrders = new ArrayList<>();
        for (List<String> content : contents) {
            purchaseOrders.add(CsvAdaptedPurchaseOrder.splitContentToPurchaseOrder(content));
        }
        return purchaseOrders;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CsvSerializablePurchaseOrderList)) {
            return false;
        }
        return contents.equals(((CsvSerializablePurchaseOrderList) other).contents);
    }

    @Override
    public List<List<String>> getContents() {
        return contents;
    }

    @Override
    public String getDataType() {
        return DATA_TYPE;
    }

    @Override
    public String[] getDataFields() {
        return FIELDS;
    }

    @Override
    public CsvSerializableData createInstance(List<List<String>> contents) {
        return new CsvSerializablePurchaseOrderList(contents);
    }
}
