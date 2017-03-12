package teamhardcoder.y_fi;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
<<<<<<< HEAD

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import teamhardcoder.y_fi.database.data.PersonalExpense;
import teamhardcoder.y_fi.database.manager.GroupManager;
import teamhardcoder.y_fi.database.manager.ManagerFactory;
import teamhardcoder.y_fi.database.manager.PersonalExpenseManager;

public class Dashboard extends AppCompatActivity {


    final static int REQUEST_CODE_GROUP = 10;
    final static int REQUEST_CODE_SCAN = 100;
    final static int REQUEST_CODE_FINANCE = 1000;
    final static int REQUEST_CODE_SETTING = 10000;


    List<Map.Entry<String, List<PersonalExpense>>> monthlyPersoanlExpenseList;
    TextView textAmount;

=======

public class Dashboard extends AppCompatActivity {

    TextView totalMonth;
>>>>>>> 62513611391a6cfc307bce98b65bb9841ebd0f26

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(teamhardcoder.y_fi.R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(teamhardcoder.y_fi.R.id.toolbar);
        toolbar.setTitle("Your Finacne");
        setSupportActionBar(toolbar);

<<<<<<< HEAD
        textAmount = (TextView) findViewById(R.id.textAmount);

        GetMonthlyPersonalExpenseListTask task = new GetMonthlyPersonalExpenseListTask(getApplicationContext());
        task.execute((Void) null);

=======

        //UPDATE TOTAL VALUES HERE
        totalMonth = (TextView) findViewById(R.id.TotalSpentVar);
        totalMonth.setText("$ SET TOTAL HERE");
>>>>>>> 62513611391a6cfc307bce98b65bb9841ebd0f26
    }

    public void toScan(View view)
    {
        Intent intent = new Intent(this, ScannerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);

    }

    public void toFinance(View view)
    {
        Intent intent = new Intent(this, PersonalFinance.class);
        startActivityForResult(intent, REQUEST_CODE_FINANCE);
    }

    public void toGroup(View view)
    {
        Intent intent = new Intent(this, Group.class);
        startActivityForResult(intent, REQUEST_CODE_GROUP);

    }

    public void toSettings(View view)
    {
        Intent intent = new Intent(this, Settings.class);
        startActivityForResult(intent, REQUEST_CODE_SETTING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        GetMonthlyPersonalExpenseListTask task = new GetMonthlyPersonalExpenseListTask(getApplicationContext());
        task.execute((Void) null);

    }

    public class GetMonthlyPersonalExpenseListTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        GetMonthlyPersonalExpenseListTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            PersonalExpenseManager pem = ManagerFactory.getPersonalExpenseManager(context);
            monthlyPersoanlExpenseList = pem.getMonthlyPersonalExpenseList();

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            Calendar cur_cal = Calendar.getInstance();
            Date dt = cur_cal.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String curMonth = dateFormat.format(dt).substring(0,7);
            if(monthlyPersoanlExpenseList.size()!= 0 && monthlyPersoanlExpenseList.get(0).getKey().equals(curMonth)){
                double sum = 0;
                for(PersonalExpense each: monthlyPersoanlExpenseList.get(0).getValue()){
                    sum += each.getAmount();
                }
                textAmount.setText("$ " + sum);
            } else{
                textAmount.setText("$ " + 0);
            }
        }
    }

}
