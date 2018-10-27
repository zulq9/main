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
import seedu.inventory.model.staff.Staff;
import seedu.inventory.testutil.staff.StaffBuilder;

/**
 * Tests the login command with integration test (With LogoutCommand)
 */
public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalInventoryWithStaff(), new UserPrefs(), new SaleList());
        expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList());
    }

    @Test
    public void execute_withoutAnyExistingSession_success() throws CommandException {
        Command loginCommand = new LoginCommand(ZUL);
        loginCommand.execute(model, commandHistory);
        expectedModel.authenticateUser(ZUL);
        assertEquals(model.getUser(), expectedModel.getUser());
    }

    @Test
    public void execute_withExistingSession_failure() throws CommandException {
        Command loginCommand = new LoginCommand(ZUL);
        model.authenticateUser(ZUL);
        thrown.expectMessage(LoginCommand.MESSAGE_USER_HAS_LOGGED_IN);
        loginCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_loginWithIncorrectUsername_failure() throws CommandException {
        Staff editedZul = new StaffBuilder(ZUL).withUsername("zulq8").build();
        Command loginCommand = new LoginCommand(editedZul);
        thrown.expectMessage(LoginCommand.MESSAGE_FAILED);
        loginCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_loginWithIncorrectPassword_failure() throws CommandException {
        Staff editedZul = new StaffBuilder(ZUL).withPassword("aasdasd").build();
        Command loginCommand = new LoginCommand(editedZul);
        thrown.expectMessage(LoginCommand.MESSAGE_FAILED);
        loginCommand.execute(model, commandHistory);
    }
}
