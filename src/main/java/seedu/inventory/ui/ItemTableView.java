package seedu.inventory.ui;

import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

import seedu.inventory.model.item.Item;
import seedu.inventory.storage.csv.CsvAdaptedTag;

/**
 * A table view containing the information of items.
 */
public class ItemTableView extends UiPart<Region> {
    private static final String FXML = "ItemTableView.fxml";

    @FXML
    private TableView<Item> itemTableView;
    @FXML
    private TableColumn<Item, String> name;
    @FXML
    private TableColumn<Item, String> price;
    @FXML
    private TableColumn<Item, String> quantity;
    @FXML
    private TableColumn<Item, String> sku;
    @FXML
    private TableColumn<Item, String> tag;

    public ItemTableView(ObservableList<Item> itemList) {
        super(FXML);
        setConnections(itemList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Item> itemList) {
        itemTableView.setItems(itemList);
        name.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getName().fullName));
        price.setCellValueFactory(item -> new SimpleStringProperty("$" + item.getValue().getPrice().value));
        quantity.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getQuantity().value));
        sku.setCellValueFactory(item -> new SimpleStringProperty(item.getValue().getSku().value));
        tag.setCellValueFactory(item -> new SimpleStringProperty(CsvAdaptedTag
                .combineTags(item.getValue().getTags().stream().map(CsvAdaptedTag::new).collect(Collectors.toList()))));
    }
}
