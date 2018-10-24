package seedu.inventory.ui;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.inventory.MainApp;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.events.ui.ItemPanelSelectionChangedEvent;
import seedu.inventory.commons.events.ui.PurchaseOrderSelectionChangedEvent;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    public static final String DEFAULT_PAGE = "default.html";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private ImageView photo;

    @FXML
    private VBox itemDetailPane;

    @FXML
    private Label name;

    @FXML
    private Label price;

    @FXML
    private Label quantity;

    @FXML
    private Label sku;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadItemPage(Item item) {
        itemDetailPane.setBackground(null);
        loadPage(item.getImage().toString(), item.getName().fullName, item.getPrice().toString(),
                item.getQuantity().toString(), item.getSku().toString());
    }

    private void loadPurchaseOrderPage(PurchaseOrder po) {
        //loadPage(SEARCH_PAGE_URL + po.getName().fullName);
    }

    public void loadPage(String url, String name, String price, String quantity, String sku) {
        File file = new File(url);

        if (file.exists()) {
            Platform.runLater(() -> photo.setImage(
                    new Image("file:" + url, 300, 300, false, true))
            );
        } else {
            Platform.runLater(() -> photo.setImage(
                    new Image(url, 300, 300, false, true))
            );
        }

        Platform.runLater(() -> this.name.setText(name));
        Platform.runLater(() -> this.price.setText("Price: $" + price));
        Platform.runLater(() -> this.quantity.setText("Quantity: " + quantity));
        Platform.runLater(() -> this.sku.setText("SKU: " + sku));
    }

    /**
     * Loads the default image file to set as the StackPane's background image that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource("/images/UI.png");
        itemDetailPane.setBackground(
            new Background(
                new BackgroundImage(
                    new Image(defaultPage.toExternalForm()),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, BackgroundSize.DEFAULT)
            )
        );
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        itemDetailPane = null;
        photo = null;
    }

    @Subscribe
    private void handleItemPanelSelectionChangedEvent(ItemPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadItemPage(event.getNewSelection());
    }

    @Subscribe
    private void handlePurchaseOrderPanelSelectionChangedEvent(PurchaseOrderSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPurchaseOrderPage(event.getNewSelection());
    }
}
