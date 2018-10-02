package seedu.address.model.sale;

/**
 * Represents a Sale in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Sale {

    private final SaleProduct saleProduct;
    private final SaleQuantity saleQuantity;
    private final SaleDate saleDate;

    /**
     * Every field must be present and not null.
     */
    public Sale(SaleProduct saleProduct, SaleQuantity saleQuantity, SaleDate saleDate) {
        this.saleProduct = saleProduct;
        this.saleQuantity = saleQuantity;
        this.saleDate = saleDate;
    }

    /**
     * Returns the SaleProduct
     */
    public SaleProduct getSaleProduct() {
        return this.saleProduct;
    }

    /**
     * Returns the SaleQuantity
     */
    public SaleQuantity getSaleQuantity() {
        return this.saleQuantity;
    }

    /**
     * Returns the SaleDate
     */
    public SaleDate getSaleDate() {
        return this.saleDate;
    }
}
