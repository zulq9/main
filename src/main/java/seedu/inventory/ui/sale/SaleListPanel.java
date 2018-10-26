package seedu.inventory.ui.sale;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.events.ui.JumpToSalesListRequestEvent;
import seedu.inventory.commons.events.ui.SalePanelSelectionChangedEvent;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.ui.UiPart;

/**
 * Panel containing the list of items.
 */
public class SaleListPanel extends UiPart<Region> {
    private static final String FXML = "SaleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SaleListPanel.class);

    @FXML
    private ListView<Sale> saleListView;

    public SaleListPanel(ObservableList<Sale> saleList) {
        super(FXML);
        setConnections(saleList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Sale> saleList) {
        saleListView.setItems(saleList);
        saleListView.setCellFactory(listView -> new SaleListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        saleListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in item list panel changed to : '" + newValue + "'");
                        raise(new SalePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ItemCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            saleListView.scrollTo(index);
            saleListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToSalesListRequestEvent(JumpToSalesListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Item} using a {@code ItemCard}.
     */
    class SaleListViewCell extends ListCell<Sale> {
        @Override
        protected void updateItem(Sale sale, boolean empty) {
            super.updateItem(sale, empty);

            if (empty || sale == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SaleCard(sale).getRoot());
            }
        }
    }

}
