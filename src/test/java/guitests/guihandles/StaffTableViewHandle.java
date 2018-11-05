package guitests.guihandles;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import seedu.inventory.model.staff.Staff;

/**
 * Provides a handle for {@code StaffTableView} containing the list of {@code Staff}.
 */
public class StaffTableViewHandle extends NodeHandle<Node> {
    public static final String STAFF_TABLE_VIEW_ID = "#staffTableView";

    private final TableView staffTableView;

    public StaffTableViewHandle(Node staffTableView) {
        super(staffTableView);
        this.staffTableView = getChildNode(STAFF_TABLE_VIEW_ID);
    }

    /**
     * Returns the item list.
     */
    public List<Staff> getItemList() {
        return staffTableView.getItems();
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
        ObservableList<TableColumn> columns = staffTableView.getColumns();
        return columns.get(index).getText();
    }
}
