package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BestCounter/main";
    private Button newCounter, loadCounter, settings;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "attempting on create in main");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "app started");

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Log.i(TAG, "window without title requested");

        // initialize views
        newCounter = findViewById(R.id.newCounterButton);
        loadCounter = findViewById(R.id.loadCounterButton);
        settings = findViewById(R.id.settingsButton);
        layout = findViewById(R.id.mainActivityLayout);

        Log.i(TAG, "views initialized");

        newCounter.setOnClickListener(this);
        loadCounter.setOnClickListener(this);
        settings.setOnClickListener(this);

        Log.i(TAG, "on click listeners set");

        // initialize appConstants, also generate the SharedPreferences object
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        AppConstants.initialize(prefs);

        Log.i(TAG, "app constants initialized");

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

        Log.i(TAG, "aesthetics set");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.loadCounterButton) {
            Toast.makeText(this, "Make class for loading counters, also make a way to" +
                    " save counters", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.newCounterButton) {
            startActivity(new Intent(this, CounterListActivity.class));
        } else if (id == R.id.settingsButton) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
    }
}