package seedu.address.model.purchaseOrder;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the required date of item to be approved in the purchase order.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class RequiredDate {


    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should be in the format of 'dd/mm/2yyy' with required leading zeros for dd and mm.";
    public static final String DATE_VALIDATION_REGEX = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/2\\d\\d\\d";
    public final String value;

    /**
     * Constructs a {@code RequiredDate}.
     *
     * @param date A valid date
     */
    public RequiredDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), DATE_VALIDATION_REGEX);
        value = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RequiredDate // instanceof handles nulls
                && value.equals(((RequiredDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
