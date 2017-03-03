package teamhardcoder.y_fi.database.manager;

import java.util.List;

import teamhardcoder.y_fi.database.data.GroupExpense;

/**
 * Created by tiesto1114 on 2/13/17.
 */

public interface GroupExpenseManager {

    /**
     * Get all expense of group
     * @param groupId
     * @return list of all expense of group
     */
    public List<GroupExpense> getGroupExpense(String groupId);

    /**
     * Get a receipt
     * @param expenseId
     * @return null if the receipt doesn't exist
     */
    public GroupExpense getExpense(String expenseId);

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
    public boolean createExpense(GroupExpense expense);

    /**
     * Manually update the amount of receipt
     * @param expense
     * @return true if update successfully; false otherwise
     */
    public boolean editExpense(GroupExpense expense);
}
