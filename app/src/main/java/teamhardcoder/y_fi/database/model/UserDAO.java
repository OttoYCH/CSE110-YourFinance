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
    private User userPool;
    private Context context;

    public UserDAO(Context context) {
        db = DatabaseHelper.getDBMapper(context);
        this.context = context;
    }

    /**
     * User getter
     * @return
     */
    public User getUser() {
        return userPool; // if return null, outside should generate error message
    }

    /**
     * Update the info of the user
     * @param user
     * @return true if send successfully; false otherwise
     */
    public boolean editUser(User user) {
        try {
            db.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean login(String userId, String password) {
        if (db.load(User.class, userId) == null) {
            return true;
        }
        return false;
    }

    public boolean createUser(User user) {
        if (db.load(User.class, user) == null) {
            return true;
        }
        return true;
    }
}
