package com.egovictoria.bestcounterever;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveReaderWriter {

    /*
    saves the list of counters in a text file in the following format

    ,          : this shows a separation between objects
    []         : brackets surround the list of objects
    "name"     : the first object in the list will be a string which is the name of the list
    {12,""} : this denotes the object
    ;          : end of list character
     */

    private File savesFile;

    public SaveReaderWriter() {
        savesFile = new File("saves.txt");
    }

    // saves the set when the save counter button in counter list activity is clicked
    void saveSet(Counter[] counters, String name) throws IOException {
        // add the name to the beginning of the list
        String line = "[" + name + ",";

        // generate the FileWriter object, making sure to append instead of overwrite
        FileWriter writer = new FileWriter(savesFile, true);

        // iterate through the counters list in order to create the objects to save
        for(int i = 0; i < counters.length; i++) {
            String counterText = "{" + counters[i].getCount() + "," + counters[i].getName() + "}";
            if (i < counters.length - 1) {
                counterText += ",";
            }
        }

        // add the end of list character
        line += "];";

        // save the string to file and close the writer
        writer.write(line);
        writer.close();
    }

    // fetches a set when it is selected in load counter activity
    Counter[] getSet(String name) {
        // iterate through the lists in order to find the one with the correct name
        return new Counter[0];
    }
}
