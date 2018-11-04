package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.inventory.testutil.TypicalItems.SAMSUNG;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.tag.Tag;
import seedu.inventory.storage.csv.CsvAdaptedTag;


public class CsvAdaptedTagTest {
    private static final String INVALID_TAG_NAME = "#gadget";
    private static final Tag VALID_TAG = new Tag("samsung");
    private static final CsvAdaptedTag VALID_ADAPTED_TAG = new CsvAdaptedTag(VALID_TAG);
    private static final List<CsvAdaptedTag> VALID_ADAPTED_TAGS = SAMSUNG.getTags().stream()
            .map(CsvAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_COMBINED_TAGS = "samsung,smartphone";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() throws Exception {
        assertEquals(VALID_ADAPTED_TAGS, VALID_ADAPTED_TAGS);
        assertNotEquals(VALID_ADAPTED_TAG, INVALID_TAG_NAME);
        assertEquals(VALID_ADAPTED_TAG, VALID_ADAPTED_TAG);
    }

    @Test
    public void toModelType_invalidTagDetails_illegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        CsvAdaptedTag adaptedTag = new CsvAdaptedTag(INVALID_TAG_NAME);
        adaptedTag.toModelType();
    }

    @Test
    public void toModelType_validTagDetails_returnsTag() throws Exception {
        assertEquals(VALID_TAG, VALID_ADAPTED_TAG.toModelType());
    }

    @Test
    public void combineTags() throws Exception {
        assertEquals(VALID_COMBINED_TAGS, CsvAdaptedTag.combineTags(VALID_ADAPTED_TAGS));
    }

    @Test
    public void splitToTags() throws Exception {
        assertEquals(VALID_ADAPTED_TAGS, CsvAdaptedTag.splitToTags(VALID_COMBINED_TAGS));
    }
}
