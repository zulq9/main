package seedu.inventory.commons.util;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import seedu.inventory.commons.exceptions.UnrecognizableDataException;
import seedu.inventory.storage.CsvAdaptedData;
import seedu.inventory.storage.CsvAdaptedItemList;


public class CsvUtilTest {
    @Test
    public void rawTest() {
        assertTrue(true);
        try {
            try {
                CsvAdaptedData data = CsvUtil.getDataFromFile(Paths.get("C:/test/test.csv"), new CsvAdaptedItemList());
                for (List<String> s: data.getContents()) {
                    for (String ss: s) {
                        System.out.print(ss + " ");
                    }
                    System.out.println();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (UnrecognizableDataException e) {
            e.printStackTrace();
        }
    }

}
