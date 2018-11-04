package seedu.inventory.storage.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.tag.Tag;

/**
 * Csv-friendly adapted version of the Tag.
 */
public class CsvAdaptedTag {

    private String tagName;

    /**
     * Constructs a {@code CsvAdaptedTag} with the given {@code tagName}.
     */
    public CsvAdaptedTag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given Tag into this class for Csv use.
     *
     * @param source future changes to this will not affect the created
     */
    public CsvAdaptedTag(Tag source) {
        tagName = source.tagName;
    }

    /**
     * Converts this Csv-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item
     */
    public Tag toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(tagName);
    }

    /**
     * Combine a list of Csv-friendly adapted tags into a single string.
     *
     * @param tags A list of Csv-friendly adapted tag
     * @return combinedTags A single string representing a list of Csv-friendly adapted tags
     */
    public static String combineTags(List<CsvAdaptedTag> tags) {
        String combinedTags = "";
        if (tags.size() == 0) {
            return combinedTags;
        }
        for (int i = 0; i < tags.size() - 1; i++) {
            combinedTags += tags.get(i).tagName + ",";
        }
        combinedTags += tags.get(tags.size() - 1).tagName;
        return combinedTags;
    }

    /**
     * Split a single string representing a list of Csv-friendly adapted tags to the list.
     *
     * @param combinedTags A single string representing a list of Csv-friendly adapted tags
     */
    public static List<CsvAdaptedTag> splitToTags(String combinedTags) {
        if ("".equals(combinedTags)) {
            return new ArrayList<>();
        }
        return Arrays.stream(combinedTags.split(",")).map(CsvAdaptedTag::new).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CsvAdaptedTag)) {
            return false;
        }
        return tagName.equals(((CsvAdaptedTag) other).tagName);
    }
}
