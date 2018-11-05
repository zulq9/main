package seedu.inventory.storage.csv;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.sale.Sale;

/**
 * An immutable sale list that is serializable to CSV format
 */
public class CsvSerializableSaleList implements CsvSerializableData {
    public static final String DATA_TYPE = "Sale";
    public static final String[] FIELDS = {"saleId", "saleSku", "saleQuantity", "saleDate"};
    public static final String MESSAGE_DUPLICATE_SALE = "Sale list contains duplicate sale(s).";

    private ReadOnlyInventory inventory;
    private List<CsvAdaptedSale> sales;
    private List<List<String>> contents;

    /**
     * Creates an empty CsvSerializableSaleList.
     */
    public CsvSerializableSaleList() {
        sales = new ArrayList<>();
        this.contents = getContentsFromSaleList(sales);
    }

    /**
     * Creates CsvSerializableSaleList from a list of content.
     */
    public CsvSerializableSaleList(List<List<String>> contents) {
        this.contents = contents;
    }

    /**
     * Creates CsvSerializableSaleList from CsvSerializableData.
     */
    public CsvSerializableSaleList(CsvSerializableData data) {
        this.contents = data.getContents();
    }

    /**
     * Conversion
     */
    public CsvSerializableSaleList(ReadOnlySaleList src) {
        sales = src.getSaleList().stream().map(CsvAdaptedSale::new).collect(Collectors.toList());
        contents = getContentsFromSaleList(sales);
    }

    /**
     * Set the Inventory of current sale list.
     */
    public void setInventory(ReadOnlyInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Converts this sale list into the model's {@code SaleList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code CsvAdaptedSale}.
     */
    public SaleList toModelType() throws IllegalValueException {
        requireNonNull(inventory);

        sales = splitContentsToSaleList(contents);
        SaleList saleList = new SaleList();

        for (CsvAdaptedSale p : sales) {
            Sale sale = p.toModelType(inventory);
            if (saleList.hasSale(sale)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SALE);
            }
            saleList.addSale(sale);
        }
        return saleList;
    }

    /**
     * Combine a list of Csv-friendly adapted sale into a list of list of string representing the contents.
     *
     * @param sales A list of Csv-friendly sale
     * @return contents A list of list of string representing the contents.
     */
    public static List<List<String>> getContentsFromSaleList(List<CsvAdaptedSale> sales) {
        return sales.stream().map(CsvAdaptedSale::getContentFromSale).collect(Collectors.toList());
    }

    /**
     * Split a list of list of string representing the contents of sale list into a list of Csv-friendly adapted sale
     *
     * @param contents A list of list of string representing the contents of sale list
     * @return A list of Csv-friendly adapted sale containing the content of the list.
     */
    public static List<CsvAdaptedSale> splitContentsToSaleList(List<List<String>> contents)
            throws IllegalValueException {
        List<CsvAdaptedSale> sales = new ArrayList<>();
        for (List<String> content : contents) {
            sales.add(CsvAdaptedSale.splitContentToSale(content));
        }
        return sales;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CsvSerializableSaleList)) {
            return false;
        }
        return contents.equals(((CsvSerializableSaleList) other).contents);
    }

    @Override
    public List<List<String>> getContents() {
        return contents;
    }

    @Override
    public String getDataType() {
        return DATA_TYPE;
    }

    @Override
    public String[] getDataFields() {
        return FIELDS;
    }

    @Override
    public CsvSerializableData createInstance(List<List<String>> contents) {
        return new CsvSerializableSaleList(contents);
    }
}
