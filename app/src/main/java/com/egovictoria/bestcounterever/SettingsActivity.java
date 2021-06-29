package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static com.egovictoria.bestcounterever.R.*;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button selectBackgroundOption, counterOptions, mainMenu;
    private ImageView background;
    private final String TAG = "BestCounter/SettingMenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layout.activity_settings);

        // initialize views
        selectBackgroundOption = findViewById(id.selectBackgroundOptionButton);
        counterOptions = findViewById(id.CounterOptionsButton);
        mainMenu = findViewById(id.toMainMenuFromSettingsMenuButton);
        background = findViewById(id.settingsImageView);

        selectBackgroundOption.setOnClickListener(this);
        counterOptions.setOnClickListener(this);
        mainMenu.setOnClickListener(this);

        Log.i(TAG, "initialized");

        // aesthetics
        SettingsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mainMenu.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                mainMenu.setTextColor(Color.parseColor(AppConstants.TextColor));
                selectBackgroundOption.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                selectBackgroundOption.setTextColor(Color.parseColor(AppConstants.TextColor));
                counterOptions.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                counterOptions.setTextColor(Color.parseColor(AppConstants.TextColor));

                if (AppConstants.getTheme().equals("bright") || AppConstants.getTheme().equals("dark")) {
                    background.setBackgroundColor(Color.parseColor((String) AppConstants.Background));
                } else {
                    background.setImageResource((Integer) AppConstants.Background);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.selectBackgroundOptionButton:
                startActivity(new Intent(this, Backgrounds.class));
                finish();
                break;
            case R.id.CounterOptionsButton:
                startActivity(new Intent(this, CounterOptions.class));
                finish();
                break;
            case R.id.toMainMenuFromSettingsMenuButton:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }

    }
}