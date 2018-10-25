package seedu.inventory.commons.events.ui;

import seedu.inventory.commons.core.index.Index;
import seedu.inventory.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of items
 */
public class JumpToItemListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToItemListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
