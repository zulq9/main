package seedu.inventory.logic.commands.item;

import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.logic.commands.CommandTestUtil.showItemAtIndex;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import org.junit.Before;
import org.junit.Test;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListItemCommand.
 */
public class ListItemCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList());
        expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListItemCommand(), model, commandHistory,
                ListItemCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);
        assertCommandSuccess(new ListItemCommand(), model, commandHistory,
                ListItemCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
