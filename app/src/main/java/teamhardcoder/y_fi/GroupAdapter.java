package teamhardcoder.y_fi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import teamhardcoder.y_fi.database.data.*;

public class GroupAdapter extends BaseAdapter {

    List<teamhardcoder.y_fi.database.data.Group> groupList;
    LayoutInflater inflater;

    public GroupAdapter(Context context, List<teamhardcoder.y_fi.database.data.Group> groupList){
        // context is Activity

        this.groupList = groupList;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return groupList.size();
    }

    @Override
    public Object getItem(int position) {
        return groupList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.activity_group_adapter, null);

            TextView textViewGroupName = (TextView) convertView.findViewById(R.id.textViewGroupName);

            holder = new Holder();

            holder.textViewGroupName = textViewGroupName;

            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();

        }

        holder.textViewGroupName.setText(groupList.get(position).getGroupName());


        return convertView;
    }

    public class Holder{
        TextView textViewGroupName;

    }
}
