package seedu.inventory.logic.commands.authentication;

import static org.junit.Assert.assertEquals;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PASSWORD_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PASSWORD_ZUL;
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
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.testutil.staff.StaffBuilder;

public class ChangePasswordCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private Staff editedZul;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalInventoryWithStaff(), new UserPrefs(), new SaleList());
        expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList());
        editedZul = new StaffBuilder(ZUL).withPassword(VALID_PASSWORD_DARREN).build();
    }

    @Test
    public void execute_withoutAnyExistingSession_failure() throws CommandException {
        ChangePasswordCommand changePasswordCommand =
                new ChangePasswordCommand(new Password(Password.hash(VALID_PASSWORD_ZUL)));
        thrown.expectMessage(ChangePasswordCommand.MESSAGE_USER_NOT_LOGGED_IN);
        changePasswordCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_withExistingSession_success() throws CommandException {
        ChangePasswordCommand changePasswordCommand =
                new ChangePasswordCommand(new Password(Password.hash(VALID_PASSWORD_DARREN)));
        model.authenticateUser(ZUL);
        changePasswordCommand.execute(model, commandHistory);
        expectedModel.authenticateUser(ZUL);
        expectedModel.updateUserSession(editedZul);
        assertEquals(expectedModel.getUser(), model.getUser());
    }

    @Test
    public void execute_withSamePassword_failure() throws CommandException {
        ChangePasswordCommand changePasswordCommand =
                new ChangePasswordCommand(new Password(Password.hash(VALID_PASSWORD_ZUL)));
        model.authenticateUser(ZUL);
        thrown.expectMessage(ChangePasswordCommand.MESSAGE_FAILED);
        changePasswordCommand.execute(model, commandHistory);
    }
}
