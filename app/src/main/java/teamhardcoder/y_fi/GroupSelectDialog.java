package teamhardcoder.y_fi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import teamhardcoder.y_fi.database.data.Group;
import teamhardcoder.y_fi.database.manager.GroupManager;
import teamhardcoder.y_fi.database.manager.ManagerFactory;
import teamhardcoder.y_fi.database.manager.UserManager;

/**
 * Created by otto on 3/8/17.
 */

public class GroupSelectDialog extends DialogFragment {

    ListView lView;
    List<teamhardcoder.y_fi.database.data.Group> groupList = new ArrayList<>();
    List<String> groupNameList = new ArrayList<>();
    List<Double> amountList;
    List<String> nickNameList = new ArrayList<>();
    Spinner groupSpinner;


    public GroupSelectDialog() {

    }

    public static GroupSelectDialog newInstance(String amount) {


        GroupSelectDialog fragment = new GroupSelectDialog();
        Bundle bundle = new Bundle();
        bundle.putString("ScanAmount", amount);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // take out sth

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View content = getActivity().getLayoutInflater().inflate(R.layout.activity_group_dialog_adapter, null);

        groupSpinner = (Spinner) content.findViewById(R.id.group_spinner);
        groupSpinner.setOnItemSelectedListener(new OnSpinnerItemClicked());

        new GroupDownloadTask(getActivity().getApplicationContext()).execute((Void) null);

        builder.setView(content)
                .setTitle("Select Group")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*drinkOrderReceived.setlNumber(largeNumberPicker.getValue());
                        drinkOrderReceived.setmNumber(mediumNumberPicker.getValue());
                        drinkOrderReceived.setIce(getSelectedItemFroRadioGroup(iceRGroup));
                        drinkOrderReceived.setSugar(getSelectedItemFroRadioGroup(sugarRGroup));
                        drinkOrderReceived.setNote(noteEditText.getText().toString());*/

                        /*if (mListener != null) {
                            mListener.onDrinkOrderFinished(drinkOrderReceived);
                            //send out to outside
                        }*/

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
            Toast.makeText(parent.getContext(), "Clicked : " +
                    parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();

            System.out.println(groupSpinner.getSelectedItem().toString());
            System.out.println(groupList.get(groupSpinner.getSelectedItemPosition()).getUserIdSet());
            new userIdToNicknameTask(getActivity().getApplicationContext(),
                    new ArrayList<String>(groupList.get(groupSpinner.getSelectedItemPosition()).getUserIdSet())).execute((Void) null);

        }

        @Override
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    public void setUpListView() {
        lView.setAdapter(new GroupDialogAdapter(getContext(), nickNameList, amountList));
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
            for (Group g:groupList) {
                groupNameList.add(g.getGroupName());
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (groupList != null) {
                groupSpinner.setAdapter(new ArrayAdapter<String>(GroupSelectDialog.this.getActivity(), android.R.layout.simple_spinner_item, groupNameList));
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
            for (String u: userName) {
                nickNameList.add(um.getUserName(u));
            }


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }
    }


}
