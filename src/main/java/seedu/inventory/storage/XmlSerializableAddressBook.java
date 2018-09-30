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
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate item(s).";

    @XmlElement
    private List<XmlAdaptedPerson> persons;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyInventory src) {
        this();
        persons.addAll(src.getItemList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code Inventory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public Inventory toModelType() throws IllegalValueException {
        Inventory inventory = new Inventory();
        for (XmlAdaptedPerson p : persons) {
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

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }
        return persons.equals(((XmlSerializableAddressBook) other).persons);
    }
}
