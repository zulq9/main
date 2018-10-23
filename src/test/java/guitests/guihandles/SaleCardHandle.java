package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

import seedu.inventory.model.sale.Sale;

/**
 * Provides a handle to a item card in the item list panel.
 */
public class SaleCardHandle extends NodeHandle<Node> {
    private static final String SALE_ID_FIELD_ID = "#saleId";
    private static final String SALE_DATE_FIELD_ID = "#saleDate";
    private static final String ITEM_FIELD_ID = "#item";
    private static final String ITEM_PRICE = "#itemPrice";

    private final Label saleIdLabel;
    private final Label saleDateLabel;
    private final Label itemLabel;
    private final Label itemPriceLabel;

    public SaleCardHandle(Node cardNode) {
        super(cardNode);

        saleIdLabel = getChildNode(SALE_ID_FIELD_ID);
        saleDateLabel = getChildNode(SALE_DATE_FIELD_ID);
        itemLabel = getChildNode(ITEM_FIELD_ID);
        itemPriceLabel = getChildNode(ITEM_PRICE);
    }

    public String getSaleId() {
        return saleIdLabel.getText();
    }

    public String getSaleDate() {
        return saleDateLabel.getText();
    }

    public String getItem() {
        return itemLabel.getText();
    }

    public String getPrice() {
        return itemPriceLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code item}.
     */
    public boolean equals(Sale sale) {
        return getSaleId().equals(sale.getSaleId().toString())
                && getSaleDate().equals(sale.getSaleDate().toString())
                && getItem().equals(sale.getItem().getQuantity() + "x " + sale.getItem().getName())
                && getPrice().equals(sale.getItem().getPrice().toString());
    }
}
