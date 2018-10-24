package seedu.inventory.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class FileUtilTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "FileUtilTest");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.file");

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
    public void createIfMissing() throws Exception {

        assertFalse(MISSING_FILE.toFile().exists());

        FileUtil.createIfMissing(MISSING_FILE);
        assertTrue(MISSING_FILE.toFile().exists());

        MISSING_FILE.toFile().delete();
        assertFalse(MISSING_FILE.toFile().exists());

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
