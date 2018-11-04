package seedu.inventory.logic.commands.sale;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.SONY;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;
import seedu.inventory.testutil.ModelStub;
import seedu.inventory.testutil.TypicalItems;

public class AddSaleCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddSaleCommand(null, null);
    }

    @Test
    public void execute_saleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubCreateSale modelStub = new ModelStubCreateSale();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        Sku sku = IPHONE.getSku();
        Quantity saleQuantity = new Quantity("1");
        SaleDate saleDate = new SaleDate(formatter.format(date));

        Sale sale = new Sale(new SaleId("1"), IPHONE, saleQuantity, saleDate);

        CommandResult commandResult = new AddSaleCommand(sku, saleQuantity).execute(modelStub, commandHistory);

        assertEquals(String.format(AddSaleCommand.MESSAGE_SUCCESS, "1", IPHONE.getName().toString()),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(sale), modelStub.salesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_quantityDeduct_successful() throws Exception {
        ModelStubCreateSaleQuantityTest modelStub = new ModelStubCreateSaleQuantityTest();

        Sku sku = IPHONE.getSku();
        Quantity saleQuantity = new Quantity("1");

        Integer expectedQuantity = Integer.parseInt(IPHONE.getQuantity().toString()) - 1;

        new AddSaleCommand(sku, saleQuantity).execute(modelStub, commandHistory);

        Quantity newQuantity = modelStub.getInventory().getItemBySku(IPHONE.getSku().toString()).getQuantity();

        assertTrue(expectedQuantity.equals(Integer.parseInt(newQuantity.toString())));
    }

    @Test
    public void execute_itemNotFound_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubCreateSale();

        AddSaleCommand addSaleCommand = new AddSaleCommand(new Sku("abc"), new Quantity("1"));

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(AddSaleCommand.MESSAGE_ITEM_NOT_FOUND, "abc"));
        addSaleCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_quantityInsufficient_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubCreateSale();

        AddSaleCommand addSaleCommand = new AddSaleCommand(IPHONE.getSku(), new Quantity("1000"));

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(AddSaleCommand.MESSAGE_QUANTITY_INSUFFICIENT, IPHONE.getQuantity()));
        addSaleCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        AddSaleCommand addSaleCommand = new AddSaleCommand(IPHONE.getSku(), new Quantity("1"));
        AddSaleCommand addSaleCommandDifferentSku = new AddSaleCommand(SONY.getSku(), new Quantity("2"));
        AddSaleCommand addSaleCommandDifferentQuantity = new AddSaleCommand(IPHONE.getSku(),
                new Quantity("2"));

        // same object -> returns true
        assertTrue(addSaleCommand.equals(addSaleCommand));

        // same values -> returns true
        AddSaleCommand addSaleCommandCopy = new AddSaleCommand(IPHONE.getSku(), new Quantity("1"));
        assertTrue(addSaleCommand.equals(addSaleCommandCopy));

        // different types -> returns false
        assertFalse(addSaleCommand.equals(1));

        // null -> returns false
        assertFalse(addSaleCommand.equals(null));

        // different sku -> returns false
        assertFalse(addSaleCommand.equals(addSaleCommandDifferentSku));

        // different quantity -> returns false
        assertFalse(addSaleCommand.equals(addSaleCommandDifferentQuantity));
    }

    /**
     * A Model stub that is used for testing.
     */
    private class ModelStubCreateSale extends ModelStub {
        private final ArrayList<Sale> salesAdded = new ArrayList<>();

        @Override
        public ReadOnlyInventory getInventory() {
            return TypicalItems.getTypicalInventory();
        }

        @Override
        public void updateItem(Item target, Item editedItem) {
        }

        @Override
        public ReadOnlySaleList getSaleList() {
            return new SaleList();
        }

        @Override
        public void addSale(Sale sale) {
            requireNonNull(sale);
            salesAdded.add(sale);
        }

        @Override
        public void commitInventory() {
            assert true;
        }
    }

    /**
     * A Model stub that is used for testing adding quantity.
     */
    private class ModelStubCreateSaleQuantityTest extends ModelStub {
        private final ArrayList<Sale> salesAdded = new ArrayList<>();
        private final Item testItem = new Item(IPHONE.getName(), IPHONE.getPrice(), IPHONE.getQuantity(),
                IPHONE.getSku(), IPHONE.getImage(), IPHONE.getTags());
        private final Inventory inventory = new Inventory();

        public ModelStubCreateSaleQuantityTest() {
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
            return new SaleList();
        }

        @Override
        public void addSale(Sale sale) {
            requireNonNull(sale);
            salesAdded.add(sale);
        }

        @Override
        public void commitInventory() {
            assert true;
        }
    }

}
