package seedu.inventory.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.inventory.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.inventory.model.item.*;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code item}'s details
     */
    public EditPersonDescriptorBuilder(Item item) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(item.getName());
        descriptor.setQuantity(item.getQuantity());
        descriptor.setSku(item.getSku());
        descriptor.setImage(item.getImage());
        descriptor.setTags(item.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setQuantity(new Quantity(phone));
        return this;
    }

    /**
     * Sets the {@code Sku} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setSku(new Sku(email));
        return this;
    }

    /**
     * Sets the {@code Image} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setImage(new Image(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
