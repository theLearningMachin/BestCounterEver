package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button newCounter, loadCounter, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize buttons
        newCounter = findViewById(R.id.newCounterButton);
        loadCounter = findViewById(R.id.loadCounterButton);
        settings = findViewById(R.id.settingsButton);

        newCounter.setOnClickListener(this);
        loadCounter.setOnClickListener(this);
        settings.setOnClickListener(this);

        AppConstants.initialize();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.loadCounterButton:
                Toast.makeText(this, "Make class for loading counters", Toast.LENGTH_SHORT).show();
                break;
            case R.id.newCounterButton:
                Toast.makeText(this, "creating new counter set", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CounterListActivity.class));
                break;
            case R.id.settingsButton:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }
}