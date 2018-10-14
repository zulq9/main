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
     * Returns inventory book in the file or an empty inventory book
     */
    public static XmlSerializableInventory loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableInventory.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Saves the given inventory data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableInventory addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Saves the given sale list data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableSaleList saleList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, saleList);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns sale list in the file or an empty sale list
     */
    public static XmlSerializableSaleList loadSaleListFromSaveFile(Path file) throws
            DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableSaleList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
