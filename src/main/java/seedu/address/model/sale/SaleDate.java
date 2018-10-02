package seedu.address.model.sale;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Date in the sale order.
 * Guarantees: immutable; date is valid as declared in {@link #isValidSaleDate(String)}
 */
public class SaleDate {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be in YYYY-mm-dd format.";
    public static final String DATE_VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    public final String saleDate;

    /**
     * Constructs a {@code SaleDate}.
     *
     * @param saleDate A valid date format.
     */
    public SaleDate(String saleDate) {
        requireNonNull(saleDate);
        checkArgument(isValidSaleDate(saleDate), MESSAGE_DATE_CONSTRAINTS);
        this.saleDate = saleDate;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidSaleDate(String test) {
        if (test.matches(DATE_VALIDATION_REGEX)) {
            String[] parts = test.split("-");

            if (parts.length < 3) {
                return false;
            }

            // Prevent years older than 2018
            if (Integer.parseInt(parts[0]) < 2018) {
                return false;
            }

            // Prevent months in invalid range
            if (Integer.parseInt(parts[1]) < 1 || Integer.parseInt(parts[1]) > 12) {
                return false;
            }

            // Prevent days in invalid range
            if (Integer.parseInt(parts[2]) < 1 || Integer.parseInt(parts[2]) > 31) {
                return false;
            }

            if (Integer.parseInt(parts[1]) == 2 || Integer.parseInt(parts[1]) == 4 || Integer.parseInt(parts[1]) == 6
                    || Integer.parseInt(parts[1]) == 9 || Integer.parseInt(parts[1]) == 11) {
                if (Integer.parseInt(parts[2]) > 30) {
                    return false;
                }

                if (Integer.parseInt(parts[2]) > 29 && Integer.parseInt(parts[1]) == 2) {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaleDate // instanceof handles nulls
                && saleDate.equals(((SaleDate) other).saleDate)); // state check
    }

    @Override
    public int hashCode() {
        return saleDate.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + saleDate + ']';
    }

}