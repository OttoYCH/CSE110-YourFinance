package teamhardcoder.y_fi.database.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teamhardcoder.y_fi.database.data.Message;

/**
 * Created by otto on 2/13/17.
 */

public class MessageDAO {

    private DynamoDBMapper db;

    /**
     * Display all message in the group
     * @param groupId
     * @return List of text content in the group
     */
    public List<Message> getGroupMessage(String groupId) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val", new AttributeValue().withS(groupId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("groupId = :val")
                .withExpressionAttributeValues(eav);

        return db.scan(Message.class, scanExpression);
    }

    /**
     * Send a message
     * @param message
     * @return true if send successfully; false otherwise
     */
    public boolean sendMessage(Message message) {
        db.save(message);
        return true;
    }
}
