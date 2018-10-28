package seedu.inventory.testutil.purchaseorder;

import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_REQDATE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SUPPLIER;

import seedu.inventory.logic.commands.purchaseorder.AddPurchaseOrderCommand;
import seedu.inventory.logic.commands.purchaseorder.EditPurchaseOrderCommand.EditPoDescriptor;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * A utility class for PurchaseOrder.
 */
public class PurchaseOrderUtil {
    /**
     * Returns an add command string for adding the {@code item}.
     */
    public static String getAddPoCommand(PurchaseOrder po) {
        return AddPurchaseOrderCommand.COMMAND_WORD + " " + getPurchaseOrderDetails(po);
    }

    /**
     * Returns the part of command string for the given {@code po}'s details.
     */
    public static String getPurchaseOrderDetails(PurchaseOrder po) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_SKU + po.getSku().value + " ");
        sb.append(PREFIX_QUANTITY + po.getQuantity().value + " ");
        sb.append(PREFIX_REQDATE + po.getReqDate().requiredDate + " ");
        sb.append(PREFIX_SUPPLIER + po.getSupplier().supplierName + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPoDescriptor}'s details.
     */
    public static String getEditPoDescriptorDetails(EditPoDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getQuantity().ifPresent(quantity -> sb.append(PREFIX_QUANTITY).append(quantity.value).append(" "));
        descriptor.getRequiredDate().ifPresent(reqDate -> sb.append(PREFIX_REQDATE).append(reqDate.requiredDate)
                .append(" "));
        descriptor.getSupplier().ifPresent(supplier -> sb.append(PREFIX_SUPPLIER).append(supplier).append(" "));

        return sb.toString();
    }
}
