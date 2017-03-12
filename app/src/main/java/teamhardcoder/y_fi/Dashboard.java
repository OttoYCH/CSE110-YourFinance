package teamhardcoder.y_fi;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import teamhardcoder.y_fi.database.data.PersonalExpense;
import teamhardcoder.y_fi.database.manager.ManagerFactory;
import teamhardcoder.y_fi.database.manager.PersonalExpenseManager;

public class Dashboard extends AppCompatActivity {


    final static int REQUEST_CODE_GROUP = 10;
    final static int REQUEST_CODE_SCAN = 100;
    final static int REQUEST_CODE_FINANCE = 1000;
    final static int REQUEST_CODE_SETTING = 10000;

    List<Map.Entry<String, List<PersonalExpense>>> monthlyPersonalExpenseList;
    TextView textAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(teamhardcoder.y_fi.R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(teamhardcoder.y_fi.R.id.toolbar);
        toolbar.setTitle("Your Finance");
        setSupportActionBar(toolbar);

        textAmount = (TextView) findViewById(R.id.textAmount);

        GetMonthlyPersonalExpenseListTask task = new GetMonthlyPersonalExpenseListTask(getApplicationContext());
        task.execute((Void) null);
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
            monthlyPersonalExpenseList = pem.getMonthlyPersonalExpenseList();

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            Calendar cur_cal = Calendar.getInstance();
            Date dt = cur_cal.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String curMonth = dateFormat.format(dt).substring(0,7);
            if (monthlyPersonalExpenseList.size() != 0 && monthlyPersonalExpenseList.get(0).getKey().equals(curMonth)) {
                double sum = 0;
                for (PersonalExpense each : monthlyPersonalExpenseList.get(0).getValue()) {
                    sum += each.getAmount();
                }
                textAmount.setText("$ " + String.format("%.2f", sum));
            } else{
                textAmount.setText("$ " + 0);
            }
        }
    }

}
