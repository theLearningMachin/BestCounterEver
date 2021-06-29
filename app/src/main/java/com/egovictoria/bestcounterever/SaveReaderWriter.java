package com.egovictoria.bestcounterever;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

public class SaveReaderWriter {

    // details for sharedPreferences
    public static final String saveNamePrefs = "saveNames";
    private static final String TAG = "BestCounter/srw";






    public static void saveSet(ArrayList<?> theSet, String saveName) {
        Log.i(TAG, "saveSet: saving set called " + saveName);

        // generate the strings for the sharedPreferences editor
        String saveNameForPrefs = saveName + "names";
        String saveValuesForPrefs = saveName + "values";


        // generate the strings for saving
        String values = "";
        String names = "";
        // check if the objects in theSet are counters
        try {
            for (int i = 0; i < theSet.size(); i++) {
                values += ((Counter)theSet.get(i)).getCount() + ",";
                names += ((Counter)theSet.get(i)).getName() + ",";
            }
        } catch (Exception e) {
            e.printStackTrace();
            for (int i = 0; i < theSet.size(); i++) {
                names += ((String)theSet.get(i)) + ",";
            }
        }


        // add the save name to saveNames[]
        if (!AppConstants.saveNames.contains(saveName)) {
            try {
                AppConstants.saveNames.add(saveName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // convert save names to single string for shared preferences
        String saveNamesForPrefs = "";
        for(int i = 0; i < AppConstants.saveNames.size(); i++) {
            saveNamesForPrefs += AppConstants.saveNames.get(i) + ",";
        }


        // save to sharedPreferences
        SharedPreferences.Editor editor = AppConstants.prefs.edit();
        editor.putString(saveNameForPrefs, names);
        editor.commit();
        if (!values.equals("")) {
            editor.putString(saveValuesForPrefs, values);
            editor.commit();
        }
        editor.putString(saveNamePrefs, saveNamesForPrefs);
        editor.commit();

        Log.i(TAG, "save items saved in shared preferences, new saves list: " + AppConstants.saveNames.toString());
    }








    public static ArrayList<Object> getSet(String saveName) {
        Log.i(TAG, "fetching set for " + saveName);

        // generate the strings for the sharedPreferences object
        String saveNameForPrefs = saveName + "names";
        String saveValuesForPrefs = saveName + "values";

        // check if saves exist, then fetch the strings to convert
        String saveNamesStringList = "";
        String saveValuesStringList = "";
        if (AppConstants.prefs.contains(saveNameForPrefs)) {
            saveNamesStringList = AppConstants.prefs.getString(saveNameForPrefs, "");
        }
        if (AppConstants.prefs.contains(saveValuesForPrefs)) {
            saveValuesStringList = AppConstants.prefs.getString(saveValuesForPrefs, "");
        }

        // if strings are greater than length zero, then convert to String[]
        String[] saveNameStrings = new String[0];
        String[] saveValueStrings = new String[0];
        if (saveNamesStringList.length() > 0) {
            saveNameStrings = listFromStringList(saveNamesStringList);
        }
        if (saveValuesStringList.length() > 0) {
            saveValueStrings = listFromStringList(saveValuesStringList);
        }

        // if both lists have length > zero then return an ArrayList<Counter> otherwise, ArrayList<String>
        ArrayList<Object> output;
        if (saveNameStrings.length>0 && saveValueStrings.length>0) {
            output = new ArrayList<>();
            for (int i = 0; i < saveNameStrings.length; i++) {
                output.add(new Counter(Integer.parseInt(saveValueStrings[i]), saveNameStrings[i]));
            }
        } else {
            output = new ArrayList<>();
            for (int i = 0 ; i < saveNameStrings.length; i++) {
                output.add(saveNameStrings[i]);
            }
        }

        return output;
    }








    // converts a list stored in a long string to a String[]
    private static String[] listFromStringList(String input) {
        if (input == null) {
            return null;
        }
        Log.i(TAG, "list: " + input);
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
