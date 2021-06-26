package com.egovictoria.bestcounterever;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class SaveReaderWriter {

    /*
    saves are stored in sharedpreferences
    there will be a string list called saveNames with the names of the saves and the counter arrays
    will be saved under those names as two seperate preferences called
    "saveName" + "values"
    "saveName" + "names"
    which will be combined to return the list
     */

    // details for sharedPreferences
    private static final String saveNamePrefs = "saveNames";
    private static final String TAG = "BestCounter/srw";
    private String[] saveNames;

    public SaveReaderWriter() {
        try {
            saveNames = listFromStringList(AppConstants.prefs.getString(saveNamePrefs, null));
            String logText = "";
            for (int i = 0; i < saveNames.length; i++) {
                logText += "\n"+saveNames[i];
            }
            Log.i(TAG, "Save names loaded from srw: " + logText);

        } catch (NullPointerException e) {
            Log.i(TAG, "null pointer exception in srw");
            SharedPreferences.Editor editor = AppConstants.prefs.edit();
            editor.putString(saveNamePrefs, "");
            editor.apply();
        }
    }

    String [] getSaveNames() {
        return saveNames;
    }





    void saveSet(ArrayList<Counter> theSet, String saveName) {
        // generate the strings for the sharedPreferences editor
        String saveNameForPrefs = saveName + "names";
        String saveValuesForPrefs = saveName + "values";

        Log.i(TAG, "save name variables set");

        // generate the strings for saving
        String values = "";
        String names = "";
        for (int i = 0; i < theSet.size(); i++) {
            values += theSet.get(i).getCount() + ",";
            names += theSet.get(i).getName() + ",";
        }

        Log.i(TAG, "save values set");

        // add the save name to saveNames[]
        if (saveNames.length == 0) {
            saveNames = new String[1];
            saveNames[0] = saveName;
        } else {
            String[] newSaves = new String[saveNames.length + 1];
            for(int i = 0; i < saveNames.length; i++) {
                newSaves[i] = saveNames[i];
            }
            newSaves[saveNames.length - 1] = saveName;
            saveNames = newSaves;
        }

        Log.i(TAG, "new save added to saves list");

        // convert save names to single string for shared preferences
        String saveNamesForPrefs = "";
        for(int i = 0; i < saveNames.length; i++) {
            saveNamesForPrefs += saveNames[i] + ",";
        }

        Log.i(TAG, "new save list created and ready to save");

        // save to sharedPreferences
        SharedPreferences.Editor editor = AppConstants.prefs.edit();
        editor.putString(saveNameForPrefs, names);
        editor.putString(saveValuesForPrefs, values);
        editor.putString(saveNamePrefs, saveNamesForPrefs);
        editor.commit();

        Log.i(TAG, "save items saved in shared preferences");
    }





    ArrayList<Counter> getSet(String saveName) {
        // strings for the preferences object
        String saveNames = saveName + "names";
        String saveValue = saveName + "values";

        // get the strings from shared preferences and convert to string[]
        String[] nameList = listFromStringList(AppConstants.prefs.getString(saveNames, null));
        String[] valuList = listFromStringList(AppConstants.prefs.getString(saveValue, null));

        // convert to Counter[] and return
        Counter[] counters = new Counter[nameList.length];
        for(int i = 0; i < counters.length; i++) {
            Counter temp = new Counter(Integer.parseInt(valuList[i]), nameList[i]);
            counters[i] = temp;
        }

        ArrayList<Counter> output = new ArrayList<>();
        for(Counter counter : counters) {
            output.add(counter);
        }
        return output;
    }

    // converts a list stored in a long string to a String[]
    private String[] listFromStringList(String input) {
        String item = "";
        ArrayList<String> tempList = new ArrayList<>();
        for(int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ',') {
                tempList.add(item);
                item = "";
            } else {
                item += input.charAt(i);
            }
        }
        String[] output = new String[tempList.size()];
        for (int i = 0; i < output.length; i++) {
            output[i] = tempList.get(i);
        }
        return output;
    }
}
