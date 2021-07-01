package com.egovictoria.bestcounterever;

import android.util.Log;

import androidx.annotation.RequiresPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SaveReaderWriter {

    private static final String TAG = "BestCounter/dictionary";

    /*
    SAVE FILE IS IN THE FOLLOWING FORMAT

    <12345=         : represents a tag (entry name) of 12345, number to the right of tag is the length
                        of the tag. Everything after the equals sign is the type of the object tied to
                        the tag, and then the object in string format

    8/12345678>     : this is the object being stored, the number to the left of the slash is the
                        length of the object, so the reader can skip straight to the end object character

    {directory:2/<>}: everything enclosed by the two curly brackets is called a directory. This bit of text is
                        at the beginning of each file and contains all the directory names so each input
                        string can be observed separately from the getTags() method.



            EXAMPLE FILE:

{d<helloWorld/99><flake/35><killMe/28>}
{helloWorld/99<one=10/helloWorld><two=10/helloWorld><three=10/helloWorld><four=10/helloWorld><five=10/helloWorld>}
{flake/35<oneHand=4/true><otherHand=5/false>}{killMe/28<1=3/yes><2=5/maybe><3=2/no>}




TODO//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
make getDirectories() return the string at the beginning of the file
make getTags() require a specified directory
add directories to appConstants
test the app!!!
     */

    private static File directoryFile;

    public static ArrayList<String> directories;



    public SaveReaderWriter(File saveFile) {
        directoryFile = saveFile;

        if (!directoryFile.exists()) {
            try {
                if (directoryFile.createNewFile()) {
                    Log.i(TAG, "file created for dictionary object");
                } else {
                    Log.i(TAG, "dictionary file already exists");
                    Log.i(TAG, directoryFile.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "UnorderedDictionary: IOException check stack trace");
            }
        }

        directoryFile.setWritable(true);
        directoryFile.setReadable(true);

        directories = new ArrayList<>();
        try {
            directories = getDirectories();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    // fetches the contained directories within the file
    ArrayList<String> getDirectories() throws IOException {
        // variables
        FileReader reader = null;
        boolean gettingTag = false;
        boolean gettingLength = false;
        boolean gettingDetails = false;
        boolean gettingDirectory = false;
        String tag = "";
        String length = "";
        String details = "";
        String directory = "";


        try {
            reader = new FileReader(directoryFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int c = 0;
        while (true) {
            try {
                if ((c = reader.read()) == -1) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            char ch = (char) c;

            if (ch == '<'){
                gettingTag = true;
            } else if (ch == '/') {
                gettingLength = false;
                if (tag.equals("directories")) {
                    gettingDetails = true;
                } else {
                    reader.skip(Long.parseLong(length));
                }
            } else if (ch == ':') {
                gettingTag = false;
                gettingLength = true;
            } else if (ch == '>') {
                gettingDetails = false;
                gettingTag = false;
                gettingLength = false;
                Log.i(TAG, "getDirectories: \ncurrent tag: " + tag + "\ncurrent details: " + details);
            } else if (ch == '{') {
                gettingDirectory = true;
            }


            if (gettingTag) {
                tag += ch;
            } else if (gettingDetails) {
                details += ch;
            } else if (gettingLength) {
                length += ch;
            }

            if (tag.equals("directories") && !gettingDetails && !gettingLength) {
                break;
            }
        }

        ArrayList<String> output = new ArrayList<>();
        String item = "";
        for (int i = 0; i < details.length(); i++) {
            char ch = (char) details.charAt(i);

            if (ch == ',') {
                output.add(item);
                item = "";
            } else {
                item += ch;
            }
        }

        return output;
    }





    // adds a directory to the file
    void addDirectory(String directory) {
        try {
            ArrayList<String> directories = getDirectories();
            directories.add(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String directoryString = "";
        for (int i = 0; i < directories.size(); i++) {
            directoryString += directories.get(i) + ",";
        }

        addItem("directories", directoryString);
    }




    // removes the selected directory from the file
    void removeDirectory(String directory) {
        try {
            ArrayList<String> directories = getDirectories();
        } catch (IOException e) {
            e.printStackTrace();
        }
        directories.remove(directory);
        String directoryString = "";
        for (int i = 0; i < directories.size(); i++) {
            directoryString += directories.get(i);
        }

        addItem("directories", directoryString);
    }





    // deletes all data in the file
    void clearFile() {
        try {
            FileWriter writer = new FileWriter(directoryFile);
            writer.write("<directories:>");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "clearFile: IOException check stack trace");
        }

        try {
            directories = getDirectories();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    // returns the entire directory file as a string object
    String getFileText() {

        String output = "";

        try {
            FileReader reader = new FileReader(directoryFile);

            int i;
            while ((i = reader.read()) != -1) {
                output += (char) i;
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "getFile: IOException check stack trace");
        }

        return output;
    }






    // deletes a tag and its details
    void delete(String tag) {
        String details = getItem(tag);
        String removeThis = "<" + tag + "=" + details.length() + "/" + details + ">";

        String oldFileText = getFileText();
        String newFileText = oldFileText.replace(removeThis, "");

        try {
            FileWriter writer = new FileWriter(directoryFile, false);
            writer.write(newFileText);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "delete: IOException check stack trace");
        }
    }







    // returns all the tags in an array list
    ArrayList<String> getTags() {

        // output array and temp string for tags
        ArrayList<String> output = new ArrayList<>();
        String tag = "";
        String len = "";

        try {
            FileReader reader = new FileReader(directoryFile);


            // the booleans control the loop
            boolean getTag = false;
            boolean getSkipLength = false;
            boolean skipItem = false;

            // iterate through the list and get all the tags, add them to a list and return it
            int r;
            while ((r = reader.read()) != -1) {
                char lineItem = (char) r;


                if (lineItem == '<') {
                    getTag = true;
                } else if ((lineItem == '=')) {
                    getTag = false;
                    getSkipLength = true;
                } else if (lineItem == '/') {
                    getSkipLength = false;
                    skipItem = true;
                } else if (lineItem == '>') {
                    getTag = false;
                    getSkipLength = false;
                    skipItem = false;
                }

                boolean LInotAS = true;
                char[] AS = {'<', '=', '/', '>'};
                for (char c : AS) {
                    if (lineItem == c) {
                        LInotAS = false;
                    }
                }
                if (LInotAS) {
                    if (getTag) {
                        tag += lineItem;
                    } else if (getSkipLength) {
                        len += lineItem;
                    } else if (skipItem) {
                        reader.skip(Long.parseLong(len));

                        output.add(tag);
                        tag = "";
                        len = "";
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "getTags: IOException check stack trace");
        }

        return output;
    }







    // adds the inputted item to the end of the file, deletes old information and adds new stuff to
    // the end of the file if the tag is already present
    void addItem(String tag, String details) {
        // if the tag already exists, overwrite the old tag
        boolean overwrite = false;
        ArrayList<String> oldTags = getTags();
        for (String oldTag : oldTags) {
            if (tag.equals(oldTag)) {
                overwrite = true;
            }
        }

        try {
            // overwrite
            if (overwrite) {
                String oldDetails = getItem(tag);
                String oldItem = "<" + tag + "=" + oldDetails.length() + "/" + oldDetails + ">";
                String oldFileText = getFileText();
                String newFileText = oldFileText.replace(oldItem, "");
                newFileText += "<" + tag + "=" + details.length() + "/" + details + ">";

                FileWriter writer = new FileWriter(directoryFile, false);
                writer.write(newFileText);
                writer.close();
            }

            // append
            else {
                String addToFile = "<" + tag + "=" + details.length() + "/" + details + ">";

                FileWriter writer = new FileWriter(directoryFile, true);
                writer.write(addToFile);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "addItem: IOException check stack trace");
        }
    }








    // returns the details attached to the tag
    String getItem(String tag) {

        String itemTag = "";
        String itemDetails = "";
        String length = "";
        boolean wrongTag = false;
        boolean skipDetails = false;
        boolean tagFound = false;
        boolean gettingTag = false;
        boolean gettingObjectString = false;
        boolean gettingObjectStringLength = false;

        try {
            FileReader reader = new FileReader(directoryFile);




            int r;
            while ((r = reader.read()) != -1) {
                char c = (char) r;

                if (c == '<') {
                    gettingTag = true;
                } else if (c == '=') {
                    gettingTag = false;
                    gettingObjectStringLength = true;

                    if (!itemTag.equals(tag)) {
                        wrongTag = true;
                    } else {
                        tagFound = true;
                    }
                } else if (c == '/') {
                    gettingObjectStringLength = false;
                    gettingObjectString = true;
                    if (wrongTag) {
                        skipDetails = true;
                    }
                } else if (c == '>') {
                    if (tagFound) {
                        break;
                    }
                }

                boolean CnotAS = true;
                char[] AS = {'<', '=', '/', '>'};
                for (char ca : AS) {
                    if (c == ca) {
                        CnotAS = false;
                    }
                }
                if (CnotAS) {
                    if (gettingTag) {
                        itemTag += c;
                    } else if (gettingObjectStringLength) {
                        length += c;
                    } else if (gettingObjectString) {
                        itemDetails += c;
                    }
                }

                if (skipDetails) {
                    reader.skip(Long.parseLong(length));

                    itemTag = "";
                    itemDetails = "";
                    length = "";
                    wrongTag = false;
                    skipDetails = false;
                    gettingTag = false;
                    gettingObjectString = false;
                    gettingObjectStringLength = false;
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "getItem: IOException check stack trace");
        }

        if (itemDetails == "") {
            return null;
        }

        return itemDetails;
    }
}
