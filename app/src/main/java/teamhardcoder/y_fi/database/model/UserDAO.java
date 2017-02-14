package teamhardcoder.y_fi.database.model;

import android.content.Context;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import teamhardcoder.y_fi.database.data.User;
import teamhardcoder.y_fi.database.manager.UserManager;

/**
 * Created by otto on 2/13/17.
 */

public class UserDAO implements UserManager {

    private DynamoDBMapper db;

    public UserDAO(Context context) {
        db = DatabaseHelper.getDBMapper(context);
    }

    /**
     * Get the user
     * @param userId
     * @return
     */
    public User getUser(String userId) {
        return db.load(User.class, userId);
    }

    /**
     * Update the info of the user
     * @param user
     * @return true if send successfully; false otherwise
     */
    public boolean editUser(User user) {
        db.save(user);
        return true;
    }
}
