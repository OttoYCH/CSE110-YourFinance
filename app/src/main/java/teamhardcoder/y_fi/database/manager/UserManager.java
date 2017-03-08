package teamhardcoder.y_fi.database.manager;

import java.util.List;

import teamhardcoder.y_fi.database.data.User;

/**
 * Created by Andrew on 2/13/17.
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
     * Check if userId exist in DB or if the password is correct
     * @param userId
     * @param password
     * @return true to login
     */
    public boolean login(String userId, String password);

    /**
     * Check if the user already exist, create a user in DB if not exist
     * @param user
     * @return ture and create a new user
     */
    public boolean createUser(User user);

    /**
     * Search the DB to see if the user already exist
     * @param userId
     * @return true if the user already exist
     */
    public boolean checkExist(String userId);

}
