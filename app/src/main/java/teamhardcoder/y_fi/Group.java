package teamhardcoder.y_fi;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import teamhardcoder.y_fi.database.manager.GroupManager;
import teamhardcoder.y_fi.database.manager.ManagerFactory;
import teamhardcoder.y_fi.database.manager.UserManager;

public class Group extends AppCompatActivity {

    ListView lView;
    List<teamhardcoder.y_fi.database.data.Group> groupList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(teamhardcoder.y_fi.R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(teamhardcoder.y_fi.R.id.toolbar);
        setSupportActionBar(toolbar);
        lView = (ListView) findViewById(R.id.groupListView);

        GroupDownloadTask task = new GroupDownloadTask(getApplicationContext(), groupList);
        task.execute((Void) null);

        //setUpListView();

    }


    public void setUpListView(List<teamhardcoder.y_fi.database.data.Group> groupList) {
        lView.setAdapter(new GroupAdapter(this, groupList));
    }


    public class GroupDownloadTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;
        private List<teamhardcoder.y_fi.database.data.Group> groupList;

        GroupDownloadTask(Context context, List<teamhardcoder.y_fi.database.data.Group> groupList) {
            this.groupList = groupList;
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            GroupManager gm = ManagerFactory.getGroupManager(context);
            groupList = gm.getAllGroupsOfUser(ManagerFactory.getUserManager(context).getUser().getUserId());

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            setUpListView(groupList);
        }

        @Override
        protected void onCancelled() {

        }
    }

}
