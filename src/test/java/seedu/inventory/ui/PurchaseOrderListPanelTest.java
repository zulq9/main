package seedu.inventory.ui;

import static org.junit.Assert.assertEquals;
import static seedu.inventory.testutil.EventsUtil.postNow;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder.getTypicalPurchaseOrder;
import static seedu.inventory.ui.testutil.GuiTestAssert.assertCardDisplaysPurchaseOrder;
import static seedu.inventory.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Test;

import guitests.guihandles.PurchaseOrderCardHandle;
import guitests.guihandles.PurchaseOrderListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inventory.commons.events.ui.JumpToPurchaseOrderListRequestEvent;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.ui.purchaseorder.PurchaseOrderListPanel;

public class PurchaseOrderListPanelTest extends GuiUnitTest {
    private static final ObservableList<PurchaseOrder> TYPICAL_PO_LIST =
            FXCollections.observableList(getTypicalPurchaseOrder());

    private static final JumpToPurchaseOrderListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToPurchaseOrderListRequestEvent(INDEX_SECOND_ITEM);

    private PurchaseOrderListPanelHandle poListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_PO_LIST);

        for (int i = 0; i < TYPICAL_PO_LIST.size(); i++) {
            poListPanelHandle.navigateToCard(TYPICAL_PO_LIST.get(i));
            PurchaseOrder expectedPo = TYPICAL_PO_LIST.get(i);
            PurchaseOrderCardHandle actualCard = poListPanelHandle.getItemCardHandle(i);

            assertCardDisplaysPurchaseOrder(expectedPo, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_PO_LIST);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        PurchaseOrderCardHandle expectedPo = poListPanelHandle.getItemCardHandle(INDEX_SECOND_ITEM.getZeroBased());
        PurchaseOrderCardHandle selectedPo = poListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPo, selectedPo);
    }


    /**
     * Initializes {@code poListPanelHandle} with a {@code PurchaseOrderListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PurchaseOrderListPanel}.
     */
    private void initUi(ObservableList<PurchaseOrder> backingList) {
        PurchaseOrderListPanel poListPanel = new PurchaseOrderListPanel(backingList);
        uiPartRule.setUiPart(poListPanel);

        poListPanelHandle = new PurchaseOrderListPanelHandle(getChildNode(poListPanel.getRoot(),
                PurchaseOrderListPanelHandle.PO_LIST_VIEW_ID));
    }
}
