package seedu.inventory.logic.commands.item;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.FilterItemPredicate;

/**
 * Filters and lists all items in inventory which has its quantity and/or price within the range stated in
 * the argument keywords.
 */
public class FilterItemCommand extends Command {

    public static final String COMMAND_WORD = "filter-item";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters items based on quantity and/or price. Either "
            + "one of the parameters have to be present. For each parameter, use '>' for more than or equals to and "
            + "'<' for less than or equals to. "
            + "Parameters: "
            + "[" + PREFIX_PRICE + "[<][>]PRICE] "
            + "[" + PREFIX_QUANTITY + "[<][>]QUANTITY]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PRICE + ">100 "
            + PREFIX_QUANTITY + "<10";

    private final FilterItemPredicate predicate;

    public FilterItemCommand(FilterItemPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.viewItem();
        model.updateFilteredItemList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, model.getFilteredItemList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterItemCommand // instanceof handles nulls
                && predicate.equals(((FilterItemCommand) other).predicate)); // state check
    }
}
