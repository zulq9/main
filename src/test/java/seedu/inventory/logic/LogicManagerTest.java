package seedu.inventory.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX;
import static seedu.inventory.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PASSWORD_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_USERNAME_ZUL;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.ExitCommand;
import seedu.inventory.logic.commands.HelpCommand;
import seedu.inventory.logic.commands.HistoryCommand;
import seedu.inventory.logic.commands.authentication.LoginCommand;
import seedu.inventory.logic.commands.authentication.LogoutCommand;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.logic.commands.item.ListItemCommand;
import seedu.inventory.logic.commands.purchaseorder.ApprovePurchaseOrderCommand;
import seedu.inventory.logic.commands.purchaseorder.RejectPurchaseOrderCommand;
import seedu.inventory.logic.commands.staff.AddStaffCommand;
import seedu.inventory.logic.commands.staff.DeleteStaffCommand;
import seedu.inventory.logic.commands.staff.EditStaffCommand;
import seedu.inventory.logic.commands.staff.ListStaffCommand;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.UserPrefs;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.testutil.staff.StaffBuilder;


public class LogicManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager();
    private Logic logic = new LogicManager(model);

    @Before
    public void setUp() {
        Staff staff = new StaffBuilder().withUsername(VALID_USERNAME_ZUL)
                .withPassword(Password.hash(VALID_PASSWORD_ZUL)).build();
        model.authenticateUser(staff);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        assertHistoryCorrect(invalidCommand);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete-item 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        assertHistoryCorrect(deleteCommand);
    }

    @Test
    public void execute_validCommand_success() {
        String listCommand = ListItemCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListItemCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listCommand);
    }

    @Test
    public void execute_invalidRole_failure() {
        String deleteStaffCommand = "delete-staff 1";
        assertCommandException(deleteStaffCommand, LogicManager.MESSAGE_NO_ACCESS);
    }

    @Test
    public void execute_logoutCommand_historyCleared() {
        String logoutCommand = LogoutCommand.COMMAND_WORD;
        assertCommandSuccess(logoutCommand, LogoutCommand.MESSAGE_SUCCESS, model);
        assertHistoryEmpty();
    }

    @Test
    public void isPublicCommand() {
        Staff staff = new StaffBuilder().build();

        assertTrue(logic.isPublicCommand(new HelpCommand()));
        assertTrue(logic.isPublicCommand(new LoginCommand(staff)));
        assertTrue(logic.isPublicCommand(new ExitCommand()));

        assertFalse(logic.isPublicCommand(new ListStaffCommand()));
        assertFalse(logic.isPublicCommand(new AddStaffCommand(staff)));
    }

    @Test
    public void isAdminCommand() {
        Staff staff = new StaffBuilder().build();

        assertFalse(logic.isAdminCommand(new HelpCommand()));
        assertFalse(logic.isAdminCommand(new LoginCommand(staff)));
        assertFalse(logic.isAdminCommand(new ExitCommand()));

        assertTrue(logic.isAdminCommand(new ListStaffCommand()));
        assertTrue(logic.isAdminCommand(new AddStaffCommand(staff)));
        assertTrue(logic.isAdminCommand(new EditStaffCommand(INDEX_FIRST_ITEM,
                new EditStaffCommand.EditStaffDescriptor())));
        assertTrue(logic.isAdminCommand(new DeleteStaffCommand(INDEX_FIRST_ITEM)));
    }

    @Test
    public void isPurchaseOrderApprovalCommand() {
        assertTrue(logic.isPurchaseOrderApprovalCommand(new ApprovePurchaseOrderCommand(INDEX_FIRST_ITEM)));
        assertTrue(logic.isPurchaseOrderApprovalCommand(new RejectPurchaseOrderCommand(INDEX_FIRST_ITEM)));

        assertFalse(logic.isPurchaseOrderApprovalCommand(new ListStaffCommand()));
        assertFalse(logic.isPurchaseOrderApprovalCommand(new ListItemCommand()));
    }

    @Test
    public void maskPassword() {
        String loginCommand = "login u/admin p/password";
        String maskedPassword = logic.maskPassword(loginCommand);
        String expectedMask = "login u/admin p/********";
        assertEquals(expectedMask, maskedPassword);
    }

    @Test
    public void getFilteredItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredItemList().remove(0);
    }

    @Test
    public void getFilteredPurchaseOrderList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredPurchaseOrderList().remove(0);
    }

    @Test
    public void getFilteredStaffList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredStaffList().remove(0);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage) {
        Model expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s inventory was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                           String expectedMessage, Model expectedModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, model);
    }

    /**
     * Asserts that the result display shows all the {@code expectedCommands} upon the execution of
     * {@code HistoryCommand}.
     */
    private void assertHistoryCorrect(String... expectedCommands) {
        try {
            CommandResult result = logic.execute(HistoryCommand.COMMAND_WORD);
            String expectedMessage = String.format(
                    HistoryCommand.MESSAGE_SUCCESS, String.join("\n", expectedCommands));
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (ParseException | CommandException e) {
            throw new AssertionError("Parsing and execution of HistoryCommand.COMMAND_WORD should succeed.", e);
        }
    }

    /**
     * Asserts that the result display shows empty history upon the execution of
     * {@code HistoryCommand}.
     */
    private void assertHistoryEmpty() {
        try {
            CommandResult result = logic.execute(HistoryCommand.COMMAND_WORD);
            String expectedMessage = String.format(
                    HistoryCommand.MESSAGE_NO_HISTORY);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (ParseException | CommandException e) {
            throw new AssertionError("Parsing and execution of HistoryCommand.COMMAND_WORD should succeed.", e);
        }
    }
}
