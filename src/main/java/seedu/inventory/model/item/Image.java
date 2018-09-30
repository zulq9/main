package seedu.inventory.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents a Item's inventory in the inventory book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Image {

    public static final String MESSAGE_IMAGE_CONSTRAINTS =
            "Addresses can take any values, and it should not be blank";

    /*
     * The first character of the inventory must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String IMAGE_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Image}.
     *
     * @param address A valid inventory.
     */
    public Image(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_IMAGE_CONSTRAINTS);
        value = address;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(IMAGE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Image // instanceof handles nulls
                && value.equals(((Image) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
