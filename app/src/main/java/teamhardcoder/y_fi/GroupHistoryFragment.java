package teamhardcoder.y_fi;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

<<<<<<< HEAD

import teamhardcoder.y_fi.database.data.*;
import teamhardcoder.y_fi.database.data.Group;

import teamhardcoder.y_fi.database.data.GroupExpense;

=======
import teamhardcoder.y_fi.database.data.GroupExpense;
>>>>>>> a8c2978778e8c381aec5ef37d1e2e1f6361f181f
import teamhardcoder.y_fi.database.manager.GroupExpenseManager;
import teamhardcoder.y_fi.database.manager.ManagerFactory;


public class GroupHistoryFragment extends Fragment {

    private static String groupId;
    private static final int REQUEST_CODE_EDIT_EXPENSE = 100;

    ListView lView;
    List<GroupExpense> groupExpenseList;
    GroupExpense ge;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public GroupHistoryFragment() {
    }

    public static GroupHistoryFragment newInstance(String groupId) {
        GroupHistoryFragment fragment = new GroupHistoryFragment();
        Bundle args = new Bundle();
        args.putString("groupId", groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_group_history_fragment, container, false);
        groupId = getArguments().getString("groupId");
        lView = (ListView) rootView.findViewById(R.id.groupHistListView);

        GroupExpenseDownloadTask task = new GroupExpenseDownloadTask(getContext());
        task.execute((Void) null);

        //The method to go to the editGroupExpense page
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                ge = (GroupExpense) adapterView.getAdapter().getItem(i);

                Intent intent = new Intent();
                intent.setClass(getContext(), EditGroupExpense2.class);
                intent.putExtra("GroupExpenseId", ge.getExpenseId());
                //intent.putExtra("GroupExpenseAmount", ge.getAmount());
                startActivityForResult(intent, REQUEST_CODE_EDIT_EXPENSE);


            }
        });

        return rootView;
    }

    public void setUpListView() {
        lView.setAdapter(new GroupHistoryAdapter(getContext(), groupExpenseList));
    }

    public void sortListViewByTime() {
        groupExpenseList = new ArrayList<>(groupExpenseList);
        Collections.sort(groupExpenseList, new Comparator<GroupExpense>() {
            @Override
            public int compare(GroupExpense lhs, GroupExpense rhs) {
                return rhs.getCreatedDate().compareTo(lhs.getCreatedDate());
            }
        });
<<<<<<< HEAD
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_EDIT_EXPENSE){
            GroupExpenseDownloadTask task = new GroupExpenseDownloadTask(getContext());
            task.execute((Void) null);
        }
    }


=======
    }

>>>>>>> a8c2978778e8c381aec5ef37d1e2e1f6361f181f
    public class GroupExpenseDownloadTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        GroupExpenseDownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            GroupExpenseManager gem = ManagerFactory.getGroupExpenseManager(context);
            groupExpenseList = gem.getGroupExpense(groupId);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            sortListViewByTime();
            setUpListView();
        }
    }
}
