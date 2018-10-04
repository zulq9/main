package seedu.inventory.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.NameContainsKeywordsPredicate;
import seedu.inventory.testutil.EditItemDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_OPPO = "Oppo F7 Dual SIM";
    public static final String VALID_NAME_SONY = "Sony Xperia XZ2";
    public static final String VALID_QUANTITY_OPPO = "11111111";
    public static final String VALID_QUANTITY_SONY = "22222222";
    public static final String VALID_SKU_DIFFERENT = "different-sku";
    public static final String VALID_SKU_OPPO = "oppo-f7-dual_sim";
    public static final String VALID_SKU_SONY = "sony-xperia-xz2";
    public static final String VALID_IMAGE_OPPO = "docs/images/oppo.jpg";
    public static final String VALID_IMAGE_SONY = "docs/images/sony.jpg";
    public static final String VALID_TAG_SMARTPHONE = "smartphone";
    public static final String VALID_TAG_GADGET = "gadget";

    public static final String NAME_DESC_OPPO = " " + PREFIX_NAME + VALID_NAME_OPPO;
    public static final String NAME_DESC_SONY = " " + PREFIX_NAME + VALID_NAME_SONY;
    public static final String QUANTITY_DESC_OPPO = " " + PREFIX_QUANTITY + VALID_QUANTITY_OPPO;
    public static final String QUANTITY_DESC_SONY = " " + PREFIX_QUANTITY + VALID_QUANTITY_SONY;
    public static final String SKU_DESC_OPPO = " " + PREFIX_SKU + VALID_SKU_OPPO;
    public static final String SKU_DESC_SONY = " " + PREFIX_SKU + VALID_SKU_SONY;
    public static final String SKU_DESC_DIFFERENT = " " + PREFIX_SKU + VALID_SKU_DIFFERENT;
    public static final String IMAGE_DESC_OPPO = " " + PREFIX_IMAGE + VALID_IMAGE_OPPO;
    public static final String IMAGE_DESC_SONY = " " + PREFIX_IMAGE + VALID_IMAGE_SONY;
    public static final String TAG_DESC_GADGET = " " + PREFIX_TAG + VALID_TAG_GADGET;
    public static final String TAG_DESC_SMARTPHONE = " " + PREFIX_TAG + VALID_TAG_SMARTPHONE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "iPhone&"; // '&' not allowed in names
    public static final String INVALID_QUANTITY_DESC = " " + PREFIX_QUANTITY + "911a"; // 'a' not allowed in quantities
    public static final String INVALID_SKU_DESC = " " + PREFIX_SKU + "bob!yahoo"; // '!' not allowed in SKUs
    public static final String INVALID_IMAGE_DESC = " " + PREFIX_IMAGE; // empty string not allowed for image path
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "tech*"; // '*' not allowed in tags

    public static final String VALID_NAME_ZUL = "Zulqarnain";
    public static final String VALID_USERNAME_ZUL = "zulq8";
    public static final String VALID_PASSWORD_ZUL = "meowmeowzul";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditItemDescriptor DESC_OPPO;
    public static final EditCommand.EditItemDescriptor DESC_SONY;

    static {
        DESC_OPPO = new EditItemDescriptorBuilder().withName(VALID_NAME_OPPO)
                .withQuantity(VALID_QUANTITY_OPPO).withSku(VALID_SKU_OPPO).withImage(VALID_IMAGE_OPPO)
                .withTags(VALID_TAG_GADGET).build();
        DESC_SONY = new EditItemDescriptorBuilder().withName(VALID_NAME_SONY)
                .withQuantity(VALID_QUANTITY_SONY).withSku(VALID_SKU_SONY).withImage(VALID_IMAGE_SONY)
                .withTags(VALID_TAG_SMARTPHONE, VALID_TAG_GADGET).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the inventory book and the filtered item list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Inventory expectedInventory = new Inventory(actualModel.getInventory());
        List<Item> expectedFilteredList = new ArrayList<>(actualModel.getFilteredItemList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedInventory, actualModel.getInventory());
            assertEquals(expectedFilteredList, actualModel.getFilteredItemList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the item at the given {@code targetIndex} in the
     * {@code model}'s inventory book.
     */
    public static void showItemAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredItemList().size());

        Item item = model.getFilteredItemList().get(targetIndex.getZeroBased());
        final String[] splitName = item.getName().fullName.split("\\s+");
        model.updateFilteredItemList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredItemList().size());
    }

    /**
     * Deletes the first item in {@code model}'s filtered list from {@code model}'s inventory book.
     */
    public static void deleteFirstItem(Model model) {
        Item firstItem = model.getFilteredItemList().get(0);
        model.deleteItem(firstItem);
        model.commitInventory();
    }

}
