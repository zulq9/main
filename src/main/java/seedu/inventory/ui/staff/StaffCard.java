package seedu.inventory.ui.staff;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.ui.UiPart;

/**
 * An UI component that displays information of a {@code Staff}.
 */
public class StaffCard extends UiPart<Region> {

    private static final String FXML = "StaffListCard.fxml";

    public final Staff staff;

    @FXML
    private HBox cardPane;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label username;

    @FXML
    private Label role;

    public StaffCard(Staff staff, int displayedIndex) {
        super(FXML);
        this.staff = staff;
        id.setText(displayedIndex + ". ");
        name.setText(staff.getStaffName().fullName);
        username.setText(staff.getUsername().username);
        role.setText(staff.getRole().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StaffCard)) {
            return false;
        }

        // state check
        StaffCard card = (StaffCard) other;
        return id.getText().equals(card.id.getText())
                && staff.equals(card.staff);
    }

}
