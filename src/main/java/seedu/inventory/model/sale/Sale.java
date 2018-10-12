package seedu.inventory.model.sale;

import seedu.inventory.model.item.Item;

import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Sale in the Inventory Manager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Sale {

    private final Item item;
    private final SaleQuantity saleQuantity;
    private final SaleDate saleDate;

    /**
     * Every field must be present and not null.
     */
    public Sale(Item item, SaleQuantity saleQuantity, SaleDate saleDate) {
        requireAllNonNull(item, saleQuantity, saleDate);

        this.item = item;
        this.saleQuantity = saleQuantity;
        this.saleDate = saleDate;
    }

    /**
     * Returns the item.
     */
    public Item getItem() {
        return this.item;
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

    @Override
    public String toString() {
        return getSaleDate().toString() + " " + getSaleQuantity().toString() + "x " + getItem().getName().toString();
    }
}
