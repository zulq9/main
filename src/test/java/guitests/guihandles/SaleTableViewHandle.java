package guitests.guihandles;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import seedu.inventory.model.sale.Sale;

/**
 * Provides a handle for {@code SaleTableView} containing the list of {@code Sale}.
 */
public class SaleTableViewHandle extends NodeHandle<Node> {
    public static final String SALE_TABLE_VIEW_ID = "#saleTableView";

    private final TableView saleTableView;

    public SaleTableViewHandle(Node saleTableView) {
        super(saleTableView);
        this.saleTableView = getChildNode(SALE_TABLE_VIEW_ID);
    }

    /**
     * Returns the sale list.
     */
    public List<Sale> getItemList() {
        return saleTableView.getItems();
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
        ObservableList<TableColumn> columns = saleTableView.getColumns();
        return columns.get(index).getText();
    }
}
