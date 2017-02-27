package teamhardcoder.y_fi.database.manager;

import teamhardcoder.y_fi.database.data.User;

/**
 * Created by Andrew on 2/13/17.
 */

public interface UserManager {

    /**
     * Get the user id
     * @return String user id
     */
    public String getUserId();

    /**
     * Get the user
     * @return User
     */
    public User getUser();

    /**
     * Update the info of the user
     * @param user
     * @return true if send successfully; false otherwise
     */
    public boolean editUser(User user);


}
