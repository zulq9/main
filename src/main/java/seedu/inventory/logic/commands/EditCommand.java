package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.inventory.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.commons.util.CollectionUtil;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.*;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.tag.Tag;

/**
 * Edits the details of an existing item in the inventory book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the item identified "
            + "by the index number used in the displayed item list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_QUANTITY + "PHONE] "
            + "[" + PREFIX_SKU + "EMAIL] "
            + "[" + PREFIX_IMAGE + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUANTITY + "91234567 "
            + PREFIX_SKU + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Item: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This item already exists in the inventory book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the item in the filtered item list to edit
     * @param editPersonDescriptor details to edit the item with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToEdit = lastShownList.get(index.getZeroBased());
        Item editedItem = createEditedPerson(itemToEdit, editPersonDescriptor);

        if (!itemToEdit.isSamePerson(editedItem) && model.hasPerson(editedItem)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.updatePerson(itemToEdit, editedItem);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedItem));
    }

    /**
     * Creates and returns a {@code Item} with the details of {@code itemToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Item createEditedPerson(Item itemToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert itemToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(itemToEdit.getName());
        Quantity updatedQuantity = editPersonDescriptor.getQuantity().orElse(itemToEdit.getQuantity());
        Sku updatedSku = editPersonDescriptor.getSku().orElse(itemToEdit.getSku());
        Image updatedImage = editPersonDescriptor.getImage().orElse(itemToEdit.getImage());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(itemToEdit.getTags());

        return new Item(updatedName, updatedQuantity, updatedSku, updatedImage, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the item with. Each non-empty field value will replace the
     * corresponding field value of the item.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Quantity quantity;
        private Sku sku;
        private Image image;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setQuantity(toCopy.quantity);
            setSku(toCopy.sku);
            setImage(toCopy.image);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, quantity, sku, image, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public void setSku(Sku sku) {
            this.sku = sku;
        }

        public Optional<Sku> getSku() {
            return Optional.ofNullable(sku);
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public Optional<Image> getImage() {
            return Optional.ofNullable(image);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getQuantity().equals(e.getQuantity())
                    && getSku().equals(e.getSku())
                    && getImage().equals(e.getImage())
                    && getTags().equals(e.getTags());
        }
    }
}
