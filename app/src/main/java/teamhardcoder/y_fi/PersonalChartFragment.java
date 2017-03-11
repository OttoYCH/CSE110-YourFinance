package teamhardcoder.y_fi;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PersonalChartFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static String groupId;

    public PersonalChartFragment() {
    }

    public static PersonalChartFragment newInstance(String groupId) {
        PersonalChartFragment fragment = new PersonalChartFragment();
        Bundle args = new Bundle();
        //args.putString("groupId", groupId);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_personal_chart_fragment, container, false);


        return rootView;
    }

}