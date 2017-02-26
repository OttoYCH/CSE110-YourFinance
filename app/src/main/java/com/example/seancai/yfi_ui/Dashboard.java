package com.example.seancai.yfi_ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }



    public void toFinance(View view)
    {
        Intent intent = new Intent(this, Finance.class);
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
