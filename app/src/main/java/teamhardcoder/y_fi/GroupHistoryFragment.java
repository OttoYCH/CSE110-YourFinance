package teamhardcoder.y_fi;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GroupHistoryFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public GroupHistoryFragment() {
    }

    /*
    public static GroupHistoryFragment newInstance(String groupId) {
        GroupHistoryFragment fragment = new GroupHistoryFragment();
        Bundle args = new Bundle();
        args.putString("", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    */




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_group_history_fragment, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.textViewTest);
        textView.setText("Sucesss Test, Frag 2");
        return rootView;
    }
}
