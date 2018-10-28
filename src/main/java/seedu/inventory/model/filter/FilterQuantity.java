package seedu.inventory.model.filter;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents the Quantity for filtering for Filter Item Command.
 * Guarantees: immutable; is valid as declared in {@link #isValidFilterQuantity(String)}
 */
public class FilterQuantity {


    public static final String MESSAGE_QUANTITY_CONSTRAINTS =
            "Filter Quantity should only contain numbers, and it should be at least 1 digit long. "
            + "Quantity can only contain a maximum of 8 digits before the decimal point, "
            + "and 2 digits after the decimal point. A prefix of '<' or '>' is required to indicate what the "
            + "condition for filtering is.";

    /*
     * The first character of the filter quantity must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String FILTER_QUANTITY_VALIDATION_REGEX = "^([><])\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code FilterQuantity}.
     *
     * @param quantity A valid filter quantity.
     */
    public FilterQuantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidFilterQuantity(quantity), MESSAGE_QUANTITY_CONSTRAINTS);
        value = quantity;
    }

    /**
     * Returns true if a given string is a valid filter quantity.
     */
    public static boolean isValidFilterQuantity(String test) {
        return test.matches(FILTER_QUANTITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
