package seedu.address.model.sale;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Product in the sale order.
 * Guarantees: immutable; name is valid as declared in {@link #isValidProductSKU(String)}
 */
public class SaleProduct {

    public static final String MESSAGE_SKU_CONSTRAINTS = "Product SKU should be alphanumeric and not empty.";
    public static final String SKU_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String productSKU;

    /**
     * Constructs a {@code SaleProduct}.
     *
     * @param productSKU A valid product SKU.
     */
    public SaleProduct(String productSKU) {
        requireNonNull(productSKU);
        checkArgument(isValidProductSKU(productSKU), MESSAGE_SKU_CONSTRAINTS);
        this.productSKU = productSKU;
    }

    /**
     * Returns true if a given string is a valid product SKU.
     */
    public static boolean isValidProductSKU(String test) {
        return test.matches(SKU_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaleProduct // instanceof handles nulls
                && productSKU.equals(((SaleProduct) other).productSKU)); // state check
    }

    @Override
    public int hashCode() {
        return productSKU.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + productSKU + ']';
    }

}
