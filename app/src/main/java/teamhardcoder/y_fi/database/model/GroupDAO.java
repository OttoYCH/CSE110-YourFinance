package teamhardcoder.y_fi.database.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teamhardcoder.y_fi.database.data.Group;

/**
 * Created by otto on 2/13/17.
 */

public class GroupDAO {

    private DynamoDBMapper db;

    public List<Group> getAllGroupsOfUser(String userId) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val", new AttributeValue().withS(userId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :val")
                .withExpressionAttributeValues(eav);

        return db.scan(Group.class, scanExpression);
    };

    public Group getGroup(String groupId) {
        return db.load(Group.class, groupId);
    };

    public boolean deleteGroup(String groupId) {
        Group group = db.load(Group.class, groupId);
        if (group == null)
            return false;
        else {
            db.delete(group);
            return true;
        }
    };

    public boolean createGroup(Group group) {
        db.save(group);
        return true;
    };

    public boolean editGroup(Group group) {
        db.save(group);
        return true;
    };
}
