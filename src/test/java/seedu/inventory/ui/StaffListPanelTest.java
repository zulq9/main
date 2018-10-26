package seedu.inventory.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.inventory.testutil.EventsUtil.postNow;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.inventory.testutil.staff.TypicalStaffs.getTypicalStaffs;
import static seedu.inventory.ui.testutil.GuiTestAssert.assertCardDisplaysStaff;
import static seedu.inventory.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.StaffCardHandle;
import guitests.guihandles.StaffListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inventory.commons.events.ui.JumpToStaffListRequestEvent;
import seedu.inventory.commons.util.FileUtil;
import seedu.inventory.commons.util.XmlUtil;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.storage.XmlSerializableStaffList;
import seedu.inventory.ui.staff.StaffCardListPanel;

public class StaffListPanelTest extends GuiUnitTest {
    private static final ObservableList<Staff> TYPICAL_STAFFS =
            FXCollections.observableList(getTypicalStaffs());

    private static final JumpToStaffListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToStaffListRequestEvent(INDEX_SECOND_ITEM);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private StaffListPanelHandle staffListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_STAFFS);

        for (int i = 0; i < TYPICAL_STAFFS.size(); i++) {
            staffListPanelHandle.navigateToCard(TYPICAL_STAFFS.get(i));
            Staff expectedStaff = TYPICAL_STAFFS.get(i);
            StaffCardHandle actualCard = staffListPanelHandle.getItemCardHandle(i);

            assertCardDisplaysStaff(expectedStaff, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_STAFFS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        StaffCardHandle expectedStaff = staffListPanelHandle.getItemCardHandle(INDEX_SECOND_ITEM.getZeroBased());
        StaffCardHandle selectedStaff = staffListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedStaff, selectedStaff);
    }

    /**
     * Verifies that creating and deleting large number of staffs in {@code StaffListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Staff> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of item cards exceeded time limit");
    }

    /**
     * Returns a list of staffs containing {@code staffCount} staffs that is used to populate the
     * {@code StaffListPanel}.
     */
    private ObservableList<Staff> createBackingList(int staffCount) throws Exception {
        Path xmlFile = createXmlFileWithStaffs(staffCount);
        XmlSerializableStaffList xmlStafflist =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableStaffList.class);
        return FXCollections.observableArrayList(xmlStafflist.toModelType().getStaffList());
    }

    /**
     * Returns a .xml file containing {@code staffCount} staffs. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithStaffs(int staffCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<staffList>\n");
        for (int i = 0; i < staffCount; i++) {
            builder.append("<staffs>\n");
            builder.append("<username>a" + i + "</username>\n");
            builder.append("<password>12345678</password>\n");
            builder.append("<name>yao</name>\n");
            builder.append("<role>user</role>\n");
            builder.append("</staffs>");
        }
        builder.append("</staffList>\n");

        Path mantStaffsFile = Paths.get(TEST_DATA_FOLDER + "manyStaffs.xml");
        FileUtil.createFile(mantStaffsFile);
        FileUtil.writeToFile(mantStaffsFile, builder.toString());
        mantStaffsFile.toFile().deleteOnExit();
        return mantStaffsFile;
    }

    /**
     * Initializes {@code staffListPanelHandle} with a {@code StaffListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code StaffListPanel}.
     */
    private void initUi(ObservableList<Staff> backingList) {
        StaffCardListPanel staffListPanel = new StaffCardListPanel(backingList);
        uiPartRule.setUiPart(staffListPanel);

        staffListPanelHandle = new StaffListPanelHandle(getChildNode(staffListPanel.getRoot(),
                StaffListPanelHandle.STAFF_LIST_VIEW_ID));
    }
}
