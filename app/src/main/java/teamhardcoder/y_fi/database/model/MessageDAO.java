package teamhardcoder.y_fi.database.model;

import android.content.Context;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teamhardcoder.y_fi.database.data.Message;
import teamhardcoder.y_fi.database.manager.MessageManager;

/**
 * Created by otto on 2/13/17.
 */

public class MessageDAO implements MessageManager {

    private DynamoDBMapper db;

    public MessageDAO(Context context) {
        db = DatabaseHelper.getDBMapper(context);
    }

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
