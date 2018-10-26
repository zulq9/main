package seedu.inventory.model.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.inventory.testutil.ItemBuilder;

public class SkuContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        SkuContainsKeywordsPredicate firstPredicate = new SkuContainsKeywordsPredicate(firstPredicateKeywordList);
        SkuContainsKeywordsPredicate secondPredicate = new SkuContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SkuContainsKeywordsPredicate firstPredicateCopy = new SkuContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different item -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_skuContainsKeywords_returnsTrue() {
        // One keyword
        SkuContainsKeywordsPredicate predicate = new SkuContainsKeywordsPredicate(Collections.singletonList("samsung"));
        assertTrue(predicate.test(new ItemBuilder().withSku("samsung-s8").build()));

        // Multiple keywords
        predicate = new SkuContainsKeywordsPredicate(Arrays.asList("samsung", "iphone"));
        assertTrue(predicate.test(new ItemBuilder().withSku("samsung-s8").build()));

        // Only one matching keyword
        predicate = new SkuContainsKeywordsPredicate(Arrays.asList("samsung", "iphone"));
        assertTrue(predicate.test(new ItemBuilder().withSku("iphone-xr").build()));

        // Mixed-case keywords
        predicate = new SkuContainsKeywordsPredicate(Arrays.asList("sAmsUng", "iPhonE"));
        assertTrue(predicate.test(new ItemBuilder().withSku("samsung-s8").build()));
    }

    @Test
    public void test_skuDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SkuContainsKeywordsPredicate predicate = new SkuContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ItemBuilder().withSku("iphone-xr").build()));

        // Non-matching keyword
        predicate = new SkuContainsKeywordsPredicate(Arrays.asList("oppo-test"));
        assertFalse(predicate.test(new ItemBuilder().withSku("samsung-test").build()));

        // Keywords match quantity, name and image, but does not match SKU
        predicate = new SkuContainsKeywordsPredicate(Arrays.asList("12345", "iphone-item", "/docs/images/iphone.jpg"));
        assertFalse(predicate.test(new ItemBuilder().withName("iPhone").withQuantity("12345")
                .withSku("test-item").withImage("docs/images/iphone.jpg").build()));
    }
}
