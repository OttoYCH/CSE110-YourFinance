package teamhardcoder.y_fi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(teamhardcoder.y_fi.R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(teamhardcoder.y_fi.R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void toScan(View view)
    {
        Intent intent = new Intent(this, ScannerActivity.class);
        startActivity(intent);
    }

    public void toFinance(View view)
    {
        Intent intent = new Intent(this, PersonalFinance.class);
        startActivity(intent);
    }

    public void toGroup(View view)
    {
        Intent intent = new Intent(this, Group.class);
        startActivity(intent);
    }

    public void toSettings(View view)
    {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

}
