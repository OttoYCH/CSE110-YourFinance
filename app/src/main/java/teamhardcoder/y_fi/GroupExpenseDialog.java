package teamhardcoder.y_fi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import teamhardcoder.y_fi.database.data.*;
import teamhardcoder.y_fi.database.manager.GroupExpenseManager;
import teamhardcoder.y_fi.database.manager.GroupManager;
import teamhardcoder.y_fi.database.manager.ManagerFactory;
import teamhardcoder.y_fi.database.manager.PersonalExpenseManager;
import teamhardcoder.y_fi.database.manager.UserManager;


    public class GroupExpenseDialog extends DialogFragment {

        ListView lView;
        List<teamhardcoder.y_fi.database.data.Group> groupList = new ArrayList<>();
        List<String> groupNameList = new ArrayList<>();
        List<Double> amountList;
        List<String> userIdList;
        List<String> nickNameList;
        Spinner groupSpinner;
        GroupDialogAdapter adapter;
        String description;
        String category;
        String groupIdSelected;
        double amount;


        public GroupExpenseDialog() {

        }

        public static GroupExpenseDialog newInstance(String amount, String message, String category) {
            GroupExpenseDialog fragment = new GroupExpenseDialog();
            Bundle bundle = new Bundle();
            bundle.putString("ScanAmount", amount);
            bundle.putString("Description", message);
            bundle.putString("Category", category);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            if (getArguments() != null) {
                amount = Double.valueOf(getArguments().getString("ScanAmount"));
                description = getArguments().getString("Description");
                category = getArguments().getString("Category");

            }


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View content = getActivity().getLayoutInflater().inflate(R.layout.activity_group_expense_dialog, null);
            lView = (ListView) content.findViewById(R.id.dialog_listview);
            groupSpinner = (Spinner) content.findViewById(R.id.group_spinner);
            groupSpinner.setOnItemSelectedListener(new OnSpinnerItemClicked());


            new GroupDownloadTask(getActivity().getApplicationContext()).execute((Void) null);

            builder.setView(content)
                    .setTitle("Select Group")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // create expense
                            // call Async createGroup expense

                            new createGroupExpenseTask(getActivity().getApplicationContext()).execute((Void) null);
                            new splitExpenseTask(getActivity().getApplicationContext()).execute((Void) null);
                            GroupExpenseDialog.this.dismiss();
                            getActivity().finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            return builder.create();
        }

        public class OnSpinnerItemClicked implements AdapterView.OnItemSelectedListener {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                /*Toast.makeText(parent.getContext(), "Clicked : " +
                        parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
                */
                groupIdSelected = groupList.get(groupSpinner.getSelectedItemPosition()).getGroupId();

                userIdList = new ArrayList<String>(groupList.get(groupSpinner.getSelectedItemPosition()).getUserIdSet());
                new userIdToNicknameTask(getActivity().getApplicationContext(),
                        new ArrayList<String>(groupList.get(groupSpinner.getSelectedItemPosition()).getUserIdSet())).execute((Void) null);


            }

            @Override
            public void onNothingSelected(AdapterView parent) {
                // Do nothing.
            }
        }

        public void setUpListView() {
            adapter = new GroupDialogAdapter(getActivity().getBaseContext(), nickNameList, amountList, userIdList);
            lView.setAdapter(adapter);
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
                for (teamhardcoder.y_fi.database.data.Group g:groupList) {
                    groupNameList.add(g.getGroupName());
                }
                return true;
            }

            @Override
            protected void onPostExecute(final Boolean success) {
                if (groupList != null) {
                    groupSpinner.setAdapter(new ArrayAdapter<String>(GroupExpenseDialog.this.getActivity(), android.R.layout.simple_spinner_item, groupNameList));
                }
            }
        }

        public class userIdToNicknameTask extends AsyncTask<Void, Void, Boolean> {

            private Context context;
            private  List<String> userName;

            userIdToNicknameTask(Context context, List<String> userIdList) {
                this.context = context;
                this.userName = userIdList;

            }

            @Override
            protected Boolean doInBackground(Void... params) {
                UserManager um = ManagerFactory.getUserManager(context);
                nickNameList = new ArrayList<>();

                for (String u: userName) {
                    nickNameList.add(um.getUserName(u));
                }

                return true;
            }

            @Override
            protected void onPostExecute(final Boolean success) {
                double avgAmount = amount / nickNameList.size();
                amountList = new ArrayList<>(nickNameList.size());
                for (int i = 0; i < nickNameList.size(); ++i) {
                    amountList.add(avgAmount);
                }
                setUpListView();
            }
        }

        class createGroupExpenseTask extends AsyncTask<Void, Void, Boolean> {

            private Context context;

            createGroupExpenseTask(Context context) {
                this.context = context;
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                GroupExpenseManager gem = ManagerFactory.getGroupExpenseManager(context);
                gem.createExpense(new GroupExpense(groupIdSelected, amount, description, category));

                return true;
            }

            @Override
            protected void onPostExecute(final Boolean success) {

            }
        }

        class splitExpenseTask extends AsyncTask<Void, Void, Boolean> {

            private Context context;

            splitExpenseTask(Context context) {
                this.context = context;
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                PersonalExpenseManager pem = ManagerFactory.getPersonalExpenseManager(context);
                for(int i = 0; i < userIdList.size(); ++i) {
                    pem.createExpense(new PersonalExpense(userIdList.get(i), amountList.get(i), description, category));
                }
                return true;
            }

            @Override
            protected void onPostExecute(final Boolean success) {

            }
        }

    }
