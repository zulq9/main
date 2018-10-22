package seedu.inventory.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class FileUtilTest {

    @Test
    public void isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        Assert.assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

    @Test
    public void isValidCsvFile() {
        // valid csv file
        assertTrue(FileUtil.isValidCsvFile(Paths.get("a.csv")));
        assertTrue(FileUtil.isValidCsvFile(Paths.get("/a.csv")));
        assertTrue(FileUtil.isValidCsvFile(Paths.get("a/a.csv")));
        assertTrue(FileUtil.isValidCsvFile(Paths.get("a/a.csv.csv")));

        // invalid csv file
        assertFalse(FileUtil.isValidCsvFile(Paths.get("/a")));
        assertFalse(FileUtil.isValidCsvFile(Paths.get("a.jpg")));
        assertFalse(FileUtil.isValidCsvFile(Paths.get("/a.cs")));
        assertFalse(FileUtil.isValidCsvFile(Paths.get("a.v")));

        // null path -> throws NullPointerException
        Assert.assertThrows(NullPointerException.class, () -> FileUtil.isValidCsvFile(null));
    }

}
