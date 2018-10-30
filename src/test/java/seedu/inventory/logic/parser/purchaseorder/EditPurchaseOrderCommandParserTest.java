package seedu.inventory.logic.parser.purchaseorder;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.commands.CommandTestUtil.DESC_SONY_PO;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_REQUIRED_DATE_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_SUPPLIER_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.QUANTITY_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.QUANTITY_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.REQUIRED_DATE_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.REQUIRED_DATE_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.SUPPLIER_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.SUPPLIER_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_QUANTITY_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_REQUIRED_DATE_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SUPPLIER_OPPO;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_THIRD_ITEM;

import org.junit.Test;

import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.commands.purchaseorder.EditPurchaseOrderCommand;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.purchaseorder.RequiredDate;
import seedu.inventory.model.purchaseorder.Supplier;
import seedu.inventory.testutil.purchaseorder.EditPurchaseOrderDescriptorBuilder;


public class EditPurchaseOrderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPurchaseOrderCommand.MESSAGE_USAGE);

    private EditPurchaseOrderCommandParser parser = new EditPurchaseOrderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_QUANTITY_SONY, EditPurchaseOrderCommand.MESSAGE_NOT_EDITED);

        // no field specified
        assertParseFailure(parser, "1", EditPurchaseOrderCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + QUANTITY_DESC_SONY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + QUANTITY_DESC_SONY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 u/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS); // invalid quantity
        assertParseFailure(parser, "1" + INVALID_REQUIRED_DATE_DESC,
                RequiredDate.MESSAGE_DATE_CONSTRAINTS); // invalid required date
        assertParseFailure(parser, "1" + INVALID_SUPPLIER_DESC,
                Supplier.MESSAGE_SUPPLIER_CONSTRAINTS); // invalid supplier


        // invalid quantity followed by valid date
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC + REQUIRED_DATE_DESC_SONY,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        // valid quantity followed by invalid quantity
        assertParseFailure(parser, "1" + QUANTITY_DESC_SONY + INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS);


        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC + INVALID_REQUIRED_DATE_DESC + VALID_SUPPLIER_OPPO,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // quantity
        Index targetIndex = INDEX_THIRD_ITEM;
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_SONY;
        EditPurchaseOrderCommand.EditPoDescriptor descriptor = new EditPurchaseOrderDescriptorBuilder()
                .withQuantity(VALID_QUANTITY_SONY).build();
        EditPurchaseOrderCommand expectedCommand = new EditPurchaseOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // required date
        userInput = targetIndex.getOneBased() + REQUIRED_DATE_DESC_OPPO;
        descriptor = new EditPurchaseOrderDescriptorBuilder().withRequiredDate(VALID_REQUIRED_DATE_OPPO).build();
        expectedCommand = new EditPurchaseOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // supplier
        userInput = targetIndex.getOneBased() + SUPPLIER_DESC_OPPO;
        descriptor = new EditPurchaseOrderDescriptorBuilder().withSupplier(VALID_SUPPLIER_OPPO).build();
        expectedCommand = new EditPurchaseOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_OPPO + QUANTITY_DESC_OPPO + QUANTITY_DESC_SONY;

        EditPurchaseOrderCommand.EditPoDescriptor descriptor = new EditPurchaseOrderDescriptorBuilder()
                .withQuantity(VALID_QUANTITY_SONY)
                .build();
        EditPurchaseOrderCommand expectedCommand = new EditPurchaseOrderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_SONY + REQUIRED_DATE_DESC_SONY
                + SUPPLIER_DESC_SONY;

        assertParseSuccess(parser, userInput, new EditPurchaseOrderCommand(INDEX_FIRST_ITEM, DESC_SONY_PO));
    }
}
