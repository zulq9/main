package seedu.inventory.ui.staff;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.events.ui.StaffPanelSelectionChangedEvent;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.ui.UiPart;

/**
 * Panel containing the list of staffs.
 */
public class StaffCardListPanel extends UiPart<Region> {
    public static final String FXML = "StaffListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StaffCardListPanel.class);

    @FXML
    private ListView<Staff> staffListView;

    public StaffCardListPanel(ObservableList<Staff> staffList) {
        super(FXML);
        setConnections(staffList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Staff> staffList) {
        staffListView.setItems(staffList);
        staffListView.setCellFactory(listView -> new StaffListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        staffListView.getSelectionModel().selectedItemProperty()
                .addListener((observable , oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in staff list panel changed to : '" + newValue + "'");
                        raise(new StaffPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Staff} using a {@code StaffCard}/
     */
    class StaffListViewCell extends ListCell<Staff> {
        @Override
        protected void updateItem(Staff staff, boolean empty) {
            super.updateItem(staff, empty);

            if (empty || staff == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StaffCard(staff, getIndex() + 1).getRoot());
            }
        }
    }
}
