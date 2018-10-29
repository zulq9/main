package seedu.inventory.model.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.model.item.FilterItemPredicate;
import seedu.inventory.model.item.FilterPrice;
import seedu.inventory.model.item.FilterQuantity;
import seedu.inventory.testutil.ItemBuilder;

public class FilterItemPredicateTest {

    @Test
    public void equals() {
        FilterPrice firstPredicatePrice = new FilterPrice(">100");
        FilterItemPredicate firstPredicate = new FilterItemPredicate(new FilterPrice(">100"));
        FilterItemPredicate secondPredicate = new FilterItemPredicate(new FilterPrice(">100"),
                new FilterQuantity("<30"));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FilterItemPredicate firstPredicateCopy = new FilterItemPredicate(firstPredicatePrice);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different item -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchesCondition_returnsTrue() {
        // One condition based on price (equals)
        FilterItemPredicate predicate = new FilterItemPredicate(new FilterPrice(">1500"));
        assertTrue(predicate.test(new ItemBuilder().withPrice("1500").build()));

        // One condition based on price (more than)
        predicate = new FilterItemPredicate(new FilterPrice(">1500"));
        assertTrue(predicate.test(new ItemBuilder().withPrice("3000").build()));

        // One condition based on price (less than)
        predicate = new FilterItemPredicate(new FilterPrice("<1500"));
        assertTrue(predicate.test(new ItemBuilder().withPrice("100").build()));

        // One condition based on quantity (equals)
        predicate = new FilterItemPredicate(new FilterQuantity("<30"));
        assertTrue(predicate.test(new ItemBuilder().withQuantity("30").build()));

        // One condition based on quantity (more than)
        predicate = new FilterItemPredicate(new FilterQuantity(">30"));
        assertTrue(predicate.test(new ItemBuilder().withQuantity("300").build()));

        // One condition based on quantity (less than)
        predicate = new FilterItemPredicate(new FilterQuantity("<30"));
        assertTrue(predicate.test(new ItemBuilder().withQuantity("10").build()));

        // Multiple conditions (more than price and more than quantity)
        predicate = new FilterItemPredicate(new FilterPrice(">1500"), new FilterQuantity(">15"));
        assertTrue(predicate.test(new ItemBuilder().withPrice("2000").withQuantity("30").build()));

        // Multiple conditions (more than price and less than quantity)
        predicate = new FilterItemPredicate(new FilterPrice(">1500"), new FilterQuantity("<15"));
        assertTrue(predicate.test(new ItemBuilder().withPrice("2000").withQuantity("10").build()));

        // Multiple conditions (less than price and less than quantity)
        predicate = new FilterItemPredicate(new FilterPrice("<1500"), new FilterQuantity("<15"));
        assertTrue(predicate.test(new ItemBuilder().withPrice("300").withQuantity("10").build()));

        // Multiple conditions (less than price and more than quantity)
        predicate = new FilterItemPredicate(new FilterPrice("<1500"), new FilterQuantity(">15"));
        assertTrue(predicate.test(new ItemBuilder().withPrice("300").withQuantity("100").build()));
    }

    @Test
    public void test_conditionsDoNotMatch_returnsFalse() {
        // Non-matching condition based on price (less than)
        FilterItemPredicate predicate = new FilterItemPredicate(new FilterPrice(">1500"));
        assertFalse(predicate.test(new ItemBuilder().withPrice("1000").build()));

        // Non-matching condition based on price (more than)
        predicate = new FilterItemPredicate(new FilterPrice("<1500"));
        assertFalse(predicate.test(new ItemBuilder().withPrice("5000").build()));

        // Non-matching condition based on quantity (less than)
        predicate = new FilterItemPredicate(new FilterPrice(">1500"));
        assertFalse(predicate.test(new ItemBuilder().withPrice("1000").build()));

        // Non-matching condition based on quantity (more than)
        predicate = new FilterItemPredicate(new FilterPrice("<1500"));
        assertFalse(predicate.test(new ItemBuilder().withPrice("5000").build()));

        // Multiple non-matching conditions (more than price and more than quantity)
        predicate = new FilterItemPredicate(new FilterPrice(">1500"), new FilterQuantity(">30"));
        assertFalse(predicate.test(new ItemBuilder().withPrice("300").withQuantity("20").build()));

        // Multiple non-matching conditions (more than price and less than quantity)
        predicate = new FilterItemPredicate(new FilterPrice(">1500"), new FilterQuantity("<30"));
        assertFalse(predicate.test(new ItemBuilder().withPrice("300").withQuantity("100").build()));

        // Multiple non-matching conditions (less than price and less than quantity)
        predicate = new FilterItemPredicate(new FilterPrice("<1500"), new FilterQuantity("<30"));
        assertFalse(predicate.test(new ItemBuilder().withPrice("1800").withQuantity("100").build()));

        // Multiple non-matching conditions (less than price and more than quantity)
        predicate = new FilterItemPredicate(new FilterPrice("<1500"), new FilterQuantity(">30"));
        assertFalse(predicate.test(new ItemBuilder().withPrice("1800").withQuantity("25").build()));
    }
}
