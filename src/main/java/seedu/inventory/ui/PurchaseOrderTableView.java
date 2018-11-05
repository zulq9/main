package seedu.inventory.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * A table view containing the information of purchase orders.
 */
public class PurchaseOrderTableView extends UiPart<Region> {
    private static final String FXML = "PurchaseOrderTableView.fxml";

    @FXML
    private TableView<PurchaseOrder> purchaseOrderTableView;
    @FXML
    private TableColumn<PurchaseOrder, String> sku;
    @FXML
    private TableColumn<PurchaseOrder, String> quantity;
    @FXML
    private TableColumn<PurchaseOrder, String> date;
    @FXML
    private TableColumn<PurchaseOrder, String> supplier;
    @FXML
    private TableColumn<PurchaseOrder, String> status;

    public PurchaseOrderTableView(ObservableList<PurchaseOrder> purchaseOrderList) {
        super(FXML);
        setConnections(purchaseOrderList);
    }

    private void setConnections(ObservableList<PurchaseOrder> purchaseOrdersList) {
        purchaseOrderTableView.setItems(purchaseOrdersList);
        sku.setCellValueFactory(purchaseOrder -> new SimpleStringProperty(purchaseOrder.getValue().getSku().value));
        quantity.setCellValueFactory(purchaseOrder ->
                new SimpleStringProperty(purchaseOrder.getValue().getQuantity().value));
        date.setCellValueFactory(purchaseOrder ->
                new SimpleStringProperty(purchaseOrder.getValue().getReqDate().requiredDate));
        supplier.setCellValueFactory(purchaseOrder ->
                new SimpleStringProperty(purchaseOrder.getValue().getSupplier().supplierName));
        status.setCellValueFactory(purchaseOrder ->
                new SimpleStringProperty(purchaseOrder.getValue().getStatus().toString()));
    }
}
