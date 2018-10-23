package seedu.inventory.logic.commands.sale;

import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import org.junit.Before;
import org.junit.Test;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.StaffList;
import seedu.inventory.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListSaleCommand.
 */
public class ListSaleCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList(), new StaffList());
        expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList(), new StaffList());
    }

    @Test
    public void execute_list_showsSameList() {
        assertCommandSuccess(new ListSaleCommand(), model, commandHistory,
                ListSaleCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
