package teamhardcoder.y_fi.database.model;

import android.content.Context;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import teamhardcoder.y_fi.database.data.User;
import teamhardcoder.y_fi.database.manager.UserManager;

/**
 * Created by otto on 2/13/17.
 */

public class UserDAO implements UserManager {

    String userId;


    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public boolean editUser(User user) {
        return false;
    }
}
