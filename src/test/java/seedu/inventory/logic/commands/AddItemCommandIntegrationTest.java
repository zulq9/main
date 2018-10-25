package seedu.inventory.logic.commands;

import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import org.junit.Before;
import org.junit.Test;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.UserPrefs;
import seedu.inventory.model.item.Item;
import seedu.inventory.testutil.ItemBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddItemCommand}.
 */
public class AddItemCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList());
    }

    @Test
    public void execute_newItem_success() {
        Item validItem = new ItemBuilder().build();

        Model expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList());
        expectedModel.addItem(validItem);
        expectedModel.commitInventory();

        assertCommandSuccess(new AddItemCommand(validItem), model, commandHistory,
                String.format(AddItemCommand.MESSAGE_SUCCESS, validItem), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Item itemInList = model.getInventory().getItemList().get(0);
        assertCommandFailure(new AddItemCommand(itemInList), model, commandHistory,
                AddItemCommand.MESSAGE_DUPLICATE_ITEM);
    }

}
