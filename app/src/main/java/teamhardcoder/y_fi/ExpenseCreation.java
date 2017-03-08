package teamhardcoder.y_fi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class ExpenseCreation extends AppCompatActivity implements OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        double amount = intent.getDoubleExtra("totalAmount", 0.0);


        TextView amountBox = (TextView) findViewById(R.id.amount);
        amountBox.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60);
        amountBox.setText(Double.toString(amount));

        // Category Spinner
        Spinner spinner = (Spinner) findViewById(R.id.category_spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Drop down list

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



}
