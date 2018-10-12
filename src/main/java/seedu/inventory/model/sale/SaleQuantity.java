package seedu.inventory.model.sale;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents a Product in the sale order.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSaleQuantity(String)}
 */
public class SaleQuantity {

    public static final String MESSAGE_QUANTITY_CONSTRAINTS = "Quantity should be numeric";
    public static final String QUANTITY_VALIDATION_REGEX = "\\p{Digit}+";

    public final String quantity;

    /**
     * Constructs a {@code SaleQuantity}.
     *
     * @param quantity A valid quantity.
     */
    public SaleQuantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidSaleQuantity(quantity), MESSAGE_QUANTITY_CONSTRAINTS);
        this.quantity = quantity;
    }

    /**
     * Returns true if a given string is a valid quantity > 0
     */
    public static boolean isValidSaleQuantity(String test) {
        boolean status = test.matches(QUANTITY_VALIDATION_REGEX);

        boolean status1;

        try {
            int result = Integer.parseInt(test);

            status1 = result != 0;
        } catch (Exception e) {
            status1 = false;
        }

        return status && status1;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaleQuantity // instanceof handles nulls
                && quantity.equals(((SaleQuantity) other).quantity)); // state check
    }

    @Override
    public int hashCode() {
        return quantity.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return quantity;
    }

}
