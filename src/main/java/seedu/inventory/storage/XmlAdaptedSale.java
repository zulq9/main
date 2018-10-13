package seedu.inventory.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.item.*;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleID;
import seedu.inventory.model.tag.Tag;

/**
 * JAXB-friendly version of the Sale.
 */
public class XmlAdaptedSale {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Sale's %s field is missing!";
    public static final String MISSING_ITEM = "Sale item cannot be found!";

    @XmlElement(required = true)
    private String saleID;
    @XmlElement(required = true)
    private String saleSku;
    @XmlElement(required = true)
    private String saleQuantity;
    @XmlElement(required = true)
    private String saleDate;

    private UniqueItemList inventory;

    /**
     * Constructs an XmlAdaptedSale.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSale() {}

    /**
     * Constructs an {@code XmlAdaptedSale} with the given item details.
     */
    public XmlAdaptedSale(String saleID, String saleSku, String saleQuantity, String saleDate, UniqueItemList inventory) {
        this.saleID = saleID;
        this.saleSku = saleSku;
        this.saleQuantity = saleQuantity;
        this.saleDate = saleDate;
        this.inventory = inventory;
    }

    /**
     * Converts a given Sale into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedSale
     */
    public XmlAdaptedSale(Sale source) {
        saleID = source.getSaleID().toString();
        saleSku = source.getItem().getSku().toString();
        saleQuantity = source.getSaleQuantity().toString();
        saleDate = source.getSaleDate().toString();
    }

    /**
     * Converts this jaxb-friendly adapted sale object into the model's Sale object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted sale
     */
    public Sale toModelType() throws IllegalValueException {
        if (saleID == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (!SaleID.isValidSaleID(saleID)) {
            throw new IllegalValueException(SaleID.MESSAGE_ID_CONSTRAINTS);
        }

        if (saleSku == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (!Sku.isValidSku(saleSku)) {
            throw new IllegalValueException(Sku.MESSAGE_SKU_CONSTRAINTS);
        }

        Item modelsSaleItem = inventory.getItemBySku(saleSku);

        if (modelsSaleItem == null) {
            throw new IllegalValueException(MISSING_ITEM);
        }

        final SaleID modelSaleID = new SaleID(saleID);

        if (saleQuantity == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName())
            );
        }

        if (!Quantity.isValidQuantity(saleQuantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }

        final Quantity modelQuantity = new Quantity(saleQuantity);

        if (saleDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sku.class.getSimpleName()));
        }

        if (!SaleDate.isValidSaleDate(saleDate)) {
            throw new IllegalValueException(SaleDate.MESSAGE_DATE_CONSTRAINTS);
        }

        final SaleDate modelSaleDate = new SaleDate(saleDate);

        return new Sale(modelSaleID, modelsSaleItem, modelQuantity, modelSaleDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedSale)) {
            return false;
        }

        XmlAdaptedSale otherItem = (XmlAdaptedSale) other;
        return Objects.equals(saleID, otherItem.saleID)
                && Objects.equals(saleQuantity, otherItem.saleQuantity)
                && Objects.equals(saleDate, otherItem.saleDate);
    }
}
