package com.egovictoria.bestcounterever;

import android.util.Log;

import java.util.ArrayList;

public class SaveLoadHelper {

    private static final String TAG = "BestCounter/slh";


    // converts an ArrayList of counters into a long string using the Counter.getString method
    // and adding a comma between entries
    public static String stringList(ArrayList<Counter> input) {
        String output = "";

        for (int i = 0; i < input.size(); i++) {
            output += input.get(i).getString() + "|";
        }

        return output;
    }


    // takes a string from SaveReaderWriter and converts to Counter array
    public static ArrayList<Counter> fromString(String input) {
        Log.i(TAG, "fromString: input: " + input);
        ArrayList<Counter> output = new ArrayList<>();
        String stringCounter = "";
        for (int i = 0; i < input.length(); i++) {
            char atI = input.charAt(i);

            if (atI == '|') {
                output.add(new Counter(stringCounter));
                stringCounter = "";
            } else {
                stringCounter += atI;
            }
        }
        Log.i(TAG, "fromString: output: " + output.toString());
        return output;
    }

}
