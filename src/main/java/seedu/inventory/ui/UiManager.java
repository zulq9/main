package seedu.inventory.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import seedu.inventory.MainApp;
import seedu.inventory.commons.core.ComponentManager;
import seedu.inventory.commons.core.Config;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.events.storage.DataExportingExceptionEvent;
import seedu.inventory.commons.events.storage.DataExportingSuccessEvent;
import seedu.inventory.commons.events.storage.DataImportingExceptionEvent;
import seedu.inventory.commons.events.storage.DataImportingSuccessEvent;
import seedu.inventory.commons.events.storage.DataSavingExceptionEvent;
import seedu.inventory.commons.util.StringUtil;
import seedu.inventory.logic.Logic;
import seedu.inventory.model.UserPrefs;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    public static final String FILE_OPS_ERROR_DIALOG_STAGE_TITLE = "File Op Error";
    public static final String FILE_OPS_ERROR_DIALOG_HEADER_MESSAGE = "Could not save data";
    public static final String FILE_OPS_ERROR_DIALOG_CONTENT_MESSAGE = "Could not save data to file";

    public static final String FILE_IMPORT_ERROR_DIALOG_HEADER_MESSAGE = "Could not import data";
    public static final String FILE_IMPORT_ERROR_DIALOG_CONTENT_MESSAGE = "Could not import data from file";

    public static final String FILE_EXPORT_ERROR_DIALOG_HEADER_MESSAGE = "Could not export data";
    public static final String FILE_EXPORT_ERROR_DIALOG_CONTENT_MESSAGE = "Could not export data to file";

    public static final String FILE_OPS_INFORMATION_DIALOG_STAGE_TITLE = "File Op Success";
    public static final String FILE_OPS_INFORMATION_DIALOG_HEADER_MESSAGE = "File Operation Success";
    public static final String FILE_EXPORT_INFORMATION_DIALOG_CONTENT_MESSAGE = "Successfully export data to file";
    public static final String FILE_IMPORT_INFORMATION_DIALOG_CONTENT_MESSAGE = "Successfully import data to file";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/inventory_manager_32.png";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
        mainWindow.releaseResources();
    }

    private void showFileOperationInformationAndWait(String description, String details) {
        showAlertDialogAndWait(AlertType.INFORMATION, FILE_OPS_INFORMATION_DIALOG_STAGE_TITLE, description, details);
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, FILE_OPS_ERROR_DIALOG_STAGE_TITLE, description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code ===============================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait(FILE_OPS_ERROR_DIALOG_HEADER_MESSAGE, FILE_OPS_ERROR_DIALOG_CONTENT_MESSAGE,
                event.exception);
    }

    @Subscribe
    private void handleDataExportingExceptionEvent(DataExportingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait(FILE_EXPORT_ERROR_DIALOG_HEADER_MESSAGE, FILE_EXPORT_ERROR_DIALOG_CONTENT_MESSAGE,
                event.exception);
    }

    @Subscribe
    private void handleDataImportingExceptionEvent(DataImportingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait(FILE_IMPORT_ERROR_DIALOG_HEADER_MESSAGE, FILE_IMPORT_ERROR_DIALOG_CONTENT_MESSAGE,
                event.exception);
    }

    @Subscribe
    private void handleDataExportingSuccessEvent(DataExportingSuccessEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationInformationAndWait(FILE_OPS_INFORMATION_DIALOG_HEADER_MESSAGE,
                FILE_EXPORT_INFORMATION_DIALOG_CONTENT_MESSAGE);
    }

    @Subscribe
    private void handleDataImportingSuccessEvent(DataImportingSuccessEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationInformationAndWait(FILE_OPS_INFORMATION_DIALOG_HEADER_MESSAGE,
                FILE_IMPORT_INFORMATION_DIALOG_CONTENT_MESSAGE);
    }
}
