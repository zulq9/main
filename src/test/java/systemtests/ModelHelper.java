package systemtests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.staff.Staff;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Item> PREDICATE_MATCHING_NO_ITEMS = unused -> false;
    private static final Predicate<Staff> PREDICATE_MATCHING_NO_STAFFS = unused ->false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Item> toDisplay, List<Staff> staffToDisplay) {
        Optional<Predicate<Item>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        Optional<Predicate<Staff>> staffPredicate =
                staffToDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredItemList(predicate.orElse(PREDICATE_MATCHING_NO_ITEMS));
        model.updateFilteredStaffList(staffPredicate.orElse(PREDICATE_MATCHING_NO_STAFFS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List, List)
     */
    public static void setFilteredList(Model model, Item... toDisplay) {
        ArrayList<Staff> staffList = new ArrayList<>();
        setFilteredList(model, Arrays.asList(toDisplay), staffList);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Item} equals to {@code other}.
     */
    private static Predicate<Item> getPredicateMatching(Item other) {
        return item -> item.equals(other);
    }

    private static Predicate<Staff> getPredicateMatching(Staff other) {
        return staff -> staff.equals(other);
    }
}
