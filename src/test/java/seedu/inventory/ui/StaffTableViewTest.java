package seedu.inventory.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.StaffTableViewHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.testutil.staff.TypicalStaffs;

public class StaffTableViewTest extends GuiUnitTest {

    private static final ObservableList<Staff> TYPICAL_STAFF =
            FXCollections.observableList(TypicalStaffs.getTypicalStaffs());

    private StaffTableView staffTableView;
    private StaffTableViewHandle staffTableViewHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> staffTableView = new StaffTableView(TYPICAL_STAFF));
        uiPartRule.setUiPart(staffTableView);

        staffTableViewHandle = new StaffTableViewHandle(staffTableView.getRoot());
    }

    @Test
    public void display() throws Exception {
        // The list size displayed in item table view should be equal to the number of items
        assertEquals(staffTableViewHandle.getListSize(), TYPICAL_STAFF.size());

        // The list should be same
        assertEquals(staffTableViewHandle.getItemList(), TYPICAL_STAFF);

        // The header should be correct
        assertEquals("Staff Name", staffTableViewHandle.getColumnHeader(0));
        assertEquals("Username", staffTableViewHandle.getColumnHeader(1));
        assertEquals("Role", staffTableViewHandle.getColumnHeader(2));
    }
}
