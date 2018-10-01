package seedu.inventory.testutil;

import static seedu.inventory.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.inventory.logic.commands.AddCommand;
import seedu.inventory.logic.commands.EditCommand.EditItemDescriptor;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.tag.Tag;

/**
 * A utility class for Item.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code item}.
     */
    public static String getAddCommand(Item item) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(item);
    }

    /**
     * Returns the part of command string for the given {@code item}'s details.
     */
    public static String getPersonDetails(Item item) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + item.getName().fullName + " ");
        sb.append(PREFIX_QUANTITY + item.getQuantity().value + " ");
        sb.append(PREFIX_SKU + item.getSku().value + " ");
        sb.append(PREFIX_IMAGE + item.getImage().value + " ");
        item.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditItemDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditItemDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getQuantity().ifPresent(phone -> sb.append(PREFIX_QUANTITY).append(phone.value).append(" "));
        descriptor.getSku().ifPresent(email -> sb.append(PREFIX_SKU).append(email.value).append(" "));
        descriptor.getImage().ifPresent(address -> sb.append(PREFIX_IMAGE).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
