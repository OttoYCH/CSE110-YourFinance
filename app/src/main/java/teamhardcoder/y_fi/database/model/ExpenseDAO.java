package teamhardcoder.y_fi.database.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teamhardcoder.y_fi.database.data.Expense;

/**
 * Created by otto on 2/13/17.
 */

public class ExpenseDAO {

    private DynamoDBMapper db;

    /**
     * Get all expense of the user
     * @param userId
     * @return list of all expense of user
     */
    public List<Expense> getUserExpense(String userId) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val", new AttributeValue().withS(userId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :val")
                .withExpressionAttributeValues(eav);

        return db.scan(Expense.class, scanExpression);
    }

    /**
     * Get all expense of group
     * @param groupId
     * @return list of all expense of group
     */
    public List<Expense> getGroupExpense(String groupId) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val", new AttributeValue().withS(groupId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("groupId = :val")
                .withExpressionAttributeValues(eav);

        return db.scan(Expense.class, scanExpression);
    }

    /**
     * Get a receipt
     * @param expenseId
     * @return null if the receipt doesn't exist
     */
    public Expense getExpense(String expenseId) {
        return db.load(Expense.class, expenseId);
    }

    /**
     * Delete a receipt
     * @param expenseId
     * @return true if delete successfully; false otherwise
     */
    public boolean deleteExpense(String expenseId) {
        Expense e = db.load(Expense.class, expenseId);
        if (e == null)
            return false;
        else {
            db.delete(e);
            return true;
        }
    }

    /**
     * Create a receipt(expense)
     * @param expense
     * @return true if create successfully; false otherwise
     */
    public boolean createExpense(Expense expense) {
        db.save(expense);
        return true;
    }

    /**
     * Manually update the amount of receipt
     * @param expense
     * @return true if update successfully; false otherwise
     */
    public boolean editExpense(Expense expense) {
        db.save(expense);
        return true;
    }
}
