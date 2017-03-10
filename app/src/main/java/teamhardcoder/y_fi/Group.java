package teamhardcoder.y_fi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import teamhardcoder.y_fi.database.manager.GroupManager;
import teamhardcoder.y_fi.database.manager.ManagerFactory;

public class Group extends AppCompatActivity {

    final static int REQUEST_CODE_EDIT_GROUP = 5;
    final static int REQUEST_CODE_CREATE_GROUP = 15;
    ListView lView;
    List<teamhardcoder.y_fi.database.data.Group> groupList = new ArrayList<>();
    GroupAdapter adapter;
    teamhardcoder.y_fi.database.data.Group gp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(teamhardcoder.y_fi.R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(teamhardcoder.y_fi.R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Group.this, NewGroup.class);
                startActivityForResult(intent, REQUEST_CODE_CREATE_GROUP);
            }
        });

        lView = (ListView) findViewById(R.id.groupListView);

        GroupDownloadTask task = new GroupDownloadTask(getApplicationContext());
        task.execute((Void) null);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                gp = (teamhardcoder.y_fi.database.data.Group) adapterView.getAdapter().getItem(i);

                Intent intent = new Intent();
                intent.setClass(Group.this, GroupBoard.class);
                intent.putExtra("GroupId", gp.getGroupId());
                intent.putExtra("GroupName", gp.getGroupName());
                intent.putExtra("numMembers",Integer.toString(gp.getUserIdSet().size()));


                startActivityForResult(intent, REQUEST_CODE_EDIT_GROUP);


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
        adapter = new GroupAdapter(this, groupList);
        lView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_EDIT_GROUP) {
            if (resultCode == RESULT_OK) {

                String[] res = data.getStringArrayExtra("userIdList");

                Set<String> newSet = new HashSet<>(Arrays.asList(res));
                gp.setGroupName(data.getStringExtra("groupName"));
                gp.setUserIdSet(newSet);
                if (newSet.size() == 0) {
                    LeaveGroupTask task = new LeaveGroupTask(getApplicationContext(), gp);
                    task.execute((Void) null);
                } else {
                    UpdateGroupTask task = new UpdateGroupTask(getApplicationContext());
                    task.execute((Void) null);
                }
                if (!newSet.contains(ManagerFactory.getUserManager(getApplicationContext()).getUser().getUserId())) {
                    groupList.remove(gp);
                    setUpListView(groupList);
                }

            }
        } else if(requestCode == REQUEST_CODE_CREATE_GROUP) {
            if (resultCode == RESULT_OK) {
                String[] res = data.getStringArrayExtra("userIdList");
                Set<String> newSet = new HashSet<>(Arrays.asList(res));
                teamhardcoder.y_fi.database.data.Group gpNew = new teamhardcoder.y_fi.database.data.Group();
                gpNew.setGroupName(data.getStringExtra("groupName"));
                gpNew.setUserIdSet(newSet);
                CreateGroupTask task = new CreateGroupTask(getApplicationContext(),gpNew);
                task.execute((Void) null);

            }
        }
    }

    public class CreateGroupTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;
        private teamhardcoder.y_fi.database.data.Group gp;

        CreateGroupTask(Context context, teamhardcoder.y_fi.database.data.Group gp) {
            this.context = context;
            this.gp = gp;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            GroupManager gm = ManagerFactory.getGroupManager(context);
            return gm.createGroup(gp);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            groupList.add(gp);
            setUpListView(groupList);
        }
    }


    public class UpdateGroupTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        UpdateGroupTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            GroupManager gm = ManagerFactory.getGroupManager(context);
            return gm.editGroup(gp);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            setUpListView(groupList);
        }
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
