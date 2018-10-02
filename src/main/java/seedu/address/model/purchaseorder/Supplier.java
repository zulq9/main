package seedu.address.model.purchaseorder;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSupplier(String)}
 */
public class Supplier {

    public static final String MESSAGE_SUPPLIER_CONSTRAINTS =
            "Supplier can take any values, and it should not be blank";

    /*
     * The first character of the supplier must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String SUPPLIER_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Supplier}.
     *
     * @param supplier A valid supplier.
     */
    public Supplier(String supplier) {
        requireNonNull(supplier);
        checkArgument(isValidSupplier(supplier), MESSAGE_SUPPLIER_CONSTRAINTS);
        value = supplier;
    }

    /**
     * Returns true if a given string is a valid supplier.
     */
    public static boolean isValidSupplier(String test) {
        return test.matches(SUPPLIER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Supplier// instanceof handles nulls
                && value.equals(((Supplier) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
