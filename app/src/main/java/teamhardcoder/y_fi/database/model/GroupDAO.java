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
import teamhardcoder.y_fi.database.manager.GroupManager;

/**
 * Created by otto on 2/13/17.
 */

public class GroupDAO implements GroupManager {

    private DynamoDBMapper db;

    public GroupDAO(Context context) {
        db = DatabaseHelper.getDBMapper(context);
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
            Group group = db.load(Group.class, groupId);
            db.delete(group);
            return true;
        } catch (Exception e)  {
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
