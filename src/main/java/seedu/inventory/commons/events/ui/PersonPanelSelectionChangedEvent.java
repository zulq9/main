package seedu.inventory.commons.events.ui;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.item.Item;

/**
 * Represents a selection change in the Item List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final Item newSelection;

    public PersonPanelSelectionChangedEvent(Item newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Item getNewSelection() {
        return newSelection;
    }
}
