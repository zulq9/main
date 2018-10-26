package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.inventory.model.staff.Staff;

/**
 * Provides a handle to a staff card in the staff list panel.
 */
public class StaffCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String USERNAME_FIELD_ID = "#username";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ROLE_FIELD_ID = "#role";

    private final Label idLabel;
    private final Label usernameLabel;
    private final Label nameLabel;
    private final Label roleLabel;

    public StaffCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        usernameLabel = getChildNode(USERNAME_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        roleLabel = getChildNode(ROLE_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getUsername() {
        return usernameLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getRole() {
        return roleLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code staff}.
     */
    public boolean equals(Staff staff) {
        return getUsername().equals(staff.getUsername().username)
                && getName().equals(staff.getStaffName().fullName)
                && getRole().equals(staff.getRole().toString());
    }
}
