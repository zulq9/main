package seedu.inventory.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.commons.core.Messages.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.SAMSUNGNOTE;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.UserPrefs;
import seedu.inventory.model.filter.FilterItemPredicate;
import seedu.inventory.model.filter.FilterPrice;
import seedu.inventory.model.filter.FilterQuantity;


/**
 * Contains integration tests (interaction with the Model) for {@code FilterItemCommand}.
 */
public class FilterItemCommandTest {
    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList());
    private Model expectedModel = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList());
    private CommandHistory commandHistory = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        FilterItemPredicate firstPredicate =
                new FilterItemPredicate(new FilterPrice(">1000"), new FilterQuantity(">10"));
        FilterItemPredicate secondPredicate =
                new FilterItemPredicate(new FilterPrice("<1000"), new FilterQuantity("<10"));

        FilterItemCommand filterFirstCommand = new FilterItemCommand(firstPredicate);
        FilterItemCommand filterSecondCommand = new FilterItemCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterItemCommand filterFirstCommandCopy = new FilterItemCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different item -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_multipleKeywords_multipleItemsFiltered() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 2);
        FilterItemPredicate predicate = preparePredicate(">1500");
        FilterItemCommand command = new FilterItemCommand(predicate);
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(IPHONE, SAMSUNGNOTE), model.getFilteredItemList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private FilterItemPredicate preparePredicate(String userInput) {
        return new FilterItemPredicate(new FilterPrice(userInput));
    }
}
