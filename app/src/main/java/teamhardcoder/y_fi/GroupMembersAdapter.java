package teamhardcoder.y_fi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import teamhardcoder.y_fi.database.data.*;

    public class GroupMembersAdapter extends BaseAdapter {

        List<String> userIdList;
        List<String> nickNameList;
        LayoutInflater inflater;

        public GroupMembersAdapter(Context context, List<String> userIdList, List<String> nickNameList){
            // context is Activity
            this.userIdList = userIdList;
            this.nickNameList = nickNameList;
            this.inflater = LayoutInflater.from(context);

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
                convertView = inflater.inflate(R.layout.activity_group_members_adapter, null);

                TextView textViewNickName = (TextView) convertView.findViewById(R.id.textViewNickName);
                TextView textViewUserId = (TextView) convertView.findViewById(R.id.textViewUserId);
                Button removeBtn = (Button) convertView.findViewById(R.id.buttonRmove);

                holder = new Holder();

                holder.textViewNickName = textViewNickName;
                holder.textViewUserId = textViewUserId;
                holder.removeBtn = removeBtn;

                convertView.setTag(holder);

            } else {
                holder = (Holder) convertView.getTag();

            }

            holder.textViewUserId.setText(userIdList.get(position));
            holder.textViewNickName.setText(nickNameList.get(position));
            holder.removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userIdList.remove(position);
                    nickNameList.remove(position);
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }

        public class Holder{
            TextView textViewNickName;
            TextView textViewUserId;
            Button removeBtn;

        }
    }

