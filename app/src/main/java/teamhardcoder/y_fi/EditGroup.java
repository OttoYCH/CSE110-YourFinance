package teamhardcoder.y_fi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import teamhardcoder.y_fi.database.data.Group;
import teamhardcoder.y_fi.database.manager.GroupManager;
import teamhardcoder.y_fi.database.manager.ManagerFactory;
import teamhardcoder.y_fi.database.manager.UserManager;

public class EditGroup extends AppCompatActivity {

    Group gp;
    EditText groupNameText;
    EditText editFriendIdText;
    Button resetButton;
    Button searchButton;
    Button cancelButton;
    Button submitButton;
    ListView lView;
    GroupMembersAdapter adapter;

    List<String> nickNameList;
    List<String> userIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        getSupportActionBar().setTitle(getIntent().getStringExtra("GroupName"));
        groupNameText = (EditText) findViewById(R.id.editGroupName);
        editFriendIdText = (EditText) findViewById(R.id.editFriendId);
        resetButton = (Button) findViewById(R.id.newGroupNameBtn);
        searchButton = (Button) findViewById(R.id.searchIdBtn);
        cancelButton = (Button) findViewById(R.id.cancelGroupEditBtn);
        submitButton = (Button) findViewById(R.id.submitGroupEditBtn);
        lView = (ListView) findViewById(R.id.listviewMembers);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gp.setGroupName(groupNameText.getText().toString());
                getSupportActionBar().setTitle(gp.getGroupName());
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editFriendIdText.getText().toString();
                if(userIdList.contains(id)){
                    Toast.makeText(getApplicationContext(), "User is already a member.",
                            Toast.LENGTH_LONG).show();
                } else {
                    CheckUserTask task = new CheckUserTask(getApplicationContext(), id);
                    task.execute((Void) null);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done(v);
            }
        });

        GetGroupTask task = new GetGroupTask(getApplicationContext());
        task.execute((Void) null);

    }

    public void done(View view) {
        Intent intent = new Intent();
        intent.putExtra("groupName", gp.getGroupName());
        intent.putExtra("userIdList",userIdList.toArray(new String[0]));
        setResult(RESULT_OK, intent);
        finish();
    }

    public void setUpListView(Set<String> memberSet) {
        adapter = new GroupMembersAdapter(this, userIdList, nickNameList);
        lView.setAdapter(adapter);
    }

    public class CheckUserTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;
        private String id;
        private String userName;

        CheckUserTask(Context context, String id) {
            this.context = context;
            this.id = id;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            UserManager um = ManagerFactory.getUserManager(context);
            if(um.checkExist(this.id)){
                userName = um.getUserName(this.id);
                return true;
            } else{
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success){
                AlertDialog.Builder builder = new AlertDialog.Builder(EditGroup.this);
                builder.setMessage("User ID: " + id + "\nName: " + this.userName
                + "\n\nDo you want to add this user in the group?");

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        nickNameList.add(userName);
                        userIdList.add(id);

                        adapter.notifyDataSetChanged();

                        Toast.makeText(context, "New member added!",
                                Toast.LENGTH_LONG).show();
                    }

                });
                builder.create().show();
            } else{
                Toast.makeText(context, "ID Doesn't Exist!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public class GetGroupTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        GetGroupTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            GroupManager gm = ManagerFactory.getGroupManager(context);
            UserManager um = ManagerFactory.getUserManager(context);
            gp = gm.getGroup(getIntent().getStringExtra("GroupId"));
            userIdList = new ArrayList<>(gp.getUserIdSet());
            nickNameList = new ArrayList<>();
            for(String each: userIdList){
                nickNameList.add(um.getUserName(each));
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            setUpListView(gp.getUserIdSet());
        }
    }
}
