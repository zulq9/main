package seedu.inventory.logic.commands.staff;

import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventoryWithStaff;

import org.junit.Before;
import org.junit.Test;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListStaffCommand.
 */
public class ListStaffCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalInventoryWithStaff(), new UserPrefs(), new SaleList());
        expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList());
    }

    @Test
    public void execute_listStaff_showsSameList() {
        assertCommandSuccess(new ListStaffCommand(), model, commandHistory,
                ListStaffCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
