package seedu.inventory.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.inventory.testutil.TypicalItems.AMY;
import static seedu.inventory.testutil.TypicalItems.BOB;
import static seedu.inventory.testutil.TypicalItems.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.inventory.testutil.AddressBookBuilder;

public class VersionedInventoryTest {

    private final ReadOnlyInventory addressBookWithAmy = new AddressBookBuilder().withPerson(AMY).build();
    private final ReadOnlyInventory addressBookWithBob = new AddressBookBuilder().withPerson(BOB).build();
    private final ReadOnlyInventory addressBookWithCarl = new AddressBookBuilder().withPerson(CARL).build();
    private final ReadOnlyInventory emptyAddressBook = new AddressBookBuilder().build();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedInventory versionedInventory = prepareAddressBookList(emptyAddressBook);

        versionedInventory.commit();
        assertAddressBookListStatus(versionedInventory,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedInventory versionedInventory = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        versionedInventory.commit();
        assertAddressBookListStatus(versionedInventory,
                Arrays.asList(emptyAddressBook, addressBookWithAmy, addressBookWithBob),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedInventory versionedInventory = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 2);

        versionedInventory.commit();
        assertAddressBookListStatus(versionedInventory,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtEndOfStateList_returnsTrue() {
        VersionedInventory versionedInventory = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertTrue(versionedInventory.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedInventory versionedInventory = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 1);

        assertTrue(versionedInventory.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedInventory versionedInventory = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedInventory.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsFalse() {
        VersionedInventory versionedInventory = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 2);

        assertFalse(versionedInventory.canUndo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedInventory versionedInventory = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 1);

        assertTrue(versionedInventory.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedInventory versionedInventory = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 2);

        assertTrue(versionedInventory.canRedo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedInventory versionedInventory = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedInventory.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtEndOfStateList_returnsFalse() {
        VersionedInventory versionedInventory = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertFalse(versionedInventory.canRedo());
    }

    @Test
    public void undo_multipleAddressBookPointerAtEndOfStateList_success() {
        VersionedInventory versionedInventory = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        versionedInventory.undo();
        assertAddressBookListStatus(versionedInventory,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void undo_multipleAddressBookPointerNotAtStartOfStateList_success() {
        VersionedInventory versionedInventory = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 1);

        versionedInventory.undo();
        assertAddressBookListStatus(versionedInventory,
                Collections.emptyList(),
                emptyAddressBook,
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedInventory versionedInventory = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedInventory.NoUndoableStateException.class, versionedInventory::undo);
    }

    @Test
    public void undo_multipleAddressBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedInventory versionedInventory = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 2);

        assertThrows(VersionedInventory.NoUndoableStateException.class, versionedInventory::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        VersionedInventory versionedInventory = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 1);

        versionedInventory.redo();
        assertAddressBookListStatus(versionedInventory,
                Arrays.asList(emptyAddressBook, addressBookWithAmy),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBookPointerAtStartOfStateList_success() {
        VersionedInventory versionedInventory = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 2);

        versionedInventory.redo();
        assertAddressBookListStatus(versionedInventory,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedInventory versionedInventory = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedInventory.NoRedoableStateException.class, versionedInventory::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedInventory versionedInventory = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertThrows(VersionedInventory.NoRedoableStateException.class, versionedInventory::redo);
    }

    @Test
    public void equals() {
        VersionedInventory versionedInventory = prepareAddressBookList(addressBookWithAmy, addressBookWithBob);

        // same values -> returns true
        VersionedInventory copy = prepareAddressBookList(addressBookWithAmy, addressBookWithBob);
        assertTrue(versionedInventory.equals(copy));

        // same object -> returns true
        assertTrue(versionedInventory.equals(versionedInventory));

        // null -> returns false
        assertFalse(versionedInventory.equals(null));

        // different types -> returns false
        assertFalse(versionedInventory.equals(1));

        // different state list -> returns false
        VersionedInventory differentAddressBookList = prepareAddressBookList(addressBookWithBob, addressBookWithCarl);
        assertFalse(versionedInventory.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedInventory differentCurrentStatePointer = prepareAddressBookList(
                addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 1);
        assertFalse(versionedInventory.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedInventory} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedInventory#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedInventory#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedInventory versionedInventory,
                                             List<ReadOnlyInventory> expectedStatesBeforePointer,
                                             ReadOnlyInventory expectedCurrentState,
                                             List<ReadOnlyInventory> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new Inventory(versionedInventory), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedInventory.canUndo()) {
            versionedInventory.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyInventory expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new Inventory(versionedInventory));
            versionedInventory.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyInventory expectedAddressBook : expectedStatesAfterPointer) {
            versionedInventory.redo();
            assertEquals(expectedAddressBook, new Inventory(versionedInventory));
        }

        // check that there are no more states after pointer
        assertFalse(versionedInventory.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedInventory.undo());
    }

    /**
     * Creates and returns a {@code VersionedInventory} with the {@code addressBookStates} added into it, and the
     * {@code VersionedInventory#currentStatePointer} at the end of list.
     */
    private VersionedInventory prepareAddressBookList(ReadOnlyInventory... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedInventory versionedInventory = new VersionedInventory(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedInventory.resetData(addressBookStates[i]);
            versionedInventory.commit();
        }

        return versionedInventory;
    }

    /**
     * Shifts the {@code versionedInventory#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedInventory versionedInventory, int count) {
        for (int i = 0; i < count; i++) {
            versionedInventory.undo();
        }
    }
}
