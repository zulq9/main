package seedu.inventory.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventoryWithStaff;
import static seedu.inventory.testutil.staff.TypicalStaffs.ZUL;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.UserPrefs;

public class LogoutCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalInventoryWithStaff(), new UserPrefs(), new SaleList());
        expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList());
    }

    @Test
    public void execute_withoutAnyExistingSession_failure() throws CommandException {
        Command logoutCommand = new LogoutCommand();
        thrown.expectMessage(LogoutCommand.MESSAGE_FAILED);
        logoutCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_withExistingSession_success() throws CommandException {
        Command loginCommand = new LoginCommand(ZUL);
        loginCommand.execute(model, commandHistory);
        Command logoutCommand = new LogoutCommand();
        logoutCommand.execute(model, commandHistory);
        expectedModel.authenticateUser(ZUL);
        expectedModel.logoutUser();
        assertEquals(model.getUser(), expectedModel.getUser());
    }
}
