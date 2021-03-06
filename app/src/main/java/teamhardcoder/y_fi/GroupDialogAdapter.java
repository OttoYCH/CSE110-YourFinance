package teamhardcoder.y_fi;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by otto on 3/8/17.
 */

public class GroupDialogAdapter extends BaseAdapter {
    List<String> nickNameList;
    List<Double> splitAmountList;
    List<String> userIdList;
    LayoutInflater inflater;

    public GroupDialogAdapter(Context context, List<String> nickNameList, List<Double> splitAmountList, List<String> userIdList){
        this.inflater = LayoutInflater.from(context);
        this.nickNameList = nickNameList;
        this.splitAmountList = splitAmountList;
        this.userIdList = userIdList;
    }

    @Override
    public int getCount() {
        return nickNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return nickNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.activity_group_expense_dialog_adapter, null);

            TextView memberName = (TextView) convertView.findViewById(R.id.memberName);
            EditText editSplitAmount = (EditText) convertView.findViewById(R.id.editSplitAmount);
            TextView dollarsign = (TextView)   convertView.findViewById(R.id.dollarSign_dialog);
            //editSplitAmount.requestFocus();


            holder = new Holder();

            holder.memberName = memberName;
            holder.editSplitAmount = editSplitAmount;
            holder.dollarsign = dollarsign;

            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();

        }

        holder.memberName.setText(nickNameList.get(position));
        holder.editSplitAmount.setText(Double.toString(splitAmountList.get(position)));

        holder.editSplitAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {
                    splitAmountList.set(position, Double.valueOf(editable.toString()));
                    System.out.println(splitAmountList.get(position));
                }

                String text = editable.toString();
                //System.out.println(text);
                //System.out.println("posi: " + position);

            }
        });

        return convertView;
    }

    public class Holder{
        TextView memberName;
        EditText editSplitAmount;
        TextView dollarsign;
    }

}
