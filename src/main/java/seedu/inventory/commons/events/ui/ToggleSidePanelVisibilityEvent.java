package seedu.inventory.commons.events.ui;

import seedu.inventory.commons.events.BaseEvent;

/**
 * Toggles the visibility of the side list panel
 */
public class ToggleSidePanelVisibilityEvent extends BaseEvent {

    public final boolean isVisible;

    public ToggleSidePanelVisibilityEvent(boolean isVisible) {
        this.isVisible = isVisible;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
