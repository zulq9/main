package seedu.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.commons.exceptions.UnrecognizableDataException;
import seedu.inventory.commons.util.CsvUtil;
import seedu.inventory.commons.util.FileUtil;
import seedu.inventory.model.ReadOnlyItemList;

public class CsvReportingStorage implements ReportingStorage {

    public Optional<ReadOnlyItemList> importItemList(Path filePath) throws DataConversionException, IOException {
        try {
            CsvSerializableItemList items = new CsvSerializableItemList(CsvUtil.getDataFromFile(filePath,
                    new CsvSerializableItemList()));
            return Optional.of(items.toModelType());
        } catch (UnrecognizableDataException e) {

        } catch (IllegalValueException e) {

        }
        return Optional.empty();
    }

    public void exportItemList(ReadOnlyItemList itemList, Path filePath) throws IOException {
        FileUtil.createIfMissing(filePath);
        CsvUtil.saveDataToFile(filePath, new CsvSerializableItemList(itemList));
    }

}
