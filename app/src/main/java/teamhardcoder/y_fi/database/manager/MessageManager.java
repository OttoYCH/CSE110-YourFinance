package teamhardcoder.y_fi.database.manager;

import java.util.List;

import teamhardcoder.y_fi.database.data.Message;

/**
 * Created by otto on 2/13/17.
 */

public interface MessageManager {

    /**
     * Display all message in the group
     * @param groupId
     * @return List of text content in the group
     */
    public List<Message> getGroupMessage(String groupId);

    /**
     * Send a message
     * @param message
     * @return true if send successfully; false otherwise
     */
    public boolean sendMessage(Message message);
}
