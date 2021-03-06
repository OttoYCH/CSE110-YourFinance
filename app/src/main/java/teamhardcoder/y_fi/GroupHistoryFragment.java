package teamhardcoder.y_fi;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import teamhardcoder.y_fi.database.data.GroupExpense;
import teamhardcoder.y_fi.database.manager.GroupExpenseManager;
import teamhardcoder.y_fi.database.manager.ManagerFactory;

public class GroupHistoryFragment extends Fragment {

    private static String groupId;

    ListView lView;
    List<GroupExpense> groupExpenseList;

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
    }

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
