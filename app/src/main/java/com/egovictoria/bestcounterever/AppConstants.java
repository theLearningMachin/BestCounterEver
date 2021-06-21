package com.egovictoria.bestcounterever;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class AppConstants {

    // sharedPreferences keys
    public static final String themeKey = "theme";
    public static final String SDKey = "standardDeviation";

    // public objects
    public static int standardDeviation;
    public static String ButtonColor;
    public static String Background;
    public static String TextColor;
    public static SharedPreferences prefs;

    public static void initialize(SharedPreferences p) {
        prefs = p;
        if (prefs.getInt(SDKey, 0) == 0) {
            standardDeviation = 1;
        } else {
            standardDeviation = prefs.getInt(SDKey, 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(SDKey, standardDeviation);
            editor.apply();
        }


        if (prefs.getString(themeKey, null) == null) {
            setBrightTheme();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(themeKey, "bright");
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
        Background = "@drawable/winter_scene.png";
        TextColor = "#000000";
    }
    public static void setFallTheme(){
        ButtonColor = "#AF98D5";
        Background = "@drawable/fall_scene.png";
        TextColor = "#000000";
    }
    public static void setSpringTheme(){
        ButtonColor = "#CEBC60";
        Background = "@drawable/spring_scene.jpg";
        TextColor = "#000000";
    }
    public static void setSummerTheme(){
        ButtonColor = "#C899E8";
        Background = "@drawable/summer_scene.jpg";
        TextColor = "#000000";
    }
}
