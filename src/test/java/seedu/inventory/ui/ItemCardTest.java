package seedu.inventory.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.inventory.model.item.Item;
import seedu.inventory.testutil.PersonBuilder;

public class ItemCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Item itemWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        ItemCard itemCard = new ItemCard(itemWithNoTags, 1);
        uiPartRule.setUiPart(itemCard);
        assertCardDisplay(itemCard, itemWithNoTags, 1);

        // with tags
        Item itemWithTags = new PersonBuilder().build();
        itemCard = new ItemCard(itemWithTags, 2);
        uiPartRule.setUiPart(itemCard);
        assertCardDisplay(itemCard, itemWithTags, 2);
    }

    @Test
    public void equals() {
        Item item = new PersonBuilder().build();
        ItemCard itemCard = new ItemCard(item, 0);

        // same item, same index -> returns true
        ItemCard copy = new ItemCard(item, 0);
        assertTrue(itemCard.equals(copy));

        // same object -> returns true
        assertTrue(itemCard.equals(itemCard));

        // null -> returns false
        assertFalse(itemCard.equals(null));

        // different types -> returns false
        assertFalse(itemCard.equals(0));

        // different item, same index -> returns false
        Item differentItem = new PersonBuilder().withName("differentName").build();
        assertFalse(itemCard.equals(new ItemCard(differentItem, 0)));

        // same item, different index -> returns false
        assertFalse(itemCard.equals(new ItemCard(item, 1)));
    }

    /**
     * Asserts that {@code itemCard} displays the details of {@code expectedItem} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ItemCard itemCard, Item expectedItem, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(itemCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify item details are displayed correctly
        assertCardDisplaysPerson(expectedItem, personCardHandle);
    }
}
