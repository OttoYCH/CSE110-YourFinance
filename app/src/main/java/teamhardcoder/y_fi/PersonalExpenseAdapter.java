package teamhardcoder.y_fi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import teamhardcoder.y_fi.database.data.PersonalExpense;

public class PersonalExpenseAdapter extends BaseAdapter {

    private List<PersonalExpense> personalExpenseList;
    LayoutInflater inflater;

    public PersonalExpenseAdapter(Context context, List<PersonalExpense> personalExpenseList) {
        this.personalExpenseList = personalExpenseList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return personalExpenseList.size();
    }

    @Override
    public Object getItem(int position) {
        return personalExpenseList.get(position);
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

            TextView textViewPersonalExpenseName = (TextView) convertView.findViewById(R.id.textViewGroupExpenseName);
            TextView textViewAmount_P = (TextView) convertView.findViewById(R.id.textViewAmount);
            TextView textViewTime_P = (TextView) convertView.findViewById(R.id.textViewTime);

            holder = new Holder();
            holder.textViewPersonalExpenseName = textViewPersonalExpenseName;
            holder.textViewAmount_P = textViewAmount_P;
            holder.textViewTime_P = textViewTime_P;

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.textViewPersonalExpenseName.setText(personalExpenseList.get(position).getDescription());
        holder.textViewAmount_P.setText("$ " + String.format("%.2f", personalExpenseList.get(position).getAmount()));
        String time = personalExpenseList.get(position).getCreatedDate();
        holder.textViewTime_P.setText(time.substring(0, 10) + "   " + time.substring(11, 19));

        return convertView;
    }

    public class Holder {
        TextView textViewPersonalExpenseName;
        TextView textViewAmount_P;
        TextView textViewTime_P;
    }
}
