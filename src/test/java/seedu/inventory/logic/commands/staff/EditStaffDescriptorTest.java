package seedu.inventory.logic.commands.staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.DESC_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.DESC_ZUL;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PASSWORD_DARREN;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_USERNAME_DARREN;

import org.junit.Test;

import seedu.inventory.logic.commands.staff.EditStaffCommand.EditStaffDescriptor;
import seedu.inventory.testutil.staff.EditStaffDescriptorBuilder;

public class EditStaffDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditStaffDescriptor descriptorWithSameValues = new EditStaffDescriptor(DESC_ZUL);
        assertTrue(DESC_ZUL.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ZUL.equals(DESC_ZUL));

        // null -> returns false
        assertFalse(DESC_ZUL.equals(null));

        // different types -> returns false
        assertFalse(DESC_ZUL.equals(5));

        // different values -> returns false
        assertFalse(DESC_ZUL.equals(DESC_DARREN));

        // different name -> returns false
        EditStaffDescriptor editedZul =
                new EditStaffDescriptorBuilder(DESC_ZUL).withName(VALID_NAME_DARREN).build();
        assertFalse(DESC_ZUL.equals(editedZul));

        // different role -> returns false
        editedZul = new EditStaffDescriptorBuilder(DESC_ZUL).withRole("manager").build();
        assertFalse(DESC_ZUL.equals(editedZul));

        // different username -> returns false
        editedZul = new EditStaffDescriptorBuilder(DESC_ZUL).withUsername(VALID_USERNAME_DARREN).build();
        assertFalse(DESC_ZUL.equals(editedZul));

        // different image -> returns false
        editedZul = new EditStaffDescriptorBuilder(DESC_ZUL).withPassword(VALID_PASSWORD_DARREN).build();
        assertFalse(DESC_ZUL.equals(editedZul));
    }
}
