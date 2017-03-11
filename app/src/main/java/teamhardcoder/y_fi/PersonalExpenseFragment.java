package teamhardcoder.y_fi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import teamhardcoder.y_fi.database.data.PersonalExpense;
import teamhardcoder.y_fi.database.manager.ManagerFactory;

public class PersonalExpenseFragment extends Fragment {

    String userID;
    ListView lView;
    List<PersonalExpense> personalExpenseList;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PersonalExpenseFragment() {
    }

    public static PersonalExpenseFragment newInstance(String groupId) {
        PersonalExpenseFragment fragment = new PersonalExpenseFragment();
        Bundle args = new Bundle();
        //args.putString("groupId", groupId);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_personal_expense_fragment, container, false);
        userID = ManagerFactory.getUserManager(getContext()).getUser().getUserId();
        lView = (ListView) rootView.findViewById(R.id.personalExpenseListView);
        setUpListView();
        return rootView;
    }

    public void setUpListView() {
        lView.setAdapter(new PersonalExpenseAdapter(getContext(), personalExpenseList));
    }
}
