package seedu.inventory.storage;

import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.commons.exceptions.UnrecognizableDataException;
import seedu.inventory.commons.util.CsvUtil;
import seedu.inventory.commons.util.FileUtil;

import javax.imageio.IIOException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public class CsvReportingStorage implements ReportingStorage{
    /**
     * Returns sale list in the file or an empty sale list
     */
    public static CsvSerializableItemList loadItemListFromFile(Path file) throws
            DataConversionException, FileNotFoundException, UnrecognizableDataException {
        return new CsvSerializableItemList(CsvUtil.getDataFromFile(file, new CsvSerializableItemList()));

    }

    /**
     * Saves the given staff list data to the specified file.
     *
     * @param file the file path that data to be saved to
     * @param itemList the staff list to be saved
     *
     * @throws FileNotFoundException if file path provided is not found
     */
    public static void saveItemListToFile(Path file, CsvSerializableItemList itemList)
            throws FileNotFoundException, IOException {
        FileUtil.createIfMissing(file);
        CsvUtil.saveDataToFile(file, itemList);
    }

}
