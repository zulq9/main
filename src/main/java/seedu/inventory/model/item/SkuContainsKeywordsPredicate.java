package seedu.inventory.model.item;

import seedu.inventory.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Item}'s {@code SKU} matches any of the keywords given.
 */
public class SkuContainsKeywordsPredicate implements Predicate<Item> {
    private final List<String> keywords;

    public SkuContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Item item) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsAnyWordIgnoreCase(item.getSku().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SkuContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SkuContainsKeywordsPredicate) other).keywords)); // state check
    }

}
