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


public class GroupHistoryAdapter extends BaseAdapter {

    List<GroupExpense> groupExpenseList;
    LayoutInflater inflater;

    public GroupHistoryAdapter(Context context, List<GroupExpense> groupExpenseList) {
        // context is Activity

        this.groupExpenseList = groupExpenseList;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return groupExpenseList.size();
    }

    @Override
    public Object getItem(int position) {
        return groupExpenseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_group_history_adapter, null);

            TextView textViewGroupExpenseName = (TextView) convertView.findViewById(R.id.textViewGroupExpenseName);
            TextView textViewAmount = (TextView) convertView.findViewById(R.id.textViewAmount);
            TextView textViewTime = (TextView) convertView.findViewById(R.id.textViewTime);

            holder = new Holder();

            holder.textViewGroupExpenseName = textViewGroupExpenseName;
            holder.textViewAmount = textViewAmount;
            holder.textViewTime = textViewTime;

            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();

        }


        holder.textViewGroupExpenseName.setText(groupExpenseList.get(position).getDescription());
        holder.textViewAmount.setText("$ " + Double.toString(groupExpenseList.get(position).getAmount()));

        String time = groupExpenseList.get(position).getCreatedDate();
        String[] splitTime = time.split("T");

        holder.textViewTime.setText(splitTime[0] + "   " + splitTime[1].split("Z")[0]);


        return convertView;
    }

    public class Holder {
        TextView textViewGroupExpenseName;
        TextView textViewAmount;
        TextView textViewTime;

    }
}

