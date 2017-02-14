package teamhardcoder.y_fi.database.model;

import android.content.Context;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teamhardcoder.y_fi.database.data.PersonalExpense;
import teamhardcoder.y_fi.database.manager.PersonalExpenseManager;

/**
 * Created by tiesto1114 on 2/13/17.
 */

public class PersonalExpenseDAO implements PersonalExpenseManager {

    private DynamoDBMapper db;

    public PersonalExpenseDAO(Context context) {
        db = DatabaseHelper.getDBMapper(context);
    }

    /**
     * Get all expense of the user
     * @param userId
     * @return list of all expense of user
     */
    public List<PersonalExpense> getPersonalExpense(String userId) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val", new AttributeValue().withS(userId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :val")
                .withExpressionAttributeValues(eav);

        return db.scan(PersonalExpense.class, scanExpression);
    }

    /**
     * Get a receipt
     * @param expenseId
     * @return null if the receipt doesn't exist
     */
    public PersonalExpense getExpense(String expenseId) {
        try {
            return db.load(PersonalExpense.class, expenseId);
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
            PersonalExpense e = db.load(PersonalExpense.class, expenseId);
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
    public boolean createExpense(PersonalExpense expense) {
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
    public boolean editExpense(PersonalExpense expense) {
        try {
            db.save(expense);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
