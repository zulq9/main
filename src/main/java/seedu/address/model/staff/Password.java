package seedu.address.model.staff;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Represents a Staff's password in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidPassword(String)}
 */
public class Password {
    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "Password should only contain alphanumberic characters only and it should not be blank.";

    public static final String PASSWORD_VALIDATION_REGEX = "[\\p{Alnum}]*";

    public final String password;

    /**
     * Constructs a {@code Password}.
     *
     * @param password A valid Password.
     */
    public Password(String password) {
        requireNonNull(password);
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINTS);
        this.password = hash(password);
    }

    private String hash(String password) {
        byte[] encodehash = new byte[0];
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            encodehash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bytesToHex(encodehash);
    }

    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.password;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Password)) {
            return false;
        }

        if (other == this) {
            return true;
        }

        return password.equals(((Password) other).password);
    }

    @Override
    public int hashCode() {
        return password.hashCode();
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        String hex;
        for (int i = 0; i < hash.length; i++) {
            hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append(0);
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
