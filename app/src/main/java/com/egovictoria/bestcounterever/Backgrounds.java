package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Backgrounds extends AppCompatActivity implements View.OnClickListener {

    private Button bright, dark, summer, winter, fall, spring, backToSettings;
    private ImageView background;
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
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_backgrounds);

        // initialize buttons and set onClickListeners
        bright = findViewById(R.id.brightThemeButton);
        dark = findViewById(R.id.darkThemeButton);
        summer = findViewById(R.id.summerThemeButton);
        winter = findViewById(R.id.winterThemebutton);
        fall = findViewById(R.id.fallThemeButton);
        spring = findViewById(R.id.springThemebutton);
        background = findViewById(R.id.backgroundsImageView);
        backToSettings = findViewById(R.id.toSettingsFromBackgroundButton);

        // for each button, set them to their relative layout color schema
        Backgrounds.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bright.setTextColor(Color.parseColor(getTextColor("bright")));
                bright.setBackgroundColor(Color.parseColor(getButtonColor("bright")));
                dark.setTextColor(Color.parseColor(getTextColor("dark")));
                dark.setBackgroundColor(Color.parseColor(getButtonColor("dark")));
                summer.setTextColor(Color.parseColor(getTextColor("summer")));
                summer.setBackgroundColor(Color.parseColor(getButtonColor("summer")));
                winter.setTextColor(Color.parseColor(getTextColor("winter")));
                winter.setBackgroundColor(Color.parseColor(getButtonColor("winter")));
                fall.setTextColor(Color.parseColor(getTextColor("fall")));
                fall.setBackgroundColor(Color.parseColor(getButtonColor("fall")));
                spring.setTextColor(Color.parseColor(getTextColor("spring")));
                spring.setBackgroundColor(Color.parseColor(getButtonColor("spring")));

                if (AppConstants.prefs.getString(AppConstants.themeKey, null).equals("bright") ||
                        AppConstants.prefs.getString(AppConstants.themeKey, null).equals("dark")) {
                    background.setBackgroundColor(Color.parseColor((String) AppConstants.Background));
                } else {
                    background.setImageResource((Integer) AppConstants.Background);
                }

                backToSettings.setTextColor(Color.parseColor(AppConstants.TextColor));
                backToSettings.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
            }
        });

        bright.setOnClickListener(this);
        dark.setOnClickListener(this);
        summer.setOnClickListener(this);
        winter.setOnClickListener(this);
        fall.setOnClickListener(this);
        spring.setOnClickListener(this);
        backToSettings.setOnClickListener(this);

        // initialize preferences editor object
        editor = AppConstants.prefs.edit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.brightThemeButton) {
            Toast.makeText(this, "Bright Theme Set", Toast.LENGTH_SHORT).show();
            AppConstants.setBrightTheme();
            editor.putString(AppConstants.themeKey, brightPrefs);
            editor.commit();
        } else if (id == R.id.darkThemeButton) {
            Toast.makeText(this, "Dark Theme Set", Toast.LENGTH_SHORT).show();
            AppConstants.setDarkTheme();
            editor.putString(AppConstants.themeKey, darkPrefs);
            editor.commit();
        } else if (id == R.id.summerThemeButton) {
            Toast.makeText(this, "Summer Theme Set", Toast.LENGTH_SHORT).show();
            AppConstants.setSummerTheme();
            editor.putString(AppConstants.themeKey, summerPrefs);
            editor.commit();
        } else if (id == R.id.winterThemebutton) {
            Toast.makeText(this, "Winter Theme Set", Toast.LENGTH_SHORT).show();
            AppConstants.setWinterTheme();
            editor.putString(AppConstants.themeKey, winterPrefs);
            editor.commit();
        } else if (id == R.id.fallThemeButton) {
            Toast.makeText(this, "Fall Theme Set", Toast.LENGTH_SHORT).show();
            AppConstants.setFallTheme();
            editor.putString(AppConstants.themeKey, fallPrefs);
            editor.commit();
        } else if (id == R.id.springThemebutton) {
            Toast.makeText(this, "Spring Theme Set", Toast.LENGTH_SHORT).show();
            AppConstants.setSpringTheme();
            editor.putString(AppConstants.themeKey, springPrefs);
            editor.commit();
        } else if (id == R.id.toSettingsFromBackgroundButton) {
            startActivity(new Intent(this, SettingsActivity.class));
            finish();
        }

        Backgrounds.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (AppConstants.getTheme().equals("bright") || AppConstants.getTheme().equals("dark")) {
                    background.setImageResource(0);
                    background.setBackgroundColor(Color.parseColor((String) AppConstants.Background));
                } else {
                    background.setImageResource((Integer) AppConstants.Background);
                }

                backToSettings.setBackgroundColor(Color.parseColor(AppConstants.ButtonColor));
                backToSettings.setTextColor(Color.parseColor(AppConstants.TextColor));
            }
        });
    }



    private static String getButtonColor(String theme) {
        switch (theme) {
            case "winter":
                return "#7ADBD7";
            case "summer":
                return "#C899E8";
            case "spring":
                return "#CEBC60";
            case "bright":
                return "#83F7E2";
            case "dark":
                return "#23274D";
            case "fall":
                return "#AF98D5";
            default:
                return null;
        }
    }

    private static String getTextColor(String theme) {
        switch (theme) {
            case "dark":
                return "#C2C7F8";
            default:
                return "#000000";
        }
    }
}