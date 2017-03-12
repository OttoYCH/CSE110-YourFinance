package teamhardcoder.y_fi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import teamhardcoder.y_fi.database.data.PersonalExpense;

public class PersonalExpenseFragment extends Fragment {

    private ListView lView;
    static List<PersonalExpense> personalExpenseListLocal;

    public PersonalExpenseFragment() {
    }

    public static PersonalExpenseFragment newInstance(List<PersonalExpense> personalExpenseList) {
        personalExpenseListLocal = personalExpenseList;
        return new PersonalExpenseFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_personal_expense_fragment, container, false);
        lView = (ListView) rootView.findViewById(R.id.personalExpenseListView);
        setUpListView(personalExpenseListLocal);
        return rootView;
    }

    public void setUpListView(List<PersonalExpense> personalExpenseList) {
        personalExpenseListLocal = personalExpenseList;
        lView.setAdapter(new PersonalExpenseAdapter(getContext(), personalExpenseListLocal));
    }
}
