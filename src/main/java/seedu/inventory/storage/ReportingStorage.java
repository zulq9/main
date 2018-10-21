package seedu.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.ReadOnlyItemList;

/**
 * Represents a storage for reporting module.
 */
public interface ReportingStorage {

    Optional<ReadOnlyItemList> importItemList(Path filePath) throws DataConversionException, IOException;

    void exportItemList(ReadOnlyItemList itemList, Path filePath) throws IOException;

}
