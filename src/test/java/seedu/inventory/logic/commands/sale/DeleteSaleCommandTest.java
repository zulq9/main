package seedu.inventory.logic.commands.sale;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.testutil.TypicalItems.IPHONE;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;
import seedu.inventory.testutil.ModelStub;

public class DeleteSaleCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteSaleCommand(null);
    }

    @Test
    public void execute_saleDeletedByModel_deleteSuccessful() throws Exception {
        DeleteSaleCommandTest.ModelStubDeleteSale modelStub = new DeleteSaleCommandTest.ModelStubDeleteSale();

        // Add sale to delete
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        Quantity saleQuantity = new Quantity("1");
        SaleDate saleDate = new SaleDate(formatter.format(date));

        Sale sale = new Sale(new SaleId("1"), IPHONE, saleQuantity, saleDate);

        modelStub.addSale(sale);

        CommandResult commandResult = new DeleteSaleCommand("1").execute(modelStub, commandHistory);

        assertEquals(String.format(DeleteSaleCommand.MESSAGE_DELETE_SALE_SUCCESS, "1"),
                commandResult.feedbackToUser);

        // Expect empty since deleted
        assertEquals(new SaleList(), modelStub.salesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_quantityAdd_successful() throws Exception {
        DeleteSaleCommandTest.ModelStubDeleteSale modelStub = new DeleteSaleCommandTest.ModelStubDeleteSale();

        Integer expectedQuantity = Integer.parseInt(IPHONE.getQuantity().toString()) + 1;

        // Add sale to delete
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        Quantity saleQuantity = new Quantity("1");
        SaleDate saleDate = new SaleDate(formatter.format(date));

        Sale sale = new Sale(new SaleId("1"), IPHONE, saleQuantity, saleDate);

        modelStub.addSale(sale);

        new DeleteSaleCommand("1").execute(modelStub, commandHistory);

        Quantity newQuantity = modelStub.getInventory().getItemBySku(IPHONE.getSku().toString()).getQuantity();

        assertTrue(expectedQuantity.equals(Integer.parseInt(newQuantity.toString())));
    }

    @Test
    public void execute_saleIdNotFound_throwsCommandException() throws Exception {
        ModelStub modelStub = new DeleteSaleCommandTest.ModelStubDeleteSale();

        DeleteSaleCommand deleteSaleCommand = new DeleteSaleCommand("123");

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(DeleteSaleCommand.MESSAGE_SALE_NOT_FOUND, "123"));
        deleteSaleCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        DeleteSaleCommand deleteSaleCommand = new DeleteSaleCommand("1");
        DeleteSaleCommand deleteSaleCommandDifferentId = new DeleteSaleCommand("2");

        // same object -> returns true
        assertTrue(deleteSaleCommand.equals(deleteSaleCommand));

        // same values -> returns true
        DeleteSaleCommand deleteSaleCommandCopy = new DeleteSaleCommand("1");
        assertTrue(deleteSaleCommand.equals(deleteSaleCommandCopy));

        // different types -> returns false
        assertFalse(deleteSaleCommand.equals(1));

        // null -> returns false
        assertFalse(deleteSaleCommand.equals(null));

        // different id -> returns false
        assertFalse(deleteSaleCommand.equals(deleteSaleCommandDifferentId));
    }

    /**
     * A Model stub that is used for testing.
     */
    private class ModelStubDeleteSale extends ModelStub {
        private final SaleList salesAdded = new SaleList();
        private final Inventory inventory = new Inventory();
        private final Item testItem = new Item(IPHONE.getName(), IPHONE.getPrice(), IPHONE.getQuantity(),
                IPHONE.getSku(), IPHONE.getImage(), IPHONE.getTags());

        public ModelStubDeleteSale() {
            inventory.addItem(testItem);
        }

        @Override
        public ReadOnlyInventory getInventory() {
            return inventory;
        }

        @Override
        public void updateItem(Item target, Item editedItem) {
            inventory.removeItem(target);
            inventory.addItem(editedItem);
        }

        @Override
        public ReadOnlySaleList getSaleList() {
            return salesAdded;
        }

        @Override
        public ObservableList<Sale> getObservableSaleList() {
            return FXCollections.unmodifiableObservableList(salesAdded.getSaleList());
        }

        @Override
        public void addSale(Sale sale) {
            requireNonNull(sale);
            salesAdded.addSale(sale);
        }

        @Override
        public void deleteSale(Sale sale) {
            salesAdded.removeSale(sale);
        }
    }
}
