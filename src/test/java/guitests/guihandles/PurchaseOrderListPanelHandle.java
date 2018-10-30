package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * Provides a handle for {@code PurchaseOrderListPanel} containing the list of {@code PurchaseOrderCard}.
 */
public class PurchaseOrderListPanelHandle extends NodeHandle<ListView<PurchaseOrder>> {

    public static final String PO_LIST_VIEW_ID = "#purchaseOrderListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<PurchaseOrder> lastRememberedSelectedPoCard;

    public PurchaseOrderListPanelHandle(ListView<PurchaseOrder> poListPanelNode) {
        super(poListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PurchaseOrderCardHandle}.
     * A maximum of 1 purchase order can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public PurchaseOrderCardHandle getHandleToSelectedCard() {
        List<PurchaseOrder> selectedPoList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedPoList.size() != 1) {
            throw new AssertionError("Purchase Order list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(PurchaseOrderCardHandle::new)
                .filter(handle -> handle.equals(selectedPoList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<PurchaseOrder> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code po}.
     */
    public void navigateToCard(PurchaseOrder po) {
        if (!getRootNode().getItems().contains(po)) {
            throw new IllegalArgumentException("Purchase order does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(po);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code PurchaseOrderCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the purchase order card handle of a purchase order associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public PurchaseOrderCardHandle getItemCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(PurchaseOrderCardHandle::new)
                .filter(handle -> handle.equals(getPurchaseOrder(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private PurchaseOrder getPurchaseOrder(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code PurchaseOrderCard} in the list.
     */
    public void rememberSelectedPoCard() {
        List<PurchaseOrder> selectedPos = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedPos.size() == 0) {
            lastRememberedSelectedPoCard = Optional.empty();
        } else {
            lastRememberedSelectedPoCard = Optional.of(selectedPos.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PurchaseOrderCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPoCard()} call.
     */
    public boolean isSelectedPoCardChanged() {
        List<PurchaseOrder> selectedPos = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedPos.size() == 0) {
            return lastRememberedSelectedPoCard.isPresent();
        } else {
            return !lastRememberedSelectedPoCard.isPresent()
                    || !lastRememberedSelectedPoCard.get().equals(selectedPos.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
