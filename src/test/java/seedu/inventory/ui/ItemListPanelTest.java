package seedu.inventory.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.inventory.testutil.EventsUtil.postNow;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.inventory.testutil.TypicalItems.getTypicalItems;
import static seedu.inventory.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.inventory.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inventory.commons.events.ui.JumpToListRequestEvent;
import seedu.inventory.commons.util.FileUtil;
import seedu.inventory.commons.util.XmlUtil;
import seedu.inventory.model.item.Item;
import seedu.inventory.storage.XmlSerializableInventory;

public class ItemListPanelTest extends GuiUnitTest {
    private static final ObservableList<Item> TYPICAL_ITEMS =
            FXCollections.observableList(getTypicalItems());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_ITEM);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private PersonListPanelHandle personListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_ITEMS);

        for (int i = 0; i < TYPICAL_ITEMS.size(); i++) {
            personListPanelHandle.navigateToCard(TYPICAL_ITEMS.get(i));
            Item expectedItem = TYPICAL_ITEMS.get(i);
            PersonCardHandle actualCard = personListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedItem, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_ITEMS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        PersonCardHandle expectedPerson = personListPanelHandle.getPersonCardHandle(INDEX_SECOND_ITEM.getZeroBased());
        PersonCardHandle selectedPerson = personListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPerson, selectedPerson);
    }

    /**
     * Verifies that creating and deleting large number of items in {@code PersonListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Item> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of item cards exceeded time limit");
    }

    /**
     * Returns a list of items containing {@code personCount} items that is used to populate the
     * {@code PersonListPanel}.
     */
    private ObservableList<Item> createBackingList(int personCount) throws Exception {
        Path xmlFile = createXmlFileWithPersons(personCount);
        XmlSerializableInventory xmlAddressBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableInventory.class);
        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getItemList());
    }

    /**
     * Returns a .xml file containing {@code personCount} items. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithPersons(int personCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<inventory>\n");
        for (int i = 0; i < personCount; i++) {
            builder.append("<items>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<quantity>333</quantity>\n");
            builder.append("<sku>sku-" + i + "</sku>\n");
            builder.append("<image>docs/images/iphone.jpg</image>\n");
            builder.append("</items>\n");
        }
        builder.append("</inventory>\n");

        Path manyPersonsFile = Paths.get(TEST_DATA_FOLDER + "manyItems.xml");
        FileUtil.createFile(manyPersonsFile);
        FileUtil.writeToFile(manyPersonsFile, builder.toString());
        manyPersonsFile.toFile().deleteOnExit();
        return manyPersonsFile;
    }

    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<Item> backingList) {
        PersonListPanel personListPanel = new PersonListPanel(backingList);
        uiPartRule.setUiPart(personListPanel);

        personListPanelHandle = new PersonListPanelHandle(getChildNode(personListPanel.getRoot(),
                PersonListPanelHandle.PERSON_LIST_VIEW_ID));
    }
}
