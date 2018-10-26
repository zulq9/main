package seedu.inventory.storage;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;

/**
 * Csv-friendly adapted version of the Sale.
 */
public class CsvAdaptedSale {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Sale's %s field is missing!";
    public static final String MISSING_FIELD_MESSAGE = "Sale's field is missing!";
    public static final String MISSING_ITEM = "Sale item cannot be found!";

    private final String saleId;
    private final String saleSku;
    private final String saleQuantity;
    private final String saleDate;


    /**
     * Constructs an {@code CsvAdaptedSale} with the given sale details.
     */
    public CsvAdaptedSale(String saleId, String saleSku, String saleQuantity, String saleDate) {
        this.saleId = saleId;
        this.saleSku = saleSku;
        this.saleQuantity = saleQuantity;
        this.saleDate = saleDate == null ? null : saleDate.replaceAll("/", "-");
    }


    /**
     * Converts a given sale into this class for Csv use.
     *
     * @param source future changes to this will not affect the created CsvAdaptedSale
     */
    public CsvAdaptedSale(Sale source) {
        this.saleId = source.getSaleId().toString();
        this.saleSku = source.getItem().getSku().toString();
        this.saleQuantity = source.getSaleQuantity().toString();
        this.saleDate = source.getSaleDate().toString();

    }

    /**
     * Converts this Csv-friendly adapted sale object into the model's Sale object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted sale
     */
    public Sale toModelType(ReadOnlyInventory inventory) throws IllegalValueException {
        requireNonNull(inventory);

        if (saleId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, SaleId.class.getSimpleName()));
        }

        if (!SaleId.isValidSaleId(saleId)) {
            throw new IllegalValueException(SaleId.MESSAGE_ID_CONSTRAINTS);
        }

        if (saleSku == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sku.class.getSimpleName()));
        }

        if (!Sku.isValidSku(saleSku)) {
            throw new IllegalValueException(Sku.MESSAGE_SKU_CONSTRAINTS);
        }

        Item modelSaleItem;

        modelSaleItem = inventory.getItemBySku(saleSku);

        if (modelSaleItem == null) {
            throw new IllegalValueException(MISSING_ITEM);
        }

        final SaleId modelSaleId = new SaleId(saleId);

        if (saleQuantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        }

        if (!Quantity.isValidQuantity(saleQuantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }

        final Quantity modelQuantity = new Quantity(saleQuantity);

        if (saleDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SaleDate.class.getSimpleName()));
        }

        if (!SaleDate.isValidSaleDate(saleDate)) {
            throw new IllegalValueException(SaleDate.MESSAGE_DATE_CONSTRAINTS);
        }

        final SaleDate modelSaleDate = new SaleDate(saleDate);

        return new Sale(modelSaleId, modelSaleItem, modelQuantity, modelSaleDate);
    }

    /**
     * Combine a Csv-friendly adapted sale into a list of string representing the content.
     *
     * @param sale A Csv-friendly sale
     * @return content A list of string representing the content.
     */
    public static List<String> getContentFromSale(CsvAdaptedSale sale) {
        List<String> content = new ArrayList<>();
        content.add(sale.saleId);
        content.add(sale.saleSku);
        content.add(sale.saleQuantity);
        content.add(sale.saleDate);
        return content;
    }

    /**
     * Split a list of string representing the content of sale into the Csv-friendly adapted sale
     *
     * @param content A list of string representing the content of sale
     * @return The Csv-friendly adapted sale containing the content of the list.
     */
    public static CsvAdaptedSale splitContentToSale(List<String> content) throws IllegalValueException {
        if (content.size() < 4) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE);
        }
        String saleId = content.get(0);
        String saleSku = content.get(1);
        String saleQuantity = content.get(2);
        String saleDate = content.get(3);
        return new CsvAdaptedSale(saleId, saleSku, saleQuantity, saleDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CsvAdaptedSale)) {
            return false;
        }

        CsvAdaptedSale otherSale = (CsvAdaptedSale) other;
        return Objects.equals(saleId, otherSale.saleId)
                && Objects.equals(saleDate, otherSale.saleDate)
                && Objects.equals(saleQuantity, otherSale.saleQuantity)
                && Objects.equals(saleSku, otherSale.saleSku);
    }
}
