package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.ui.UiManager.FILE_EXPORT_INFORMATION_DIALOG_CONTENT_MESSAGE;
import static seedu.inventory.ui.UiManager.FILE_OPS_INFORMATION_DIALOG_HEADER_MESSAGE;
import static seedu.inventory.ui.UiManager.FILE_OPS_INFORMATION_DIALOG_STAGE_TITLE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import guitests.GuiRobot;
import guitests.guihandles.AlertDialogHandle;
import seedu.inventory.logic.commands.csv.ExportCsvCommand;
import seedu.inventory.model.Model;

public class ExportCsvCommandSystemTest extends InventorySystemTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private final GuiRobot guiRobot = new GuiRobot();

    //Because the logic of exporting csv are quite same so I just test export csv items
    @Test
    public void exportCsvItems() {
        /* ------------------------ Perform export csv file operations -------------------------- */

        // Case: valid output file
        String path = testFolder.getRoot().toPath().resolve("tempItemList.csv").toAbsolutePath().toString();
        String command = "   " + ExportCsvCommand.COMMAND_WORD_ITEMS + "  "
                + PREFIX_FILEPATH + path + "   ";

        assertCommandSuccessWithInformationDialog(command, path);

        // Case: invalid file name
        path = testFolder.getRoot().toPath().resolve("tempItemList.cs").toAbsolutePath().toString();
        command = "   " + ExportCsvCommand.COMMAND_WORD_ITEMS + "  "
                + PREFIX_FILEPATH + path + "   ";
        String expectedResultMessage = String.format(ExportCsvCommand.MESSAGE_INVALID_CSV_FILEPATH, path);
        assertCommandFailure(command, expectedResultMessage);

        // Case: invalid prefix name
        path = testFolder.getRoot().toPath().resolve("tempItemList.csv").toAbsolutePath().toString();
        command = "   " + ExportCsvCommand.COMMAND_WORD_ITEMS + "  "
                + PREFIX_NAME + path + "   ";
        expectedResultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCsvCommand.MESSAGE_USAGE);
        assertCommandFailure(command, expectedResultMessage);

    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays the input.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the internal message of executing the command.<br>
     * 4. {@code Storage} and {@code ItemListPanel} is correctly updated
     * 5. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccessWithInformationDialog(String command, String path) {

        Model expectedModel = getModel();

        String expectedResultMessage = String.format(ExportCsvCommand.MESSAGE_EXPORT);

        executeCommand(command);

        guiRobot.waitForEvent(() -> guiRobot.isWindowShown(FILE_OPS_INFORMATION_DIALOG_STAGE_TITLE));

        AlertDialogHandle alertDialog =
                new AlertDialogHandle(guiRobot.getStage(FILE_OPS_INFORMATION_DIALOG_STAGE_TITLE));
        assertEquals(FILE_OPS_INFORMATION_DIALOG_HEADER_MESSAGE, alertDialog.getHeaderText());
        assertEquals(FILE_EXPORT_INFORMATION_DIALOG_CONTENT_MESSAGE, alertDialog.getContentText());

        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();

        alertDialog.close();

        expectedResultMessage = String.format(ExportCsvCommand.MESSAGE_SUCCESS_ITEMS, path);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. Browser url, status bar remain unchanged.<br>
     * Verifications 1, are performed by
     * {@code InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
