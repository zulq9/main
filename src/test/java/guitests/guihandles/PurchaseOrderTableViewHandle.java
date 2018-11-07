package guitests.guihandles;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * Provides a handle for {@code PurchaseOrderTableView} containing the list of {@code PurchaseOrder}.
 */
public class PurchaseOrderTableViewHandle extends NodeHandle<Node> {
    public static final String PURCHASE_ORDER_TABLE_VIEW_ID = "#purchaseOrderTableView";

    private final TableView purchaseOrderTableView;

    public PurchaseOrderTableViewHandle(Node purchaseOrderTableView) {
        super(purchaseOrderTableView);
        this.purchaseOrderTableView = getChildNode(PURCHASE_ORDER_TABLE_VIEW_ID);
    }

    /**
     * Returns the item list.
     */
    public List<PurchaseOrder> getItemList() {
        return purchaseOrderTableView.getItems();
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getItemList().size();
    }

    /**
     * Returns the header of TableColumn at index.
     */
    public String getColumnHeader(int index) {
        ObservableList<TableColumn> columns = purchaseOrderTableView.getColumns();
        return columns.get(index).getText();
    }
}
