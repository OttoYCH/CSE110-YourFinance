package teamhardcoder.y_fi;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.astuetz.PagerSlidingTabStrip;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import teamhardcoder.y_fi.database.data.PersonalExpense;
import teamhardcoder.y_fi.database.manager.ManagerFactory;

public class PersonalFinance extends AppCompatActivity {

    private PagerSlidingTabStrip tabs;
    private ArrayList<String> monthList;
    private List<Map.Entry<String, List<PersonalExpense>>> monthlyPersonalExpenseList;
    private PersonalExpenseFragment fragment_expense;
    private PersonalChartFragment fragment_chart;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_finance);

        new PersonalExpenseDownloadTask(getApplicationContext()).execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Finance");
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_personal_finance);

        tabs.setViewPager(mViewPager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.android_action_bar_spinner_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        spinner.setPrompt("Month");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_item, monthList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new onSpinnerItemSelected());

        return true;
    }

    private class onSpinnerItemSelected implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            fragment_expense.setUpListView(monthlyPersonalExpenseList.get(pos).getValue());
        }

        @Override
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"History", "Chart"};
        private List<PersonalExpense> personalExpenseList;

        SectionsPagerAdapter(FragmentManager fm, List<PersonalExpense> personalExpenseList) {
            super(fm);
            this.personalExpenseList = personalExpenseList;
            fragment_expense = PersonalExpenseFragment.newInstance(personalExpenseList);
            fragment_chart = new PersonalChartFragment();
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 1) {
                return fragment_chart;
            } else if (position == 0) {
                return fragment_expense;
            }
            return fragment_expense;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
    }

    private class PersonalExpenseDownloadTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        PersonalExpenseDownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            monthlyPersonalExpenseList = ManagerFactory.getPersonalExpenseManager(context).getMonthlyPersonalExpenseList();
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            monthList = new ArrayList<>();
            for (Map.Entry<String, List<PersonalExpense>> entry : monthlyPersonalExpenseList)
                monthList.add(entry.getKey());
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Finance");
            setSupportActionBar(toolbar);

            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),
                    monthlyPersonalExpenseList.get(0).getValue());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_personal_finance);
            tabs.setViewPager(mViewPager);
        }
    }
}
