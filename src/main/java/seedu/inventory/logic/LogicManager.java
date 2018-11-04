package seedu.inventory.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.inventory.commons.core.ComponentManager;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.logic.commands.ClearCommand;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.ExitCommand;
import seedu.inventory.logic.commands.ExportCsvCommand;
import seedu.inventory.logic.commands.HelpCommand;
import seedu.inventory.logic.commands.HistoryCommand;
import seedu.inventory.logic.commands.ImportCsvCommand;
import seedu.inventory.logic.commands.authentication.LoginCommand;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.logic.commands.purchaseorder.ApprovePurchaseOrderCommand;
import seedu.inventory.logic.commands.purchaseorder.RejectPurchaseOrderCommand;
import seedu.inventory.logic.commands.staff.AddStaffCommand;
import seedu.inventory.logic.commands.staff.DeleteStaffCommand;
import seedu.inventory.logic.commands.staff.EditStaffCommand;
import seedu.inventory.logic.commands.staff.ListStaffCommand;
import seedu.inventory.logic.parser.InventoryParser;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.staff.Staff;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {

    public static final String MESSAGE_NO_ACCESS = "You have no permission to use this command.";
    public static final String MESSAGE_NOT_LOGGED_IN = "You have not logged in.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final InventoryParser inventoryParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        inventoryParser = new InventoryParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        String commandLog = maskPassword(commandText);
        logger.info("----------------[USER COMMAND][" + commandLog + "]");
        try {
            Command command = inventoryParser.parseCommand(commandText);

            if (!model.isUserLoggedIn() && !isPublicCommand(command)) {
                throw new CommandException(MESSAGE_NOT_LOGGED_IN);
            }
            checkIsValidRole(command);
            return command.execute(model, history);
        } finally {
            if (commandText.contains("logout")) {
                history.clear();
            } else {
                history.add(commandText);
            }
        }
    }

    @Override
    public ObservableList<Item> getFilteredItemList() {
        return model.getFilteredItemList();
    }

    @Override
    public ObservableList<PurchaseOrder> getFilteredPurchaseOrderList() {
        return model.getFilteredPurchaseOrderList();
    }

    @Override
    public ObservableList<Staff> getFilteredStaffList() {
        return model.getFilteredStaffList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    @Override
    public ObservableList<Sale> getObservableSaleList() {
        return model.getObservableSaleList();
    }

    @Override
    public boolean isPublicCommand(Command command) {
        return command instanceof HelpCommand || command instanceof LoginCommand || command instanceof ExitCommand
                || command instanceof HistoryCommand;
    }

    @Override
    public boolean isAdminCommand(Command command) {
        return command instanceof AddStaffCommand || command instanceof EditStaffCommand
                || command instanceof ListStaffCommand || command instanceof DeleteStaffCommand
                || command instanceof ImportCsvCommand || command instanceof ExportCsvCommand
                || command instanceof ClearCommand;
    }

    @Override
    public boolean isPurchaseOrderApprovalCommand(Command command) {
        return command instanceof ApprovePurchaseOrderCommand || command instanceof RejectPurchaseOrderCommand;
    }

    @Override
    public void checkIsValidRole(Command command) throws CommandException {
        if (model.getUser() == null) {
            return;
        }
        Staff.Role role = model.getUser().getRole();
        if (isAdminCommand(command) && !Staff.Role.admin.equals(role)) {
            throw new CommandException(MESSAGE_NO_ACCESS);
        } else if (isPurchaseOrderApprovalCommand(command) && Staff.Role.admin.equals(role)) {
            throw new CommandException(MESSAGE_NO_ACCESS);
        }
    }

    @Override
    public String maskPassword(String commandText) {
        if (commandText.contains("login") || commandText.contains("change-password")
                || commandText.contains("add-staff") || commandText.contains("edit-staff")) {
            return commandText.replaceAll("p/[a-zA-Z-0-9]*", "p/********");
        }
        return commandText;
    }
}
