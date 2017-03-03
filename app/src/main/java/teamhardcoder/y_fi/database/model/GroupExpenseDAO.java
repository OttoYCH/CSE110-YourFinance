package teamhardcoder.y_fi.database.model;

import android.content.Context;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teamhardcoder.y_fi.database.data.GroupExpense;
import teamhardcoder.y_fi.database.data.PersonalExpense;
import teamhardcoder.y_fi.database.manager.GroupExpenseManager;

/**
 * Created by tiesto1114 on 2/13/17.
 */

public class GroupExpenseDAO implements GroupExpenseManager {

    private DynamoDBMapper db;

    public GroupExpenseDAO(Context context) {
        db = DatabaseHelper.getDBMapper(context);
    }

    /**
     * Get all expense of group
     * @param groupId
     * @return list of all expense of group
     */
    public List<GroupExpense> getGroupExpense(String groupId) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val", new AttributeValue().withS(groupId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("groupId = :val")
                .withExpressionAttributeValues(eav);

        return db.scan(GroupExpense.class, scanExpression);
    }

    /**
     * Get a receipt
     * @param expenseId
     * @return null if the receipt doesn't exist
     */
    public GroupExpense getExpense(String expenseId) {
        try {
            return db.load(GroupExpense.class, expenseId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Delete a receipt
     * @param expenseId
     * @return true if delete successfully; false otherwise
     */
    public boolean deleteExpense(String expenseId) {
        try {
            GroupExpense e = db.load(GroupExpense.class, expenseId);
            db.delete(e);
            return true;
        } catch (Exception e)  {
            return false;
        }
    }

    /**
     * Create a receipt(expense)
     * @param expense
     * @return true if create successfully; false otherwise
     */
    public boolean createExpense(GroupExpense expense) {
        try {
            db.save(expense);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Manually update the amount of receipt
     * @param expense
     * @return true if update successfully; false otherwise
     */
    public boolean editExpense(GroupExpense expense) {
        try {
            db.save(expense);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
