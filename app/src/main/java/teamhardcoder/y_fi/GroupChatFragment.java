package teamhardcoder.y_fi;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import teamhardcoder.y_fi.database.data.Message;
import teamhardcoder.y_fi.database.data.User;
import teamhardcoder.y_fi.database.manager.ManagerFactory;
import teamhardcoder.y_fi.database.manager.MessageManager;

public class GroupChatFragment extends Fragment {

    private static String groupId;

    ListView lView;
    List<Message> groupChatList;
    Button mSubmitMsgButton;
    EditText message_editText;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public GroupChatFragment() {
    }

    public static GroupChatFragment newInstance(String groupId) {
        GroupChatFragment fragment = new GroupChatFragment();
        Bundle args = new Bundle();
        args.putString("groupId", groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_group_chat_fragment, container, false);
        groupId = getArguments().getString("groupId");
        lView = (ListView) rootView.findViewById(R.id.groupChatListView);

        new GroupChatDownloadTask(getContext()).execute();

        message_editText = (EditText) rootView.findViewById(R.id.message_editText);
        mSubmitMsgButton = (Button) rootView.findViewById(R.id.submit_msg_btn);
        mSubmitMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = message_editText.getText().toString();
                if (!msg.equals("")) {
                    mSubmitMsgButton.setEnabled(false);
                    new SendGroupChatTask(getContext()).execute(msg);
                } else {
                    message_editText.setError("Say something!");
                }
            }
        });

        return rootView;
    }


    public void setUpListView() {
        GroupChatAdapter apt = new GroupChatAdapter(getContext(), groupChatList);
        lView.setAdapter(apt);
        lView.setSelection(apt.getCount()-1);
        lView.setDivider(null);
        lView.setDividerHeight(0);

    }

    public void sortListViewByTime() {
        groupChatList = new ArrayList<>(groupChatList);
        Collections.sort(groupChatList, new Comparator<Message>() {
            @Override
            public int compare(Message lhs, Message rhs) {
                return lhs.getCreatedDate().compareTo(rhs.getCreatedDate());
            }
        });
    }

    class GroupChatDownloadTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        GroupChatDownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            MessageManager mm = ManagerFactory.getMessageManager(context);
            groupChatList = mm.getGroupMessage(groupId);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            sortListViewByTime();
            setUpListView();
        }
    }

    class SendGroupChatTask extends AsyncTask<String, Void, Boolean> {

        private Context context;

        SendGroupChatTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            MessageManager mm = ManagerFactory.getMessageManager(context);
            mm.sendMessage(new Message(groupId,
                    ManagerFactory.getUserManager(context).getUser().getNickname(),params[0]));
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            message_editText.setText("");
            new GroupChatDownloadTask(context).execute();
            mSubmitMsgButton.setEnabled(true);
        }
    }
}
