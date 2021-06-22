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
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BestCounter/main";
    private Button newCounter, loadCounter, settings;
    private ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "app started");

        // initialize views
        newCounter = findViewById(R.id.newCounterButton);
        loadCounter = findViewById(R.id.loadCounterButton);
        settings = findViewById(R.id.settingsButton);
        background = findViewById(R.id.mainImageView);

        Log.i(TAG, "views initialized");

        newCounter.setOnClickListener(this);
        loadCounter.setOnClickListener(this);
        settings.setOnClickListener(this);

        Log.i(TAG, "on click listeners set");

        // initialize appConstants, also generate the SharedPreferences object
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        AppConstants.initialize(prefs);

        Log.i(TAG, "app constants initialized");

        String theme = prefs.getString(AppConstants.themeKey, null);
        int SD = prefs.getInt(AppConstants.SDKey, 0);

        Log.i(TAG, "shared preferences: \nTheme: " + theme + "\nStandardDeviation: " + SD);

        // set aesthetic settings
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (AppConstants.getTheme().equals("bright") || AppConstants.getTheme().equals("dark")) {
                    background.setBackgroundColor(Color.parseColor((String) AppConstants.Background));
                } else {
                    background.setImageResource((Integer) AppConstants.Background);
                }

                newCounter.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                newCounter.setTextColor(Color.parseColor(AppConstants.TextColor));
                loadCounter.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                loadCounter.setTextColor(Color.parseColor(AppConstants.TextColor));
                settings.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                settings.setTextColor(Color.parseColor(AppConstants.TextColor));
            }
        });

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
            finish();
        } else if (id == R.id.settingsButton) {
            startActivity(new Intent(this, SettingsActivity.class));
            finish();
        }
    }
}