package teamhardcoder.y_fi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import teamhardcoder.y_fi.database.data.Message;


public class GroupChatAdapter extends BaseAdapter {

    List<Message> groupChatList;
    LayoutInflater inflater;

    public GroupChatAdapter(Context context, List<Message> groupChatList) {
        // context is Activity
        this.groupChatList = groupChatList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return groupChatList.size();
    }

    @Override
    public Object getItem(int position) {
        return groupChatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GroupChatAdapter.Holder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_group_chat_adapter, null);

            TextView chatTextViewContent = (TextView) convertView.findViewById(R.id.chatTextViewContent);
            TextView chatTextViewUserName = (TextView) convertView.findViewById(R.id.chatTextViewUserName);
            TextView chatTextViewTime = (TextView) convertView.findViewById(R.id.chatTextViewTime);

            holder = new GroupChatAdapter.Holder();

            holder.chatTextViewContent = chatTextViewContent;
            holder.chatTextViewUserName = chatTextViewUserName;
            holder.chatTextViewTime = chatTextViewTime;

            convertView.setTag(holder);

        } else {
            holder = (GroupChatAdapter.Holder) convertView.getTag();

        }

        holder.chatTextViewContent.setText(groupChatList.get(position).getContent());
        holder.chatTextViewUserName.setText(groupChatList.get(position).getUserName());
        String time = groupChatList.get(position).getCreatedDate();
        holder.chatTextViewTime.setText(time.substring(0, 10) + "   " + time.substring(11, 19));

        return convertView;
    }

    public class Holder {
        TextView chatTextViewContent;
        TextView chatTextViewUserName;
        TextView chatTextViewTime;
    }
}

