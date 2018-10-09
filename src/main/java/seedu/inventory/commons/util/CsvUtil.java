package seedu.inventory.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.inventory.commons.exceptions.UnrecognizableDataException;
import seedu.inventory.storage.CsvAdaptedData;
/**
 * Helps with reading from and writing to CSV files.
 */
public class CsvUtil {
    /**
     * Returns the csv data in the file as an object of the specified type. The format of data is constrained.
     *
     * @param file           Points to a valid csv file containing data that match the {@code dataTypeToConvert}.
     *                       Cannot be null.
     * @param dataTypeToConvert The class corresponding to the csv data.
     *                       Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     * @throws UnrecognizableDataException Thrown if the file is empty or does not have the correct format.
     */
    @SuppressWarnings("unchecked")
    public static CsvAdaptedData getDataFromFile(Path file, CsvAdaptedData dataTypeToConvert)
            throws FileNotFoundException, UnrecognizableDataException {
        requireNonNull(file);
        requireNonNull(dataTypeToConvert);
        if (!Files.exists(file)) {
            throw new FileNotFoundException("File not found : " + file.toAbsolutePath());
        }
        if (!isDataHeaderRecognizable(file, dataTypeToConvert)) {
            throw new UnrecognizableDataException("File header format can not be recognized");
        }
        List<List<String>> contents = getDataContentFromFile(file, dataTypeToConvert);

        return new CsvAdaptedData(contents);
    }

    /**
     * Saves the data in the file in xml format.
     *
     * @param file Points to a valid xml file containing data that match the {@code classToConvert}.
     *             Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     */
    public static <T> void saveDataToFile(Path file, T data) throws FileNotFoundException {
        requireNonNull(file);
        requireNonNull(data);

        if (!Files.exists(file)) {
            throw new FileNotFoundException("File not found : " + file.toAbsolutePath());
        }

    }

    /**
     * Returns true if header in the csv file is recognizable.
     *
     * @param file           Points to a valid csv file containing data that match the {@code dataTypeToConvert}.
     *                       Cannot be null.
     * @param dataTypeToConvert The class corresponding to the csv data.
     *                       Cannot be null.
     */
    public static boolean isDataHeaderRecognizable(Path file, CsvAdaptedData dataTypeToConvert) {
        requireNonNull(file);
        requireNonNull(dataTypeToConvert);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file.toFile()));
            List<String> dataTypes = Arrays.stream(reader.readLine().split(",")).collect(Collectors.toList());

            if (!isDataTypeEqual(dataTypes, dataTypeToConvert)) {
                return false;
            }

            List<String> dataFields = Arrays.stream(reader.readLine().split(",")).collect(Collectors.toList());

            if (!isDataFieldsEqual(dataFields, dataTypeToConvert)) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if header in the csv file is recognizable.
     *
     * @param file           Points to a valid csv file containing data that match the {@code dataTypeToConvert}.
     *                       Cannot be null.
     * @param dataTypeToConvert The class corresponding to the csv data.
     *                       Cannot be null.
     */
    public static List<List<String>> getDataContentFromFile(Path file, CsvAdaptedData dataTypeToConvert)
            throws UnrecognizableDataException {
        requireNonNull(file);
        requireNonNull(dataTypeToConvert);
        BufferedReader reader;
        List<List<String>> contents = new LinkedList<>();
        try {
            reader = new BufferedReader(new FileReader(file.toFile()));
            reader.readLine();
            long fieldsNumber = Arrays.stream(reader.readLine().split(",")).count();
            String contentLine;
            while ((contentLine = reader.readLine()) != null) {
                List<String> content = Arrays.stream(contentLine.split(",")).collect(Collectors.toList());
                if (content.size() != fieldsNumber) {
                    throw new UnrecognizableDataException("File content format can not be recognized");
                }
                contents.add(content);
            }
        } catch (IOException e) {
            throw new UnrecognizableDataException("File content format can not be recognized");
        }
        return contents;
    }

    /**
     * Returns true if data type in the csv file equals the data type of  {@code dataTypeToConvert}.
     *
     * @param dataTypes         The types of data indicated in the csv file.
     * @param dataTypeToConvert The class corresponding to the csv data.
     */
    private static boolean isDataTypeEqual(List<String> dataTypes, CsvAdaptedData dataTypeToConvert) {
        return dataTypes.size() == 1 && dataTypes.get(0).equals(dataTypeToConvert.getDataType());
    }

    /**
     * Returns true if fields in the csv file equals the fields of  {@code dataTypeToConvert}.
     *
     * @param dataFields         The fields of data indicated in the csv file.
     * @param dataTypeToConvert The class corresponding to the csv data.
     */
    private static boolean isDataFieldsEqual(List<String> dataFields, CsvAdaptedData dataTypeToConvert) {
        return dataFields.equals(new LinkedList<>(Arrays.asList(dataTypeToConvert.getDataFields())));
    }



}
