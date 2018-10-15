package seedu.inventory.storage;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.tag.Tag;

/**
 * A class to access item list data stored as an csv file on the hard disk.
 */
public class CsvAdaptedTag {

    private String tagName;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public CsvAdaptedTag() {}

    /**
     * Constructs a {@code CsvAdaptedTag} with the given {@code tagName}.
     */
    public CsvAdaptedTag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public CsvAdaptedTag(Tag source) {
        tagName = source.tagName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item
     */
    public Tag toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(tagName);
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
