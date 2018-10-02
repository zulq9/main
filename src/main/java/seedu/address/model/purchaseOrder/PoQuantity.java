package seedu.address.model.purchaseOrder;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the quantity of item in the purchase order to be added in the inventory upon approval.
 * Guarantees: immutable; is valid as declared in {@link #isValidPoQuantity(String)}
 */
public class PoQuantity {


    public static final String MESSAGE_QUANTITY_CONSTRAINTS =
            "Quantity should only contain numbers, and it should not start with a 0";
    public static final String QUANTITY_VALIDATION_REGEX = "^[1-9]\\d*$";
    public final String value;

    /**
     * Constructs a {@code PoQuantity}.
     *
     * @param quantity A valid quantity amount
     */
    public PoQuantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidPoQuantity(quantity), MESSAGE_QUANTITY_CONSTRAINTS);
        value = quantity;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPoQuantity(String test) {
        return test.matches(QUANTITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PoQuantity // instanceof handles nulls
                && value.equals(((PoQuantity) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
