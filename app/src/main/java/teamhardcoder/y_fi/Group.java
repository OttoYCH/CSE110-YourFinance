package teamhardcoder.y_fi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import teamhardcoder.y_fi.database.manager.GroupManager;
import teamhardcoder.y_fi.database.manager.ManagerFactory;

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

        GroupDownloadTask task = new GroupDownloadTask(getApplicationContext());
        task.execute((Void) null);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                teamhardcoder.y_fi.database.data.Group gp = (teamhardcoder.y_fi.database.data.Group) adapterView.getAdapter().getItem(i);
                System.out.println(gp);

                Intent intent = new Intent();
                intent.setClass(Group.this, GroupBoard.class);
                intent.putExtra("GroupId", gp.getGroupId());
                intent.putExtra("GroupName", gp.getGroupName());
                startActivity(intent);

            }
        });

        lView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final teamhardcoder.y_fi.database.data.Group gp = (teamhardcoder.y_fi.database.data.Group) adapterView.getAdapter().getItem(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(Group.this);
                builder.setTitle("WARNING");
                builder.setMessage("Do you want to leave "+gp.getGroupName() +"?");

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("Leave", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        LeaveGroupTask task = new LeaveGroupTask(getApplicationContext(), gp);
                        task.execute((Void) null);

                        groupList.remove(gp);
                        setUpListView(groupList);
                    }
                });
                builder.create().show();


                return true;
            }});


    }

    public void setUpListView(List<teamhardcoder.y_fi.database.data.Group> groupList) {
        lView.setAdapter(new GroupAdapter(this, groupList));
    }

    public class GroupDownloadTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        GroupDownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            GroupManager gm = ManagerFactory.getGroupManager(context);
            groupList = gm.getAllGroupsOfUser(ManagerFactory.getUserManager(context).getUser().getUserId());
            groupList = new ArrayList<>(groupList);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            setUpListView(groupList);
        }
    }

    public class LeaveGroupTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;
        private teamhardcoder.y_fi.database.data.Group gp;

        LeaveGroupTask(Context context, teamhardcoder.y_fi.database.data.Group gp) {
            this.context = context;
            this.gp = gp;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            gp.getUserIdSet().remove(ManagerFactory.getUserManager(this.context).getUser().getUserId());
            if(gp.getUserIdSet().size() == 0){
                ManagerFactory.getGroupManager(context).deleteGroup(gp.getGroupId());
            }else {
                ManagerFactory.getGroupManager(this.context).editGroup(gp);
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }
    }
}
