package com.egovictoria.bestcounterever;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class AppConstants {

    private static final String TAG = "BestCounter/AppConst";

    // sharedPreferences keys
    public static final String themeKey = "theme";
    public static final String SDKey = "standardDeviation";

    // public objects
    public static int standardDeviation;
    public static String ButtonColor;
    public static Object Background;
    public static String TextColor;
    public static ArrayList<Counter> counters;
    public static String CurrentSaveName;
    public static ArrayList<String> saveNames;
    public static SaveReaderWriter counterSRW;
    public static SaveReaderWriter constantSRW;

    // save reader items
    public static String saveTextFileName = "saves.txt";
    public static String saveThemeFileName = "themes.txt";

    public static void initialize(Context c) {
        // counters array
        counters = new ArrayList<>();

        // get saveNames
        saveNames = new ArrayList<>();

        File counterSaveFile = c.getFilesDir();
        counterSaveFile = new File(counterSaveFile, saveTextFileName);
        Log.i(TAG, "initialize: saveFile for constants: " + counterSaveFile.getAbsolutePath());
        counterSRW = new SaveReaderWriter(counterSaveFile);
        Log.i(TAG, "initialize: current data in " + saveTextFileName + ":\n" + counterSRW.getFileText());

        saveNames = counterSRW.getTags();

        File constantSaveFile = c.getFilesDir();
        constantSaveFile = new File(constantSaveFile, saveThemeFileName);
        Log.i(TAG, "initialize: saveFile for constants: " + constantSaveFile.getAbsolutePath());
        constantSRW = new SaveReaderWriter(constantSaveFile);
        Log.i(TAG, "initialize: current data in " + saveThemeFileName + ":\n" + constantSRW.getFileText());

        String stringSD = constantSRW.getItem(SDKey);
        if (stringSD == null) {
            standardDeviation = 1;
            constantSRW.addItem(SDKey, String.valueOf(standardDeviation));
        } else {
            standardDeviation = Integer.parseInt(stringSD);
        }

        String theme = constantSRW.getItem(themeKey);
        if (theme == null) {
            setBrightTheme();
            constantSRW.addItem(themeKey, "bright");
        } else {
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
        return constantSRW.getItem(themeKey);
    }

    public static void setBrightTheme(){
        ButtonColor = "#883E68";
        Background = "#E6C7FE";
        TextColor = "#D799E0";
        saveTheme("bright");
    }
    public static void setDarkTheme(){
        ButtonColor = "#23274D";
        Background = "#607E95";
        TextColor = "#C2C7F8";
        saveTheme("dark");
    }
    public static void setWinterTheme(){
        ButtonColor = "#7ADBD7";
        Background = R.drawable.winter_scene;
        TextColor = "#000000";
        saveTheme("winter");
    }
    public static void setFallTheme(){
        ButtonColor = "#AF98D5";
        Background = R.drawable.fall_scene;
        TextColor = "#000000";
        saveTheme("fall");
    }
    public static void setSpringTheme(){
        ButtonColor = "#CEBC60";
        Background = R.drawable.spring_scene;
        TextColor = "#000000";
        saveTheme("spring");
    }
    public static void setSummerTheme(){
        ButtonColor = "#C899E8";
        Background = R.drawable.summer_scene;
        TextColor = "#000000";
        saveTheme("summer");
    }

    private static void saveTheme(String theme) {
        constantSRW.addItem(themeKey, theme);
    }
}
