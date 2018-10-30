package seedu.inventory.logic;

import javafx.collections.ObservableList;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.staff.Staff;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Item> getFilteredItemList();

    /** Returns an unmodifiable view of the filtered list of purchase order */
    ObservableList<PurchaseOrder> getFilteredPurchaseOrderList();

    /**
     * Returns an unmodifiable view of the filtered list of staffs
     */
    ObservableList<Staff> getFilteredStaffList();

    /** Returns an unmodifiable view of the sale orders */
    ObservableList<Sale> getObservableSaleList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();

    /**
     * Returns true if it is a public accessible command.
     * @param command the command that parsed by the parser
     * @return true if it is a public command
     */
    boolean isPublicCommand(Command command);

    /**
     * Returns true if it is a user management command.
     * @param command the command that parsed by the parser
     * @return true if it is a user management command
     */
    boolean isAdminCommand(Command command);

    /**
     * Returns true if it is a purchase order approval or reject command.
     * @param command the command that parsed by the parser
     * @return true if it is a purchase order approval or reject command
     */
    boolean isPurchaseOrderApprovalCommand(Command command);

    /**
     * Returns an error if the user has no permission accessing.
     * @param command the command that parsed by the parser
     * @throws CommandException if the user has no permission to access the command
     */
    void checkIsValidRole(Command command) throws CommandException;
}
