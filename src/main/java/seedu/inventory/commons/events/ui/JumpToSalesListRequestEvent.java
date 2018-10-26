package seedu.inventory.commons.events.ui;

import seedu.inventory.commons.core.index.Index;
import seedu.inventory.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of sales
 */
public class JumpToSalesListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToSalesListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
