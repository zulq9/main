package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.inventory.commons.core.Messages.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static seedu.inventory.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.inventory.testutil.TypicalItems.FILTER_CONDITIONS_MATCHING_SAMSUNG;
import static seedu.inventory.testutil.TypicalItems.GOOGLE;
import static seedu.inventory.testutil.TypicalItems.LG;
import static seedu.inventory.testutil.TypicalItems.SAMSUNG;
import static seedu.inventory.testutil.TypicalItems.SAMSUNGNOTE;

import org.junit.Test;

import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.commands.DeleteItemCommand;
import seedu.inventory.logic.commands.FilterItemCommand;
import seedu.inventory.logic.commands.RedoCommand;
import seedu.inventory.logic.commands.UndoCommand;
import seedu.inventory.model.Model;

public class FilterItemCommandSystemTest extends InventorySystemTest {

    @Test
    public void filter() {
        /* Case: filter multiple items in inventory using price, command with leading spaces and trailing spaces
         * -> 1 item found
         */
        String command = "   " + FilterItemCommand.COMMAND_WORD + " " + FILTER_CONDITIONS_MATCHING_SAMSUNG + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, SAMSUNG, SAMSUNGNOTE); // more than 1200, more than 100 quantity
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous filter command where item list is displaying the items we are filtering
         * -> 1 item found
         */
        command = FilterItemCommand.COMMAND_WORD + " " + FILTER_CONDITIONS_MATCHING_SAMSUNG;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter item where item list is not displaying the item we are finding -> 1 item found */
        command = FilterItemCommand.COMMAND_WORD + " p/>1400 q/<5";
        ModelHelper.setFilteredList(expectedModel, GOOGLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter multiple items in inventory, condition matches multiple items -> 2 items found */
        command = FilterItemCommand.COMMAND_WORD + " p/>1200 q/>100";
        ModelHelper.setFilteredList(expectedModel, SAMSUNG, SAMSUNGNOTE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter multiple items in inventory, conditions in reversed order -> 2 items found */
        command = FilterItemCommand.COMMAND_WORD + " q/>100 p/>1200";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple items in inventory, with repeated conditions -> 2 items found */
        command = FilterItemCommand.COMMAND_WORD + " q/>100 p/>1200 q/>100";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous filter command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous filter command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: filter same items in inventory after deleting 1 of them -> 1 item found */
        executeCommand(DeleteItemCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getInventory().getItemList().contains(SAMSUNGNOTE));
        command = FilterItemCommand.COMMAND_WORD + " " + FILTER_CONDITIONS_MATCHING_SAMSUNG;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, SAMSUNG);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter while a item is selected -> selected card deselected */
        showAllItems();
        selectItem(Index.fromOneBased(1));
        assertFalse(getItemListPanel().getHandleToSelectedCard().getName().equals(LG.getName().fullName));
        command = FilterItemCommand.COMMAND_WORD + " p/<1099 q/<20";
        ModelHelper.setFilteredList(expectedModel, LG);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: filter items in empty inventory -> 0 items found */
        deleteAllItems();
        command = FilterItemCommand.COMMAND_WORD + " " + FILTER_CONDITIONS_MATCHING_SAMSUNG;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, SAMSUNG);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiLtEr-iTeM p/<230";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_ITEMS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_ITEMS_LISTED_OVERVIEW, expectedModel.getFilteredItemList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see InventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
