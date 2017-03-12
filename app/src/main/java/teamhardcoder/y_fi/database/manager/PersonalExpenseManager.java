package teamhardcoder.y_fi.database.manager;

import java.util.List;
import java.util.Map;

import teamhardcoder.y_fi.database.data.PersonalExpense;

/**
 * Created by tiesto1114 on 2/13/17.
 */

public interface PersonalExpenseManager {

    /**
     * Get all expense of the user
     * @param userId
     * @return list of all expense of user
     */
    public List<PersonalExpense> getPersonalExpense(String userId);

    /**
     * Get a list of Entry. The entry is sorted by the key(created month) with its value a list of
     * PersonalExpense
     * @return list of all expense of user
     */
    public List<Map.Entry<String, List<PersonalExpense>>> getMonthlyPersonalExpenseList();

    /**
     * Get a receipt
     * @param expenseId
     * @return null if the receipt doesn't exist
     */
    public PersonalExpense getExpense(String expenseId);

    /**
     * Delete a receipt
     * @param expenseId
     * @return true if delete successfully; false otherwise
     */
    public boolean deleteExpense(String expenseId);

    /**
     * Create a receipt(expense)
     * @param expense
     * @return true if create successfully; false otherwise
     */
    public boolean createExpense(PersonalExpense expense);

    /**
     * Manually update the amount of receipt
     * @param expense
     * @return true if update successfully; false otherwise
     */
    public boolean editExpense(PersonalExpense expense);
}
