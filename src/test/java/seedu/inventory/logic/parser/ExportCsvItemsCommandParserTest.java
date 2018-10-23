package seedu.inventory.logic.parser;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.inventory.logic.commands.ExportCsvItemsCommand;

public class ExportCsvItemsCommandParserTest {
    private ExportCsvItemsCommandParser parser = new ExportCsvItemsCommandParser();

    @Test
    public void parse_validPath_success() throws Exception {
        Path expectedPath = Paths.get("valid.csv");
        parser.parse(" f/valid.csv");
        assertParseSuccess(parser, " f/valid.csv", new ExportCsvItemsCommand(expectedPath));
        expectedPath = Paths.get("valid/valid");
        assertParseSuccess(parser, " f/valid/valid", new ExportCsvItemsCommand(expectedPath));
        expectedPath = Paths.get("valid/valid.jpg");
        assertParseSuccess(parser, " f/valid/valid.jpg", new ExportCsvItemsCommand(expectedPath));
    }
    @Test
    public void parse_invalidPath_failure() {
        assertParseFailure(parser, " f///valid.csv", ParserUtil.MESSAGE_FILEPATH_CONSTRAINTS);
        assertParseFailure(parser, " f/:", ParserUtil.MESSAGE_FILEPATH_CONSTRAINTS);
    }
    @Test
    public void parse_invalidField_success() {
        assertParseFailure(parser, " d/valid.csv", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCsvItemsCommand.MESSAGE_USAGE));
    }
}
