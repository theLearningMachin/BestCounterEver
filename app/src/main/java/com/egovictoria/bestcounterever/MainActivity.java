package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button newCounter, loadCounter, settings;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // initialize views
        newCounter = findViewById(R.id.newCounterButton);
        loadCounter = findViewById(R.id.loadCounterButton);
        settings = findViewById(R.id.settingsButton);
        layout = findViewById(R.id.mainActivityLayout);

        newCounter.setOnClickListener(this);
        loadCounter.setOnClickListener(this);
        settings.setOnClickListener(this);

        // initialize appConstants, also generate the SharedPreferences object
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        AppConstants.initialize(prefs);

        // set aesthetic settings
        if (AppConstants.getTheme().equals("bright") || AppConstants.getTheme().equals("dark")) {
            layout.setBackgroundColor(Color.parseColor(AppConstants.Background));
        } else {
            layout.setBackground(Drawable.createFromPath(AppConstants.Background));
        }
        newCounter.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
        newCounter.setTextColor(Color.parseColor(AppConstants.TextColor));
        loadCounter.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
        loadCounter.setTextColor(Color.parseColor(AppConstants.TextColor));
        settings.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
        settings.setTextColor(Color.parseColor(AppConstants.TextColor));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.loadCounterButton) {
            Toast.makeText(this, "Make class for loading counters, also make a way to" +
                    " save counters", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.newCounterButton) {
            Toast.makeText(this, "creating new counter set", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, CounterListActivity.class));
        } else if (id == R.id.settingsButton) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
    }
}