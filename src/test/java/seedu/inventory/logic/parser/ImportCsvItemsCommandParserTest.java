package seedu.inventory.logic.parser;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.inventory.logic.commands.ImportCsvItemsCommand;

public class ImportCsvItemsCommandParserTest {
    private ImportCsvItemsCommandParser parser = new ImportCsvItemsCommandParser();

    @Test
    public void parse_validPath_success() throws Exception {
        parser.parse(" f/valid.csv");
        Path expectedPath = Paths.get("valid.csv");
        assertParseSuccess(parser, " f/valid.csv", new ImportCsvItemsCommand(expectedPath));
        expectedPath = Paths.get("valid/valid");
        assertParseSuccess(parser, " f/valid/valid", new ImportCsvItemsCommand(expectedPath));
        expectedPath = Paths.get("valid/valid.jpg");
        assertParseSuccess(parser, " f/valid/valid.jpg", new ImportCsvItemsCommand(expectedPath));
    }

    @Test
    public void parse_invalidPath_failure() {
        assertParseFailure(parser, " f/ csv/:csv", ParserUtil.MESSAGE_FILEPATH_CONSTRAINTS);
    }

    @Test
    public void parse_invalidField_success() {
        assertParseFailure(parser, " d/valid.csv", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCsvItemsCommand.MESSAGE_USAGE));
    }
}
