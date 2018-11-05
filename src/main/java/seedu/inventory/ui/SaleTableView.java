package seedu.inventory.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

import seedu.inventory.model.sale.Sale;

/**
 * A table view containing the information of sales.
 */
public class SaleTableView extends UiPart<Region> {
    private static final String FXML = "SaleTableView.fxml";

    @FXML
    private TableView<Sale> saleTableView;
    @FXML
    private TableColumn<Sale, String> saleId;
    @FXML
    private TableColumn<Sale, String> saleSku;
    @FXML
    private TableColumn<Sale, String> saleQuantity;
    @FXML
    private TableColumn<Sale, String> saleDate;

    public SaleTableView(ObservableList<Sale> saleList) {
        super(FXML);
        setConnections(saleList);
    }

    private void setConnections(ObservableList<Sale> saleList) {
        saleTableView.setItems(saleList);
        saleId.setCellValueFactory(sale -> new SimpleStringProperty(sale.getValue().getSaleId().toString()));
        saleSku.setCellValueFactory(sale -> new SimpleStringProperty(sale.getValue().getItem().getSku().value));
        saleQuantity
                .setCellValueFactory(sale -> new SimpleStringProperty(sale.getValue().getSaleQuantity().toString()));
        saleDate.setCellValueFactory(sale -> new SimpleStringProperty(sale.getValue().getSaleDate().toString()));
    }
}
