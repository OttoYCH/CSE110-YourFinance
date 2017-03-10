package teamhardcoder.y_fi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import teamhardcoder.y_fi.database.manager.GroupManager;
import teamhardcoder.y_fi.database.manager.ManagerFactory;
import teamhardcoder.y_fi.database.manager.UserManager;

public class NewGroup extends AppCompatActivity {


    teamhardcoder.y_fi.database.data.Group gp;
    EditText newGroupNameText;
    EditText newFriendIdText;
    Button searchNewIdBtn;
    Button cancelNewGroupEditBtn;
    Button createNewGroupEditBtn;
    ListView lView;

    GroupMembersAdapter adapter;

    List<String> nickNameList;
    List<String> userIdList;
    Boolean setMyself = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        newGroupNameText = (EditText) findViewById(R.id.newGroupName);
        newFriendIdText = (EditText) findViewById(R.id.newFriendId);
        searchNewIdBtn = (Button) findViewById(R.id.searchNewIdBtn);
        cancelNewGroupEditBtn = (Button) findViewById(R.id.cancelNewGroupEditBtn);
        createNewGroupEditBtn = (Button) findViewById(R.id.createNewGroupEditBtn);
        lView = (ListView) findViewById(R.id.listviewNewGroupMembers);
        nickNameList = new ArrayList<>();
        userIdList = new ArrayList<>();

        CheckUserTask task = new CheckUserTask(getApplicationContext(), ManagerFactory.getUserManager(getApplicationContext()).getUser().getUserId());
        task.execute((Void) null);


        searchNewIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchNewIdBtn.setEnabled(false);
                String id = newFriendIdText.getText().toString();
                if(id.length() == 0){
                    Toast.makeText(getApplicationContext(), "Please fill in ID",
                            Toast.LENGTH_SHORT).show();
                    searchNewIdBtn.setEnabled(true);
                } else if(userIdList.contains(id)){
                    Toast.makeText(getApplicationContext(), "User is already a member.",
                            Toast.LENGTH_SHORT).show();
                    searchNewIdBtn.setEnabled(true);
                } else {
                    searchNewIdBtn.setEnabled(false);
                    CheckUserTask task = new CheckUserTask(getApplicationContext(), id);
                    task.execute((Void) null);
                }
            }
        });

        cancelNewGroupEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createNewGroupEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userIdList.contains(ManagerFactory.getUserManager(getApplicationContext()).getUser().getUserId())){
                    Toast.makeText(getApplicationContext(), "You are not in the group.",
                            Toast.LENGTH_SHORT).show();
                } else if(newGroupNameText.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "Group name cannot be emptry.",
                            Toast.LENGTH_SHORT).show();
                } else if (userIdList.size() == 0){
                    Toast.makeText(getApplicationContext(), "No member in the group",
                            Toast.LENGTH_SHORT).show();
                } else {
                    done(v);
                }
            }
        });

        setUpListView();

    }

    public void done(View view) {
        Intent intent = new Intent();
        intent.putExtra("groupName", newGroupNameText.getText().toString());
        intent.putExtra("userIdList",userIdList.toArray(new String[0]));
        setResult(RESULT_OK, intent);
        finish();
    }

    public void setUpListView() {
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
            searchNewIdBtn.setEnabled(true);

            if(!setMyself) {
                if (success) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(NewGroup.this);
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

                            newFriendIdText.setText("");

                            Toast.makeText(context, "New member added!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    });
                    builder.create().show();
                } else {
                    Toast.makeText(context, "ID Doesn't Exist!",
                            Toast.LENGTH_SHORT).show();
                }
            } else{
                nickNameList.add(userName);
                userIdList.add(id);

                adapter.notifyDataSetChanged();

                setMyself = false;
            }
        }
    }

}
