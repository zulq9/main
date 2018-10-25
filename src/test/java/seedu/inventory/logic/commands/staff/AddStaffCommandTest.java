package seedu.inventory.logic.commands.staff;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.testutil.ModelStub;
import seedu.inventory.testutil.staff.StaffBuilder;

public class AddStaffCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddStaffCommand(null);
    }

    @Test
    public void execute_staffAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStaffAdded modelStub = new ModelStubAcceptingStaffAdded();
        Staff validStaff = new StaffBuilder().build();

        CommandResult commandResult = new AddStaffCommand(validStaff).execute(modelStub, commandHistory);

        assertEquals(String.format(AddStaffCommand.MESSAGE_SUCCESS, validStaff), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validStaff), modelStub.staffsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateStaff_throwsCommandException() throws Exception {
        Staff validStaff = new StaffBuilder().build();
        AddStaffCommand addStaffCommand = new AddStaffCommand(validStaff);
        ModelStub modelStub = new ModelStubWithStaff(validStaff);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddStaffCommand.MESSAGE_DUPLICATE_USER);
        addStaffCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Staff zul = new StaffBuilder().withName("Zulqarnain").build();
        Staff tengxiong = new StaffBuilder().withName("Yao TengXiong").build();
        AddStaffCommand addZulCommand = new AddStaffCommand(zul);
        AddStaffCommand addTengXiongCommand = new AddStaffCommand(tengxiong);

        // same object -> returns true
        assertTrue(addZulCommand.equals(addZulCommand));

        // same values -> returns true
        AddStaffCommand addZulCommandCopy = new AddStaffCommand(zul);
        assertTrue(addZulCommand.equals(addZulCommandCopy));

        // different types -> returns false
        assertFalse(addZulCommand.equals(1));

        // null -> returns false
        assertFalse(addZulCommand.equals(null));

        // different item -> returns false
        assertFalse(addZulCommand.equals(addTengXiongCommand));
    }

    /**
     * A Model stub that contains a single staff.
     */
    private class ModelStubWithStaff extends ModelStub {
        private final Staff staff;

        ModelStubWithStaff(Staff staff) {
            requireNonNull(staff);
            this.staff = staff;
        }

        @Override
        public boolean hasStaff(Staff staff) {
            requireNonNull(staff);
            return this.staff.isSameStaff(staff);
        }
    }

    /**
     * A Model stub that always accept the staff being added.
     */
    private class ModelStubAcceptingStaffAdded extends ModelStub {
        private final ArrayList<Staff> staffsAdded = new ArrayList<>();

        @Override
        public boolean hasStaff(Staff staff) {
            requireNonNull(staff);
            return staffsAdded.stream().anyMatch(staff::isSameStaff);
        }

        @Override
        public void addStaff(Staff staff) {
            requireNonNull(staff);
            staffsAdded.add(staff);
        }

        @Override
        public void commitInventory() {
            // called by {@code AddItemCommand#execute()}
        }

        @Override
        public ReadOnlyInventory getInventory() {
            return new Inventory();
        }
    }
}
