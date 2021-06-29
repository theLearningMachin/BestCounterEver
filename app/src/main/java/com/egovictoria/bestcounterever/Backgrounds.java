package com.egovictoria.bestcounterever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdValue;
import com.google.android.gms.ads.AdView;

import java.util.Timer;
import java.util.TimerTask;

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
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_backgrounds);

        // initialize views
        bright = findViewById(R.id.brightThemeButton);
        dark = findViewById(R.id.darkThemeButton);
        summer = findViewById(R.id.summerThemeButton);
        winter = findViewById(R.id.winterThemebutton);
        fall = findViewById(R.id.fallThemeButton);
        spring = findViewById(R.id.springThemebutton);
        background = findViewById(R.id.backgroundsImageView);
        backToSettings = findViewById(R.id.toSettingsFromBackgroundButton);
        adView = findViewById(R.id.backgroundActivityAdView);

        // aesthetics
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


        // initialize ad
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // wait for the ad to load
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Fetching Background Images");
        progress.setMessage("Please wait while loading...");
        progress.setCancelable(false);
        progress.show();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                progress.dismiss();
            }
        }, 6000);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.brightThemeButton:
                Toast.makeText(this, "Bright Theme Set", Toast.LENGTH_SHORT).show();
                AppConstants.setBrightTheme();
                editor.putString(AppConstants.themeKey, brightPrefs);
                editor.apply();
                break;
            case R.id.darkThemeButton:
                Toast.makeText(this, "Dark Theme Set", Toast.LENGTH_SHORT).show();
                AppConstants.setDarkTheme();
                editor.putString(AppConstants.themeKey, darkPrefs);
                editor.apply();
                break;
            case R.id.summerThemeButton:
                Toast.makeText(this, "Summer Theme Set", Toast.LENGTH_SHORT).show();
                AppConstants.setSummerTheme();
                editor.putString(AppConstants.themeKey, summerPrefs);
                editor.apply();
                break;
            case R.id.winterThemebutton:
                Toast.makeText(this, "Winter Theme Set", Toast.LENGTH_SHORT).show();
                AppConstants.setWinterTheme();
                editor.putString(AppConstants.themeKey, winterPrefs);
                editor.apply();
                break;
            case R.id.fallThemeButton:
                Toast.makeText(this, "Fall Theme Set", Toast.LENGTH_SHORT).show();
                AppConstants.setFallTheme();
                editor.putString(AppConstants.themeKey, fallPrefs);
                editor.apply();
                break;
            case R.id.springThemebutton:
                Toast.makeText(this, "Spring Theme Set", Toast.LENGTH_SHORT).show();
                AppConstants.setSpringTheme();
                editor.putString(AppConstants.themeKey, springPrefs);
                editor.apply();
                break;
            case R.id.toSettingsFromBackgroundButton:
                startActivity(new Intent(this, SettingsActivity.class));
                finish();
                break;
        }

        Backgrounds.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (AppConstants.getTheme().equals("bright") ||
                        AppConstants.getTheme().equals("dark")) {
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
                return "#883E68";
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
            case "bright":
                return "#D799E0";
            default:
                return "#000000";
        }
    }
}