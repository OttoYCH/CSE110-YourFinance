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

import java.util.List;

/**
 * Created by otto on 3/8/17.
 */

public class GroupDialogAdapter extends BaseAdapter {
    List<String> nickNameList;
    List<Double> splitAmountList;
    LayoutInflater inflater;

    public GroupDialogAdapter(Context context, List<String> nickNameList, List<Double> splitAmountList){
        this.inflater = LayoutInflater.from(context);
        this.nickNameList = nickNameList;
        this.splitAmountList = splitAmountList;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.activity_group_dialog_adapter2, null);

            TextView memberName = (TextView) convertView.findViewById(R.id.memberName);
            EditText editSplitAmount = (EditText) convertView.findViewById(R.id.editSplitAmount);
            TextView dollarsign = (TextView)   convertView.findViewById(R.id.dollarSign_dialog);

            holder = new Holder();

            holder.memberName = memberName;
            holder.editSplitAmount = editSplitAmount;
            holder.dollarsign = dollarsign;

            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();

        }

        System.out.println("POSITION: " + position);
        System.out.println(nickNameList.get(position));


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
                //tmpAmount[holder.ref] = editable.toString();
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
