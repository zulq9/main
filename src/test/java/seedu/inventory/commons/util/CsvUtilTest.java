package seedu.address.commons.util;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.exceptions.UnrecognizableDataException;
import seedu.address.storage.CsvAdaptedData;
import seedu.address.storage.CsvAdaptedItemList;


public class CsvUtilTest {
    @Test
    public void rawTest() {
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
