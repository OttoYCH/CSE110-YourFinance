package teamhardcoder.y_fi.database.model;

import android.content.Context;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teamhardcoder.y_fi.database.data.Group;
import teamhardcoder.y_fi.database.data.GroupExpense;
import teamhardcoder.y_fi.database.data.Message;
import teamhardcoder.y_fi.database.manager.GroupExpenseManager;
import teamhardcoder.y_fi.database.manager.GroupManager;
import teamhardcoder.y_fi.database.manager.ManagerFactory;
import teamhardcoder.y_fi.database.manager.MessageManager;

/**
 * Created by otto on 2/13/17.
 */

public class GroupDAO implements GroupManager {

    private DynamoDBMapper db;
    private Context context;

    public GroupDAO(Context context) {
        db = DatabaseHelper.getDBMapper(context);
        this.context = context;
    }

    public List<Group> getAllGroupsOfUser(String userId) {
        Map<String, AttributeValue> filter = new HashMap<String, AttributeValue>();
        filter.put(":user",new AttributeValue().withS(userId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(userIdSet,:user)")
                .withExpressionAttributeValues(filter);

        PaginatedScanList<Group> scanResult = db.scan(Group.class, scanExpression);

        if(scanResult != null){
            return scanResult;
        }

        return null;
    };

    public Group getGroup(String groupId) {
        try {
            return db.load(Group.class, groupId);
        } catch (Exception e) {
            return null;
        }
    };

    public boolean deleteGroup(String groupId) {
        try {
            Group groupToDelete = db.load(Group.class, groupId);
            GroupExpenseManager groupExpManager = ManagerFactory.getGroupExpenseManager(this.context);
            for (GroupExpense groupExp:groupExpManager.getGroupExpense(groupId)) {
                db.delete(groupExp);    // delete group expense
            }

            MessageManager msgManager = ManagerFactory.getMessageManager(this.context);
            for (Message msg: msgManager.getGroupMessage(groupId)) {
                db.delete(msg);         // delete group message
            }

            db.delete(groupToDelete);   // delete group object
            return true;
        } catch (NullPointerException e)  {
            return false;
        }
    };

    public boolean createGroup(Group group) {
        try {
            db.save(group);
            return true;
        } catch (Exception e) {
            return false;
        }
    };

    public boolean editGroup(Group group) {
        try {
            db.save(group);
            return true;
        } catch (Exception e) {
            return false;
        }
    };
}
