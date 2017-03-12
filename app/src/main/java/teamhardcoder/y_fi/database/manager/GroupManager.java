package teamhardcoder.y_fi.database.manager;

import java.util.List;

import teamhardcoder.y_fi.database.data.Group;

/**
 * Created by otto on 2/13/17.
 */

public interface GroupManager {

    /**
     * Display a list of all groups of the user
     * @param userId
     * @return set of groups
     */
    public List<Group> getAllGroupsOfUser(String userId);

    /**
     * Link to group table
     * @param groupId
     * @return null if group doesn't exist
     */
    public Group getGroup(String groupId);

    /**
     * Delete a group
     * @param groupId
     * @return true if delete a group successfully; false otherwise
     */
    public boolean deleteGroup(String groupId);

    /**
     * Create a new group
     * @param group
     * @return true if create a group successfully; false otherwise
     */
    public boolean createGroup(Group group);

    /**
     * Update a whole group, will fail if update non-existing group
     * @param group
     * @return true if update successfully; false otherwise
     */
    public boolean editGroup(Group group);
}
