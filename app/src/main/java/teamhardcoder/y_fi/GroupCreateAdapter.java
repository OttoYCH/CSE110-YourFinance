package teamhardcoder.y_fi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import teamhardcoder.y_fi.database.*;
import teamhardcoder.y_fi.database.data.User;

/**
 * Created by jessicawu on 3/6/17.
 */

public class GroupCreateAdapter extends BaseAdapter{
    List<User> friendList;
    LayoutInflater inflater;

    public GroupCreateAdapter(Context context, List<User> friendList){
        this.friendList = friendList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int itemId) {
        return itemId;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {

        GroupCreateAdapter.Holder holder;
        User user = (User) getItem(position);

        if(contentView == null){

            contentView = inflater.inflate(R.layout.activity_group_create_adapter, null);
            TextView textViewFriendName = (TextView) contentView.findViewById(R.id.textViewFriendName);
            holder = new GroupCreateAdapter.Holder();
            holder.textViewFriendName = textViewFriendName;
            contentView.setTag(holder);

        } else {
            holder = (GroupCreateAdapter.Holder) contentView.getTag();
        }

        holder.textViewFriendName.setText(friendList.get(position).getUserId());

        return contentView;
    }

    public class Holder{
        TextView textViewFriendName;
    }
}

