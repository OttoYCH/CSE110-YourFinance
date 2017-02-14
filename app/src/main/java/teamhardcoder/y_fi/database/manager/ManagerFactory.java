package teamhardcoder.y_fi.database.manager;

import android.content.Context;

import teamhardcoder.y_fi.database.model.ExpenseDAO;
import teamhardcoder.y_fi.database.model.GroupDAO;
import teamhardcoder.y_fi.database.model.MessageDAO;
import teamhardcoder.y_fi.database.model.UserDAO;

/**
 * Created by Andrew on 2/13/17.
 */

public class ManagerFactory {
    private static ExpenseManager expenseManager;
    private static GroupManager groupManager;
    private static MessageManager messageManager;
    private static UserManager userManager;

    public static ExpenseManager getExpenseManager(Context context) {
        if (expenseManager == null) {
            expenseManager = new ExpenseDAO(context);
        }
        return expenseManager;
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
            userManager = new UserDAO(context);
        }
        return userManager;
    }


}
