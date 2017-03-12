package teamhardcoder.y_fi;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

import teamhardcoder.y_fi.database.data.*;
import teamhardcoder.y_fi.database.manager.GroupExpenseManager;
import teamhardcoder.y_fi.database.manager.ManagerFactory;

/**
 * Created by jessicawu on 3/11/17.
 */

public class EditGroupExpense2 extends AppCompatActivity {

    GroupExpense ge;
    EditText groupExpenseName;
    EditText groupExpenseAmount;
    Button saveButton;
    Button cancelButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_expense2);
        getSupportActionBar().setTitle(getIntent().getStringExtra("GroupExpense"));

        groupExpenseName = (EditText) findViewById(R.id.newGroupExpenseName);
        groupExpenseAmount = (EditText) findViewById(R.id.newGroupExpenseAmount);
        saveButton = (Button) findViewById(R.id.saveButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        //load old expense
        LoadExpenseTask task = new LoadExpenseTask(getApplicationContext());
        task.execute((Void)null);


        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v ){
                String name = groupExpenseName.getText().toString();
                String amount = groupExpenseAmount.getText().toString();

                if (name.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please rename the expense",
                            Toast.LENGTH_SHORT).show();
                } else if (amount.length() == 0){
                    Toast.makeText(getApplicationContext(), "Please edit the amount",
                            Toast.LENGTH_SHORT).show();
                } else {

                    ge.setDescription(name);
                    ge.setAmount(Double.valueOf(amount));

                    buttonSaveDone(v);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void buttonSaveDone(View view){

        FinishExpenseTask task = new FinishExpenseTask(getApplicationContext());
        task.execute((Void)null);

        finish();
    }

    public class FinishExpenseTask extends AsyncTask<Void, Void, Boolean> {
        private Context context;

        FinishExpenseTask(Context context){ this.context = context; }

        @Override
        protected Boolean doInBackground( Void... params ) {
            GroupExpenseManager gem = ManagerFactory.getGroupExpenseManager(context);
            gem.editExpense(ge);

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }
    }

    //Load group expense from previous page
    public class LoadExpenseTask extends AsyncTask<Void, Void, Boolean> {
        private Context context;

        LoadExpenseTask(Context context){
            this.context = context;
        }

        @Override
        protected Boolean doInBackground( Void... params ) {
            GroupExpenseManager gem = ManagerFactory.getGroupExpenseManager(context);
            ge = gem.getExpense(getIntent().getStringExtra("GroupExpenseId"));
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            groupExpenseName.setText(ge.getDescription());
            groupExpenseAmount.setText(Double.toString(ge.getAmount()));

        }

    }
}
