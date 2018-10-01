package seedu.inventory.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;

/**
 * Represents a Item's inventory in the inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidImage(String)}
 */
public class Image {

    public static final String MESSAGE_IMAGE_CONSTRAINTS =
            "Image file path must be a valid file path and must be a valid image, and it should not be blank.";

    /*
     * The first character of the image file path must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String IMAGE_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Image}.
     *
     * @param image A valid image.
     */
    public Image(String image) {
        requireNonNull(image);
        checkArgument(isValidImage(image), MESSAGE_IMAGE_CONSTRAINTS);
        value = image;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidImage(String test) {
        if (test.matches(IMAGE_VALIDATION_REGEX)) {
            File file = new File(test);

            if (file.exists()) {
                try {
                    String mimetype = Files.probeContentType(file.toPath());

                    if (mimetype != null && mimetype.split("/")[0].equals("image")) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Image // instanceof handles nulls
                && value.equals(((Image) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
