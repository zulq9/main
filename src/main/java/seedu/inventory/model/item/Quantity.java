package seedu.inventory.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents an Item's quantity in the inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity {


    public static final String MESSAGE_QUANTITY_CONSTRAINTS =
            "Quantity should be realistic and only contain positive numbers. Decimals or other characters are not"
            + " allowed.";
    public static final String QUANTITY_VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param quantity A valid quantity.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidQuantity(quantity), MESSAGE_QUANTITY_CONSTRAINTS);
        checkArgument(isNotOverflowInteger(quantity), MESSAGE_QUANTITY_CONSTRAINTS);
        value = quantity.replaceFirst("^0+(?!$)", "");
    }

    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidQuantity(String test) {
        return test.matches(QUANTITY_VALIDATION_REGEX);
    }

    /**
     * Prevent integer overflow caused by bad users.
     */
    public static boolean isNotOverflowInteger(String test) {
        try {
            Integer value = Integer.parseInt(test);

            if (value > Integer.MAX_VALUE || value < Integer.MIN_VALUE) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quantity // instanceof handles nulls
                && value.equals(((Quantity) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
