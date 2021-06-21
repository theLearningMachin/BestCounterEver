package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Backgrounds extends AppCompatActivity implements View.OnClickListener {

    private Button bright, dark, summer, winter, fall, spring;
    private ConstraintLayout layout;
    private static final String brightPrefs = "bright";
    private static final String darkPrefs = "dark";
    private static final String summerPrefs = "summer";
    private static final String winterPrefs = "winter";
    private static final String fallPrefs = "fall";
    private static final String springPrefs = "spring";
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backgrounds);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // initialize buttons and set onClickListeners
        bright = findViewById(R.id.brightThemeButton);
        dark = findViewById(R.id.darkThemeButton);
        summer = findViewById(R.id.summerThemeButton);
        winter = findViewById(R.id.winterThemebutton);
        fall = findViewById(R.id.fallThemeButton);
        spring = findViewById(R.id.springThemebutton);

        bright.setOnClickListener(this);
        dark.setOnClickListener(this);
        summer.setOnClickListener(this);
        winter.setOnClickListener(this);
        fall.setOnClickListener(this);
        spring.setOnClickListener(this);

        // initialize preferences editor object
        editor = AppConstants.prefs.edit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.brightThemeButton) {
            AppConstants.setBrightTheme();
            editor.putString(AppConstants.themeKey, brightPrefs);
            editor.commit();
        } else if (id == R.id.darkThemeButton) {
            AppConstants.setDarkTheme();
            editor.putString(AppConstants.themeKey, darkPrefs);
            editor.commit();
        } else if (id == R.id.summerThemeButton) {
            AppConstants.setSummerTheme();
            editor.putString(AppConstants.themeKey, summerPrefs);
            editor.commit();
        } else if (id == R.id.winterThemebutton) {
            AppConstants.setWinterTheme();
            editor.putString(AppConstants.themeKey, winterPrefs);
            editor.commit();
        } else if (id == R.id.fallThemeButton) {
            AppConstants.setFallTheme();
            editor.putString(AppConstants.themeKey, fallPrefs);
            editor.commit();
        } else if (id == R.id.springThemebutton) {
            AppConstants.setSpringTheme();
            editor.putString(AppConstants.themeKey, springPrefs);
            editor.commit();
        }
    }
}