package com.egovictoria.bestcounterever;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Window;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AppConstants {

    // sharedPreferences keys
    public static final String themeKey = "theme";
    public static final String SDKey = "standardDeviation";

    // public objects
    public static int standardDeviation;
    public static String ButtonColor;
    public static Object Background;
    public static String TextColor;
    public static SharedPreferences prefs;
    public static ArrayList<Counter> counters;
    public static Context context;

    public static void initialize(SharedPreferences p) {
        // sharedpreferences object
        prefs = p;

        // the amount that pressing a button changes the counter
        if (prefs.getInt(SDKey, 0) == 0) {
            standardDeviation = 1;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(SDKey, standardDeviation);
            editor.apply();
        } else {
            standardDeviation = prefs.getInt(SDKey, 0);
        }


        // the counters array for the application
        counters = new ArrayList<Counter>();


        // theme stuff
        if (prefs.getString(themeKey, null) == null) {
            setBrightTheme();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(themeKey, "bright");
            editor.apply();
        } else {
            String theme = prefs.getString(themeKey, null);
            switch (theme) {
                case "winter":
                    setWinterTheme();
                    break;
                case "summer":
                    setSummerTheme();
                    break;
                case "spring":
                    setSpringTheme();
                    break;
                case "bright":
                    setBrightTheme();
                    break;
                case "dark":
                    setDarkTheme();
                    break;
                case "fall":
                    setFallTheme();
                    break;
            }
        }
    }

    public static String getTheme() {
        return prefs.getString(themeKey, null);
    }

    public static void setBrightTheme(){
        ButtonColor = "#83F7E2";
        Background = "#E6C7FE";
        TextColor = "#000000";
    }
    public static void setDarkTheme(){
        ButtonColor = "#23274D";
        Background = "#607E95";
        TextColor = "#C2C7F8";
    }
    public static void setWinterTheme(){
        ButtonColor = "#7ADBD7";
        Background = R.drawable.winter_scene;
        TextColor = "#000000";
    }
    public static void setFallTheme(){
        ButtonColor = "#AF98D5";
        Background = R.drawable.fall_scene;
        TextColor = "#000000";
    }
    public static void setSpringTheme(){
        ButtonColor = "#CEBC60";
        Background = R.drawable.spring_scene;
        TextColor = "#000000";
    }
    public static void setSummerTheme(){
        ButtonColor = "#C899E8";
        Background = R.drawable.summer_scene;
        TextColor = "#000000";
    }

    public void setCounters(ArrayList<Counter> counters) {
        AppConstants.counters = counters;
    }

    public ArrayList<Counter> getCounters() {
        return counters;
    }
}
