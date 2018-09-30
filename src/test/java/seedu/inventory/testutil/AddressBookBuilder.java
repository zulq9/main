package seedu.inventory.testutil;

import seedu.inventory.model.Inventory;
import seedu.inventory.model.item.Item;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code Inventory ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Inventory inventory;

    public AddressBookBuilder() {
        inventory = new Inventory();
    }

    public AddressBookBuilder(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Adds a new {@code Item} to the {@code Inventory} that we are building.
     */
    public AddressBookBuilder withPerson(Item item) {
        inventory.addPerson(item);
        return this;
    }

    public Inventory build() {
        return inventory;
    }
}
