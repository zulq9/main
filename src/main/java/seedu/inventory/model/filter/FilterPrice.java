package seedu.inventory.model.filter;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents the Price for filtering for Filter Item Command.
 * Guarantees: immutable; is valid as declared in {@link #isValidFilterPrice(String)}
 */
public class FilterPrice {


    public static final String MESSAGE_PRICE_CONSTRAINTS =
            "Filter Price should only contain numbers and/or a decimal point, and it should be at least 1 digit long. "
            + "Price can only contain a maximum of 8 digits before the decimal point, "
            + "and 2 digits after the decimal point. A prefix of '<' or '>' is required to indicate what the "
            + "condition for filtering is.";

    /*
     * The first character of the filter price must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String FILTER_PRICE_VALIDATION_REGEX = "^([><])\\d{1,8}(\\.\\d{1,2})?$";
    public final String value;

    /**
     * Constructs a {@code FiltePrice}.
     *
     * @param price A valid filter price.
     */
    public FilterPrice(String price) {
        requireNonNull(price);
        checkArgument(isValidFilterPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        value = price;
    }

    /**
     * Returns true if a given string is a valid filter price.
     */
    public static boolean isValidFilterPrice(String test) {
        return test.matches(FILTER_PRICE_VALIDATION_REGEX);
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
