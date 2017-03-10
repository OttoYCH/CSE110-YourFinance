package teamhardcoder.y_fi;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

import teamhardcoder.y_fi.database.data.User;
import teamhardcoder.y_fi.database.manager.ManagerFactory;
import teamhardcoder.y_fi.database.manager.UserManager;

import static android.R.id.list;

/**
 * Created by jessicawu on 3/6/17.
 */

public class GroupCreateFrag extends Fragment {
    private static String friendId;

    ExpandableListView expandableListView;
    List<User> friendList;
    Button createGroupBtn;

    public static GroupCreateFrag newInstance(String userId) {
        GroupCreateFrag frag = new GroupCreateFrag();
        Bundle argument = new Bundle();
        argument.putString("friendId", userId);
        frag.setArguments(argument);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View backView = inflater.inflate(R.layout.activity_group_create_frag, container, false);
        friendId = getArguments().getString("friendId");
        expandableListView = (ListView) backView.findViewById(R.id.groupFriendListView);

        createGroupBtn = (Button) backView.findViewById(R.id.create_group_btn);
        createGroupBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add Friend");
                builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ){
                        friendId = ManagerFactory.getUserManager(getContext()).getUser().getUserId();
                        //add friend to the list
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ){
                        dialog.cancel();
                    }
                });

                dialog = builder.create();
            }
        });

        GroupCreateTask task = new GroupCreateTask(getContext());
        task.execute();

        return backView;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater minflater = new MenuInflater(getActivity().getApplicationContext());
        minflater.inflate(R.menu.group_friend_search, menu);

/*
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
*/
        return true;
    }


    public void setListView(){
        GroupCreateAdapter adapter = new GroupCreateAdapter(getContext(), friendList);
        expandableListView.setAdapter(adapter);
        expandableListView.setSelection(adapter.getCount()-1);
        expandableListView.setDivider(null);
        expandableListView.setDividerHeight(0);
    }

    public class GroupCreateTask extends AsyncTask<String, Void, Boolean> {
        private Context context;
        GroupCreateTask(Context context){ this.context = context; }

        @Override
        protected Boolean doInBackground(String... arg) {

            //friendId = ManagerFactory.getUserManager(getContext()).getUser().getUserId();
            UserManager gfm = ManagerFactory.getUserManager(context);
            friendList = gfm.getUser();
            return true;
        }

        @Override
        protected void onPostExecute( final Boolean success){
            setListView();
        }

        @Override
        protected void onCancelled(){

        }

    }




}
