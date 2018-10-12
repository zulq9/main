package seedu.inventory.model.sale;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents an ID in the sale order.
 * Guarantees: immutable; ID is valid as declared in {@link #isValidSaleID(String)}
 */
public class SaleID {

    public static final String MESSAGE_ID_CONSTRAINTS = "ID should be only numbers with at least 1 digit.";
    public static final String ID_VALIDATION_REGEX = "\\d{1,}";

    public final String saleID;

    /**
     * Constructs a {@code SaleID}.
     *
     * @param saleID A valid ID format.
     */
    public SaleID(String saleID) {
        requireNonNull(saleID);
        checkArgument(isValidSaleID(saleID), MESSAGE_ID_CONSTRAINTS);
        this.saleID = saleID;
    }

    /**
     * Returns true if a given string is a valid ID.
     */
    public static boolean isValidSaleID(String test) {
        return test.matches(ID_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaleID // instanceof handles nulls
                && saleID.equals(((SaleID) other).saleID)); // state check
    }

    @Override
    public int hashCode() {
        return saleID.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return saleID;
    }
}
