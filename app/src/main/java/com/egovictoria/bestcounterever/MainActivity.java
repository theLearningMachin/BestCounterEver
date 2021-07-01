package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
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

        Log.i(TAG, "onCreate: app started");

        try {
            MobileAds.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "onCreate: ads initialized");

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


        // initialize appConstants if it hasn't already been
        AppConstants.initialize(getApplicationContext());

        // set aesthetic settings
        try {
            MainActivity.this.runOnUiThread(new Runnable() {
                @SuppressLint("ResourceType")
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

                    Log.i(TAG, "aesthetics set");
                }
            });
        } catch (Exception e) {
            String name = e.getClass().getCanonicalName();
            Log.i(TAG, "app failed during aesthetics " + name);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.loadCounterButton:
                startActivity(new Intent(this, LoadCounterSetActivity.class));
                finish();
                break;
            case R.id.newCounterButton:
                AppConstants.counters = new ArrayList<>();
                startActivity(new Intent(this, CounterListActivity.class));
                finish();
                break;
            case R.id.settingsButton:
                startActivity(new Intent(this, SettingsActivity.class));
                finish();
                break;
        }
    }
}