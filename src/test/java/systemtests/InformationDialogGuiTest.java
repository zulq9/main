package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.inventory.testutil.EventsUtil.postLater;
import static seedu.inventory.ui.UiManager.FILE_EXPORT_INFORMATION_DIALOG_CONTENT_MESSAGE;
import static seedu.inventory.ui.UiManager.FILE_IMPORT_INFORMATION_DIALOG_CONTENT_MESSAGE;
import static seedu.inventory.ui.UiManager.FILE_OPS_INFORMATION_DIALOG_HEADER_MESSAGE;
import static seedu.inventory.ui.UiManager.FILE_OPS_INFORMATION_DIALOG_STAGE_TITLE;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.AlertDialogHandle;
import seedu.inventory.commons.events.storage.DataExportingSuccessEvent;
import seedu.inventory.commons.events.storage.DataImportingSuccessEvent;

public class InformationDialogGuiTest extends InventorySystemTest {

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void showExportInformationDialogs() {
        postLater(new DataExportingSuccessEvent());

        guiRobot.waitForEvent(() -> guiRobot.isWindowShown(FILE_OPS_INFORMATION_DIALOG_STAGE_TITLE));

        AlertDialogHandle alertDialog =
                new AlertDialogHandle(guiRobot.getStage(FILE_OPS_INFORMATION_DIALOG_STAGE_TITLE));
        assertEquals(FILE_OPS_INFORMATION_DIALOG_HEADER_MESSAGE, alertDialog.getHeaderText());
        assertEquals(FILE_EXPORT_INFORMATION_DIALOG_CONTENT_MESSAGE, alertDialog.getContentText());
    }

    @Test
    public void showImportInformationDialogs() {
        postLater(new DataImportingSuccessEvent());

        guiRobot.waitForEvent(() -> guiRobot.isWindowShown(FILE_OPS_INFORMATION_DIALOG_STAGE_TITLE));

        AlertDialogHandle alertDialog =
                new AlertDialogHandle(guiRobot.getStage(FILE_OPS_INFORMATION_DIALOG_STAGE_TITLE));
        assertEquals(FILE_OPS_INFORMATION_DIALOG_HEADER_MESSAGE, alertDialog.getHeaderText());
        assertEquals(FILE_IMPORT_INFORMATION_DIALOG_CONTENT_MESSAGE, alertDialog.getContentText());
    }

}
