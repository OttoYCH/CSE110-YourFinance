package teamhardcoder.y_fi.database.model;

import android.content.Context;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public String getUserName(String userId) {
        return db.load(User.class, userId).getNickname();
    }

    public boolean editUser(User user) {
        try {
            db.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean login(String userId, String password) {
        /* If userId doesn't exist in DB or wrong password, return false */
        User userReturn = db.load(User.class, userId);
        userPool = userReturn;
        return userReturn != null && userReturn.getPassword().equals(password);
    }

    public boolean checkExist(String userId) {
        return db.load(User.class, userId) != null;
    }

    public boolean createUser(User user) {
        /*if (checkExist(user)) return false;
        editUser(user);
        return true;*/
        if(checkExist(user.getUserId())){
            return false;
        } else {
            HashSet<String> category_list = new HashSet<>();

            /* Default categories */
            category_list.add("Bill");
            category_list.add("Daily");
            category_list.add("Gasoline");
            category_list.add("Grocery");
            category_list.add("Online Shopping");
            category_list.add("Uncategorized");
            category_list.add("Restaurant");

            user.setCategory_list(category_list);
            editUser(user);
            userPool = user;
            return true;
        }
    }




}
