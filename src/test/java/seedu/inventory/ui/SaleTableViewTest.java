package seedu.inventory.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.SaleTableViewHandle;
import javafx.collections.ObservableList;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;
import seedu.inventory.testutil.TypicalItems;

public class SaleTableViewTest extends GuiUnitTest {

    private ObservableList<Sale> typicalSales;

    private SaleTableView saleTableView;
    private SaleTableViewHandle saleTableViewHandle;

    @Before
    public void setUp() {
        SaleList saleList = new SaleList();
        SaleId saleId = new SaleId("1");
        SaleId anotherSaleId = new SaleId("2");
        Item item = TypicalItems.IPHONE;
        Quantity quantity = new Quantity("1");
        SaleDate saleDate = new SaleDate("2018-08-01");
        saleList.addSale(new Sale(saleId, item, quantity, saleDate));
        saleList.addSale(new Sale(anotherSaleId, item, quantity, saleDate));
        typicalSales = saleList.getSaleList();

        guiRobot.interact(() -> saleTableView = new SaleTableView(typicalSales));
        uiPartRule.setUiPart(saleTableView);

        saleTableViewHandle = new SaleTableViewHandle(saleTableView.getRoot());
    }

    @Test
    public void display() throws Exception {
        // The list size displayed in item table view should be equal to the number of items
        assertEquals(saleTableViewHandle.getListSize(), typicalSales.size());

        // The list should be same
        assertEquals(saleTableViewHandle.getItemList(), typicalSales);

        // The header should be correct
        assertEquals("Sale ID", saleTableViewHandle.getColumnHeader(0));
        assertEquals("Sku", saleTableViewHandle.getColumnHeader(1));
        assertEquals("Quantity", saleTableViewHandle.getColumnHeader(2));
        assertEquals("Date", saleTableViewHandle.getColumnHeader(3));
    }
}
