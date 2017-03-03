package teamhardcoder.y_fi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class Group extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(teamhardcoder.y_fi.R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(teamhardcoder.y_fi.R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
