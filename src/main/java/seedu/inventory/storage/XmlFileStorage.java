package seedu.inventory.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.commons.util.XmlUtil;

/**
 * Stores inventory data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given inventory data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableInventory inventory)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, inventory);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns inventory in the file or an empty inventory list
     */
    public static XmlSerializableInventory loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableInventory.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
