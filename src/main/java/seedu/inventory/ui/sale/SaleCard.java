package seedu.inventory.ui.sale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.ui.UiPart;

/**
 * An UI component that displays information of a {@code Item}.
 */
public class SaleCard extends UiPart<Region> {

    private static final String FXML = "SaleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Sale sale;

    @FXML
    private HBox cardPane;
    @FXML
    private Label saleId;
    @FXML
    private Label saleDate;
    @FXML
    private Label item;
    @FXML
    private Label itemPrice;

    public SaleCard(Sale sale) {
        super(FXML);
        this.sale = sale;
        saleId.setText(sale.getSaleId() + ". ");
        saleDate.setText(sale.getSaleDate().toString());
        item.setText(sale.getSaleQuantity().toString() + "x " + sale.getItem().getName().toString());
        itemPrice.setText("$" + sale.getItem().getPrice().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SaleCard)) {
            return false;
        }

        // state check
        SaleCard card = (SaleCard) other;
        return saleId.getText().equals(card.saleId.getText())
                && sale.equals(card.sale);
    }
}
