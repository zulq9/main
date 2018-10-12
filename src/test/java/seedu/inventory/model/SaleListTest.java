package seedu.inventory.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_IMAGE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_SMARTPHONE;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.exceptions.DuplicateItemException;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleID;
import seedu.inventory.model.sale.exceptions.DuplicateSaleException;
import seedu.inventory.testutil.ItemBuilder;
import seedu.inventory.testutil.TypicalItems;

public class SaleListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final SaleList saleList = new SaleList();
    private static SaleID saleID = new SaleID("1");
    private static Item item = TypicalItems.IPHONE;
    private static Quantity quantity = new Quantity("1");
    private static SaleDate saleDate = new SaleDate("2018-08-01");

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), saleList.getSaleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saleList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlySale_replacesData() {
        SaleList newData = new SaleList();

        Sale sale = new Sale(saleID, item, quantity, saleDate);

        newData.addSale(sale);

        saleList.resetData(newData);
        assertEquals(newData, saleList);
    }

    @Test
    public void hasSale_nullSale_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saleList.hasSale(null);
    }

    @Test
    public void hasSale_saleNotInSaleList_returnsFalse() {
        Sale sale = new Sale(saleID, item, quantity, saleDate);

        assertFalse(saleList.hasSale(sale));
    }

    @Test
    public void hasSale_saleInSaleList_returnsTrue() {
        Sale sale = new Sale(saleID, item, quantity, saleDate);

        saleList.addSale(sale);
        assertTrue(saleList.hasSale(sale));
    }

    @Test
    public void geSaleList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        saleList.getSaleList().remove(0);
    }

}
