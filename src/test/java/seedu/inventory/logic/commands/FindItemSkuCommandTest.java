package seedu.inventory.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.commons.core.Messages.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.testutil.TypicalItems.GOOGLE;
import static seedu.inventory.testutil.TypicalItems.ONEPLUS;
import static seedu.inventory.testutil.TypicalItems.SAMSUNGNOTE;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.UserPrefs;
import seedu.inventory.model.item.SkuContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindItemCommand}.
 */
public class FindItemSkuCommandTest {
    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList());
    private Model expectedModel = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        SkuContainsKeywordsPredicate firstPredicate =
                new SkuContainsKeywordsPredicate(Collections.singletonList("first"));
        SkuContainsKeywordsPredicate secondPredicate =
                new SkuContainsKeywordsPredicate(Collections.singletonList("second"));

        FindItemSkuCommand findFirstCommand = new FindItemSkuCommand(firstPredicate);
        FindItemSkuCommand findSecondCommand = new FindItemSkuCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindItemSkuCommand findFirstCommandCopy = new FindItemSkuCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different item -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noItemFound() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 0);
        SkuContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindItemSkuCommand command = new FindItemSkuCommand(predicate);
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredItemList());
    }

    @Test
    public void execute_multipleKeywords_multipleItemsFound() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 3);
        SkuContainsKeywordsPredicate predicate = preparePredicate("google note oneplus");
        FindItemSkuCommand command = new FindItemSkuCommand(predicate);
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GOOGLE, SAMSUNGNOTE, ONEPLUS), model.getFilteredItemList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private SkuContainsKeywordsPredicate preparePredicate(String userInput) {
        return new SkuContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
