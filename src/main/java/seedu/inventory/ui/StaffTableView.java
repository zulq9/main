package seedu.inventory.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

import seedu.inventory.model.staff.Staff;

/**
 * A table view containing the information of staffs.
 */
public class StaffTableView extends UiPart<Region> {
    private static final String FXML = "StaffTableView.fxml";

    @FXML
    private TableView<Staff> staffTableView;
    @FXML
    private TableColumn<Staff, String> username;
    @FXML
    private TableColumn<Staff, String> role;
    @FXML
    private TableColumn<Staff, String> staffName;

    public StaffTableView(ObservableList<Staff> staffList) {
        super(FXML);
        setConnections(staffList);
    }

    private void setConnections(ObservableList<Staff> staffList) {
        staffTableView.setItems(staffList);
        username.setCellValueFactory(staff -> new SimpleStringProperty(staff.getValue().getUsername().username));
        role.setCellValueFactory(staff -> new SimpleStringProperty(staff.getValue().getRole().toString()));
        staffName.setCellValueFactory(staff -> new SimpleStringProperty(staff.getValue().getStaffName().fullName));
    }
}
