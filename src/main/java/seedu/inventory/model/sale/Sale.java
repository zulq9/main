package seedu.inventory.model.sale;

import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;

import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Sale in the Inventory Manager.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Sale {

    private final SaleID saleID;
    private final Item item;
    private final Quantity quantity;
    private final SaleDate saleDate;

    /**
     * Every field must be present and not null.
     */
    public Sale(SaleID saleID, Item item, Quantity quantity, SaleDate saleDate) {
        requireAllNonNull(saleID, item, quantity, saleDate);

        this.saleID = saleID;
        this.item = item;
        this.quantity = quantity;
        this.saleDate = saleDate;
    }

    /**
     * Returns the sale ID.
     */
    public SaleID getSaleID() {
        return this.saleID;
    }

    /**
     * Returns the item.
     */
    public Item getItem() {
        return this.item;
    }

    /**
     * Returns the sold quantity.
     */
    public Quantity getSaleQuantity() {
        return this.quantity;
    }

    /**
     * Returns the SaleDate
     */
    public SaleDate getSaleDate() {
        return this.saleDate;
    }

    @Override
    public String toString() {
        return getSaleID() + ": " + getSaleDate().toString() + " " + getSaleQuantity().toString() + "x " + getItem().getName().toString();
    }
}
