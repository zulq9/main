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
import seedu.inventory.commons.events.ui.ClearBrowserPanelEvent;
import seedu.inventory.commons.events.ui.ShowBrowserPanelEvent;
import seedu.inventory.commons.events.ui.ShowDefaultPageEvent;
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

    /**
     * Load the item details and display it in the browser panel
     *
     * @param item
     */
    private void loadItemPage(Item item) {
        itemDetailPane.setBackground(null);
        setItemVisibility(true);
        loadPage(item.getImage().toString(), item.getName().fullName, item.getPrice().toString(),
                item.getQuantity().toString(), item.getSku().toString());
    }

    /**
     * Load the purchase order details and display it in the browser panel
     *
     * @param po
     */
    private void loadPurchaseOrderPage(PurchaseOrder po) {
        //TODO: Load purchase order page
        itemDetailPane.setBackground(null);
        setItemVisibility(false);
    }

    private void setItemVisibility(Boolean show) {
        photo.setVisible(show);
        sku.setVisible(show);
        name.setVisible(show);
        price.setVisible(show);
        quantity.setVisible(show);
    }

    /**
     * Display the page which shows the Item's information
     *
     * @param url      the image path of the item
     * @param name     the name of the item
     * @param price    the price of the item
     * @param quantity the quantity of the item
     * @param sku      the SKU of the item
     */
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

        if (Integer.parseInt(quantity) <= 10) {
            this.quantity.setStyle("-fx-graphic: url(\"/images/warning_icon.png\");"
                    + "-fx-graphic-text-gap: 10; -fx-text-fill: #ff1900;");
        } else {
            this.quantity.setStyle(null);
        }
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
        setItemVisibility(false);
    }

    /**
     * Clears the browser panel to show up as empty.
     */
    private void clearBrowserPanel() {
        setItemVisibility(false);
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
        raise(new ShowBrowserPanelEvent());
        loadItemPage(event.getNewSelection());
    }

    @Subscribe
    private void handlePurchaseOrderPanelSelectionChangedEvent(PurchaseOrderSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        raise(new ShowBrowserPanelEvent());
        loadPurchaseOrderPage(event.getNewSelection());
    }

    @Subscribe
    private void handleShowDefaultPageEvent(ShowDefaultPageEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        raise(new ShowBrowserPanelEvent());
        loadDefaultPage();
    }

    @Subscribe
    private void handleClearBrowserPanelEvent(ClearBrowserPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        raise(new ShowBrowserPanelEvent());
        clearBrowserPanel();
    }
}
