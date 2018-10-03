package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.staff.Staff;

/**
 * An user session object to keep track user's login status
 * and retrieve current user's information.
 */
public class UserSession {

    private Staff user;
    private boolean isLoggedIn;

    /**
     * Constructs a new UserSession object with {@code user} and {@code isLoggedIn}.
     *
     * @param user user who is logging into the system
     * @param isLoggedIn the login status of the user
     */
    public UserSession(Staff user, boolean isLoggedIn) {
        requireAllNonNull(user, isLoggedIn);
        this.user = user;
        this.isLoggedIn = isLoggedIn;
    }

    /**
     * Updates the information of the current user.
     *
     * @param updatedUser the user with updated information.
     */
    public void updateUser(Staff updatedUser) {
        requireNonNull(user);
        this.user = user;
    }

    /**
     * Clears the user's object in the session and set isLoggedIn status to false.
     */
    public void logout() {
        this.user = null;
        this.isLoggedIn = false;
    }

}
