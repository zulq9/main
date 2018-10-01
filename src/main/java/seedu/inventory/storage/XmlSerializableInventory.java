package seedu.inventory.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.item.Item;

/**
 * An Immutable Inventory that is serializable to XML format
 */
@XmlRootElement(name = "inventory")
public class XmlSerializableInventory {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate item(s).";

    @XmlElement
    private List<XmlAdaptedItem> persons;

    /**
     * Creates an empty XmlSerializableInventory.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableInventory() {
        persons = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableInventory(ReadOnlyInventory src) {
        this();
        persons.addAll(src.getItemList().stream().map(XmlAdaptedItem::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code Inventory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedItem}.
     */
    public Inventory toModelType() throws IllegalValueException {
        Inventory inventory = new Inventory();
        for (XmlAdaptedItem p : persons) {
            Item item = p.toModelType();
            if (inventory.hasPerson(item)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            inventory.addPerson(item);
        }
        return inventory;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableInventory)) {
            return false;
        }
        return persons.equals(((XmlSerializableInventory) other).persons);
    }
}
