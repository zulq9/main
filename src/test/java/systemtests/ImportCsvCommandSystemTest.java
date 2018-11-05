package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.storage.csv.CsvSerializableItemList.MESSAGE_DUPLICATE_ITEM;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.ui.UiManager.FILE_IMPORT_ERROR_DIALOG_CONTENT_MESSAGE;
import static seedu.inventory.ui.UiManager.FILE_IMPORT_ERROR_DIALOG_HEADER_MESSAGE;
import static seedu.inventory.ui.UiManager.FILE_IMPORT_INFORMATION_DIALOG_CONTENT_MESSAGE;
import static seedu.inventory.ui.UiManager.FILE_OPS_ERROR_DIALOG_STAGE_TITLE;
import static seedu.inventory.ui.UiManager.FILE_OPS_INFORMATION_DIALOG_HEADER_MESSAGE;
import static seedu.inventory.ui.UiManager.FILE_OPS_INFORMATION_DIALOG_STAGE_TITLE;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.AlertDialogHandle;
import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.logic.commands.csv.ImportCsvCommand;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Sku;

public class ImportCsvCommandSystemTest extends InventorySystemTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "ImportCsvCommandSystemTest");
    private static final Path TYPICAL_ITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalItemList.csv");
    private static final Path INVALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("invalidItemList.csv");
    private static final Path DUPLICATE_ITEM_FILE = TEST_DATA_FOLDER.resolve("duplicateItemList.csv");
    private static final Path INVALID_NAME_FILE = TEST_DATA_FOLDER.resolve("duplicateItemList.cs");

    private final GuiRobot guiRobot = new GuiRobot();

    //Because the logic of importing csv are quite same so I just test import csv items
    @Test
    public void importCsvItems() {
        /* ------------------------ Perform import csv file operations -------------------------- */

        // Case: valid input file
        String path = TYPICAL_ITEMS_FILE.toAbsolutePath().toString();
        String command = "   " + ImportCsvCommand.COMMAND_WORD_ITEMS + "  "
                + PREFIX_FILEPATH + path + "   ";

        Model expectedModel = getModel();
        expectedModel.deleteItem(IPHONE);
        assertCommandSuccessWithInformationDialog(command, expectedModel, path);

        // Case: duplicate item file
        path = DUPLICATE_ITEM_FILE.toAbsolutePath().toString();
        command = "   " + ImportCsvCommand.COMMAND_WORD_ITEMS + "  "
                + PREFIX_FILEPATH + path + "   ";
        String expectedContentMessage = FILE_IMPORT_ERROR_DIALOG_CONTENT_MESSAGE + ":\n"
                + new DataConversionException(new IllegalValueException(MESSAGE_DUPLICATE_ITEM));
        assertCommandSuccessWithErrorDialog(command, expectedContentMessage, path);

        // Case: invalid item file
        path = INVALID_ITEM_FILE.toAbsolutePath().toString();
        command = "   " + ImportCsvCommand.COMMAND_WORD_ITEMS + "  "
                + PREFIX_FILEPATH + path + "   ";
        expectedContentMessage = FILE_IMPORT_ERROR_DIALOG_CONTENT_MESSAGE + ":\n"
                + new DataConversionException(new IllegalValueException(Sku.MESSAGE_SKU_CONSTRAINTS));
        assertCommandSuccessWithErrorDialog(command, expectedContentMessage, path);

        // Case: invalid file name
        path = INVALID_NAME_FILE.toAbsolutePath().toString();
        command = "   " + ImportCsvCommand.COMMAND_WORD_ITEMS + "  "
                + PREFIX_FILEPATH + path + "   ";
        String expectedResultMessage = String.format(ImportCsvCommand.MESSAGE_INVALID_CSV_FILEPATH, path);
        assertCommandFailure(command, expectedResultMessage);

        // Case: invalid prefix name
        path = INVALID_NAME_FILE.toAbsolutePath().toString();
        command = "   " + ImportCsvCommand.COMMAND_WORD_ITEMS + "  "
                + PREFIX_NAME + path + "   ";
        expectedResultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCsvCommand.MESSAGE_USAGE);
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
    private void assertCommandSuccessWithInformationDialog(String command, Model expectedModel, String path) {

        String expectedResultMessage = String.format(ImportCsvCommand.MESSAGE_IMPORT);

        executeCommand(command);

        guiRobot.waitForEvent(() -> guiRobot.isWindowShown(FILE_OPS_INFORMATION_DIALOG_STAGE_TITLE));

        AlertDialogHandle alertDialog =
                new AlertDialogHandle(guiRobot.getStage(FILE_OPS_INFORMATION_DIALOG_STAGE_TITLE));
        assertEquals(FILE_OPS_INFORMATION_DIALOG_HEADER_MESSAGE, alertDialog.getHeaderText());
        assertEquals(FILE_IMPORT_INFORMATION_DIALOG_CONTENT_MESSAGE, alertDialog.getContentText());

        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus(false);

        alertDialog.close();

        expectedResultMessage = String.format(ImportCsvCommand.MESSAGE_SUCCESS_ITEMS, path);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
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
    private void assertCommandSuccessWithErrorDialog(String command, String expectedContentMessage, String path) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(ImportCsvCommand.MESSAGE_IMPORT);

        executeCommand(command);

        guiRobot.waitForEvent(() -> guiRobot.isWindowShown(FILE_OPS_ERROR_DIALOG_STAGE_TITLE));
        AlertDialogHandle alertDialog =
                new AlertDialogHandle(guiRobot.getStage(FILE_OPS_ERROR_DIALOG_STAGE_TITLE));
        assertEquals(FILE_IMPORT_ERROR_DIALOG_HEADER_MESSAGE, alertDialog.getHeaderText());
        assertEquals(expectedContentMessage, alertDialog.getContentText());

        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();

        alertDialog.close();

        expectedResultMessage = String.format(ImportCsvCommand.MESSAGE_SUCCESS_ITEMS, path);
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
