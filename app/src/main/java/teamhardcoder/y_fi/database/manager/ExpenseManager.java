package teamhardcoder.y_fi.database.manager;

import java.util.List;

import teamhardcoder.y_fi.database.data.Expense;

/**
 * Created by otto on 2/13/17.
 */

public interface ExpenseManager {

    /**
     * Get all expense of the user
     * @param userId
     * @return list of all expense of user
     */
    public List<Expense> getUserExpense(String userId);

    /**
     * Get all expense of group
     * @param groupId
     * @return list of all expense of group
     */
    public List<Expense> getGroupExpense(String groupId);

    /**
     * Get a receipt
     * @param expenseId
     * @return null if the receipt doesn't exist
     */
    public Expense getExpense(String expenseId);

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
    public boolean createExpense(Expense expense);

    /**
     * Manually update the amount of receipt
     * @param expense
     * @return true if update successfully; false otherwise
     */
    public boolean editExpense(Expense expense);
}
