package com.egovictoria.bestcounterever;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class AppConstants {

    private static final String TAG = "BestCounter/AppConst";

    // sharedPreferences keys
    public static final String themeKey = "theme";
    public static final String SDKey = "standardDeviation";

    // public objects
    public static boolean initialized;
    public static int standardDeviation;
    public static String ButtonColor;
    public static Object Background;
    public static String TextColor;
    public static ArrayList<Counter> counters;
    public static String CurrentSaveName;
    public static SharedPreferences prefs;
    public static ArrayList<String> saveNames;

    public static void initialize(Context c) {
        prefs = PreferenceManager.getDefaultSharedPreferences(c);

        // counters array
        counters = new ArrayList<>();

        // get saveNames
        saveNames = new ArrayList<>();
        if (prefs.contains(SaveReaderWriter.saveNamePrefs)) {
            ArrayList<Object> items = SaveReaderWriter.getSet(SaveReaderWriter.saveNamePrefs);
            for (int i = 0; i < items.size(); i++) {
                saveNames.add((String) items.get(i));
            }
            Log.i(TAG, "saveNames object loaded from shared preferences " + saveNames.toString());
        } else {
            Log.i(TAG, "new saveNames object created");
        }

        // the amount that pressing a button changes the counter
        if (prefs.getInt(SDKey, 0) == 0) {
            standardDeviation = 1;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(SDKey, standardDeviation);
            editor.commit();
        } else {
            standardDeviation = prefs.getInt(SDKey, 0);
        }

        // theme stuff
        if (prefs.getString(themeKey, null) == null) {
            setBrightTheme();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(themeKey, "bright");
            editor.commit();
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

        Log.i(TAG, "initialized");
    }

    public static String getTheme() {
        return prefs.getString(themeKey, null);
    }

    public static void setBrightTheme(){
        ButtonColor = "#883E68";
        Background = "#E6C7FE";
        TextColor = "#D799E0";
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
}
