package seedu.inventory.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.inventory.commons.core.ComponentManager;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CommandResult;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.logic.parser.AddressBookParser;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Item> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
