package teamhardcoder.y_fi.database.manager;

import android.content.Context;

import teamhardcoder.y_fi.database.model.GroupDAO;
import teamhardcoder.y_fi.database.model.GroupExpenseDAO;
import teamhardcoder.y_fi.database.model.MessageDAO;
import teamhardcoder.y_fi.database.model.PersonalExpenseDAO;
import teamhardcoder.y_fi.database.model.UserDAO;

/**
 * Created by Andrew on 2/13/17.
 */

public class ManagerFactory {
    private static GroupExpenseManager groupExpenseManager;
    private static PersonalExpenseManager personalExpenseManager;
    private static GroupManager groupManager;
    private static MessageManager messageManager;
    private static UserManager userManager;


    public static GroupExpenseManager getGroupExpenseManager(Context context) {
        if (groupExpenseManager == null) {
            groupExpenseManager = new GroupExpenseDAO(context);
        }
        return groupExpenseManager;
    }

    public static PersonalExpenseManager getPersonalExpenseManager(Context context) {
        if (personalExpenseManager == null) {
            personalExpenseManager = new PersonalExpenseDAO(context);
        }
        return personalExpenseManager;
    }

    public static GroupManager getGroupManager(Context context) {
        if (groupManager == null) {
            groupManager = new GroupDAO(context);
        }
        return groupManager;
    }

    public static MessageManager getMessageManager(Context context) {
        if (messageManager == null) {
            messageManager = new MessageDAO(context);
        }
        return messageManager;
    }

    public static UserManager getUserManager(Context context) {
        if (userManager == null) {
            userManager = new UserDAO();
        }
        return userManager;
    }


}
