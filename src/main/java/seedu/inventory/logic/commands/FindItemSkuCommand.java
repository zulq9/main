package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.SkuContainsKeywordsPredicate;

/**
 * Finds and lists all items in inventory whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindItemSkuCommand extends Command {

    public static final String COMMAND_WORD = "find-item-sku";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all items which SKUs contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " samsung iphone nokia";

    private final SkuContainsKeywordsPredicate predicate;

    public FindItemSkuCommand(SkuContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredItemList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, model.getFilteredItemList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindItemSkuCommand // instanceof handles nulls
                && predicate.equals(((FindItemSkuCommand) other).predicate)); // state check
    }
}
