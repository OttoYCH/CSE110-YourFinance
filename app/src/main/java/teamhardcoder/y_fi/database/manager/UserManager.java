package teamhardcoder.y_fi.database.manager;

import teamhardcoder.y_fi.database.data.User;

/**
 * Created by otto on 2/13/17.
 */

public interface UserManager {

    /**
     * Get the userId (User name)
     * @return
     */
    public User getUser();

    /**
     * Update the info of the user
     * @param user
     * @return true if send successfully; false otherwise
     */
    public boolean editUser(User user);

    /**
     * Check
     * @param userId
     * @param password
     * @return
     */
    public boolean login(String userId, String password);

    public boolean createUser(User user);

}
