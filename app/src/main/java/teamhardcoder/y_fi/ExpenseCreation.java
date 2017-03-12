package teamhardcoder.y_fi;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import teamhardcoder.y_fi.database.data.*;
import teamhardcoder.y_fi.database.data.Group;
import teamhardcoder.y_fi.database.manager.ManagerFactory;
import teamhardcoder.y_fi.database.manager.PersonalExpenseManager;
import teamhardcoder.y_fi.database.manager.UserManager;

public class ExpenseCreation extends AppCompatActivity implements OnItemSelectedListener {

    final static int REQUEST_CODE_GROUP_EXPENSE_CREATION = 87;
    Set<String> categoryList;
    //List<String> userCategoryList;
    //Spinner spinner;
    AutoCompleteTextView categoryView;
    TextView amountBox;
    EditText message;
    double amount;
    String description;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        amount = intent.getDoubleExtra("totalAmount", 0.0);


        amountBox = (TextView) findViewById(R.id.amount);
        amountBox.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60);
        amountBox.setText(Double.toString(amount));

        // Category Spinner
        //spinner = (Spinner) findViewById(R.id.category_spinner);
        categoryView = (AutoCompleteTextView) findViewById(R.id.category_field);

        // Spinner click listener
        //spinner.setOnItemSelectedListener(this);
        categoryView.setOnItemSelectedListener(this);
        new CategoryListLoadTask(getApplicationContext()).execute((Void) null);

        message = (EditText) findViewById(R.id.message);


        ImageButton imgPersonalExpenseBtn = (ImageButton) findViewById(R.id.personal_expense);
        imgPersonalExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // directly create personal expense
                amount = Double.valueOf(amountBox.getText().toString());
                description = message.getText().toString();
                category = categoryView.getText().toString();
                if (!category.equals("")) {
                    // create new category for user if not in the origin category list
                    if (!categoryList.contains(category)) {
                        categoryList.add(category);
                        new updateUserCategoryTask(getApplicationContext(), categoryList).execute((Void) null);
                    }
                    new createPersonalExpenseTask(getApplicationContext()).execute((Void) null);
                    finish();
                } else {
                    categoryView.setError("Category can't be empty!");
                }

            }
        });
        ImageButton imgGroupExpenseBtn = (ImageButton) findViewById(R.id.group_expense);

        imgGroupExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = categoryView.getText().toString();
                if (!category.equals("")) {
                    showGroupSelectDialog();
                }
                categoryView.setError("Category can't be empty!");
            }

        } );
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

        // for debug
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void showGroupSelectDialog() {

        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        GroupExpenseDialog dialog = GroupExpenseDialog.newInstance(amountBox.getText().toString(), message.getText().toString(), categoryView.getText().toString());

        dialog.show(ft, "GroupExpenseDialog");

    }

    public class CategoryListLoadTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        CategoryListLoadTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            UserManager um = ManagerFactory.getUserManager(context);
            categoryList = um.getUser().getCategory_list();
            //userCategoryList = new ArrayList<>(categoryList);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            // Drop down list
            if (categoryList != null) {
                //spinner.setAdapter(new ArrayAdapter<String>(ExpenseCreation.this, android.R.layout.simple_spinner_item,new ArrayList<String>(categoryList)));
                categoryView.setAdapter(new ArrayAdapter<String>(ExpenseCreation.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>(categoryList)));

                categoryView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        categoryView.showDropDown();
                        return false;
                    }
                });
            }

        }
    }

    class createPersonalExpenseTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        createPersonalExpenseTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            PersonalExpenseManager pem = ManagerFactory.getPersonalExpenseManager(context);
            pem.createExpense(new PersonalExpense(ManagerFactory.getUserManager(context).getUser().getUserId(), amount, description, category));

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }
    }

    class updateUserCategoryTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;
        private Set<String> categoryList;

        updateUserCategoryTask(Context context, Set<String> categoryList) {
            this.context = context;
            this.categoryList = categoryList;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            UserManager um = ManagerFactory.getUserManager(context);
            um.getUser().setCategory_list(categoryList);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }
    }

}
