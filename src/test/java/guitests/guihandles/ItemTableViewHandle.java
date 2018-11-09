package guitests.guihandles;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import seedu.inventory.model.item.Item;

/**
 * Provides a handle for {@code ItemTableView} containing the list of {@code Item}.
 */
public class ItemTableViewHandle extends NodeHandle<Node> {
    public static final String ITEM_TABLE_VIEW_ID = "#itemTableView";

    private final TableView itemTableView;

    public ItemTableViewHandle(Node itemTableView) {
        super(itemTableView);
        this.itemTableView = getChildNode(ITEM_TABLE_VIEW_ID);
    }

    /**
     * Returns the item list.
     */
    public List<Item> getItemList() {
        return itemTableView.getItems();
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
        ObservableList<TableColumn> columns = itemTableView.getColumns();
        return columns.get(index).getText();
    }
}
