package seedu.inventory.model.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.inventory.testutil.TypicalPersons.ALICE;
import static seedu.inventory.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.model.item.exceptions.DuplicateItemException;
import seedu.inventory.model.item.exceptions.ItemNotFoundException;
import seedu.inventory.testutil.PersonBuilder;

public class UniqueItemListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueItemList uniqueItemList = new UniqueItemList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueItemList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueItemList.add(ALICE);
        assertTrue(uniqueItemList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueItemList.add(ALICE);
        Item editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueItemList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueItemList.add(ALICE);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.add(ALICE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setPerson(null, ALICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setPerson(ALICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        uniqueItemList.setPerson(ALICE, ALICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueItemList.add(ALICE);
        uniqueItemList.setPerson(ALICE, ALICE);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(ALICE);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueItemList.add(ALICE);
        Item editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueItemList.setPerson(ALICE, editedAlice);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(editedAlice);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueItemList.add(ALICE);
        uniqueItemList.setPerson(ALICE, BOB);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(BOB);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueItemList.add(ALICE);
        uniqueItemList.add(BOB);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.setPerson(ALICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        uniqueItemList.remove(ALICE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueItemList.add(ALICE);
        uniqueItemList.remove(ALICE);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setPersons((UniqueItemList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueItemList.add(ALICE);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(BOB);
        uniqueItemList.setPersons(expectedUniqueItemList);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setPersons((List<Item>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueItemList.add(ALICE);
        List<Item> itemList = Collections.singletonList(BOB);
        uniqueItemList.setPersons(itemList);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(BOB);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Item> listWithDuplicateItems = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.setPersons(listWithDuplicateItems);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueItemList.asUnmodifiableObservableList().remove(0);
    }
}
