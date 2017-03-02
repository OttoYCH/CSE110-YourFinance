package teamhardcoder.y_fi.database.manager;

import teamhardcoder.y_fi.database.data.User;

/**
 * Created by otto on 2/13/17.
 */

public interface UserManager {

    /**
     * Get the user
     * @param userId
     * @return
     */
    public User getUser(String userId);

    /**
     * Update the info of the user
     * @param user
     * @return true if send successfully; false otherwise
     */
    public boolean editUser(User user);
}
