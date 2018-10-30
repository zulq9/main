package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * Provides a handle to a purhcase order card in the purchase order list panel.
 */
public class PurchaseOrderCardHandle extends NodeHandle<Node> {

    private static final String ID_FIELD_ID = "#id";
    private static final String SKU_FIELD_ID = "#sku";
    private static final String QUANTITY_FIELD_ID = "#quantity";
    private static final String REQUIRE_DATE_FIELD_ID = "#reqDate";
    private static final String SUPPLIER_FIELD_ID = "#supplier";
    private static final String STATUS_FIELD_ID = "#status";

    private final Label idLabel;
    private final Label skuLabel;
    private final Label quantityLabel;
    private final Label reqDateLabel;
    private final Label supplierLabel;
    private final Label statusLabel;

    public PurchaseOrderCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        skuLabel = getChildNode(SKU_FIELD_ID);
        quantityLabel = getChildNode(QUANTITY_FIELD_ID);
        reqDateLabel = getChildNode(REQUIRE_DATE_FIELD_ID);
        supplierLabel = getChildNode(SUPPLIER_FIELD_ID);
        statusLabel = getChildNode(STATUS_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getSku() {
        return skuLabel.getText();
    }

    public String getQuantity() {
        return quantityLabel.getText();
    }

    public String getReqDate() {
        return reqDateLabel.getText();
    }

    public String getSupplier() {
        return supplierLabel.getText();
    }

    public String getStatus() {
        return statusLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code po}.
     */
    public boolean equals(PurchaseOrder po) {
        return getSku().equals(po.getSku().value)
                && getQuantity().equals(po.getQuantity().value)
                && getReqDate().equals(po.getReqDate().requiredDate)
                && getSupplier().equals(po.getSupplier().supplierName)
                && getStatus().equals(po.getStatus().name());
    }
}
