package com.egovictoria.bestcounterever;

public class Counter {

    private boolean isSelected;
    private int count;
    private String name;


    public Counter(String stringCounter) {
        boolean getName = false;
        boolean getCount = false;
        boolean waitOne = false;
        String n = "";
        String c = "";
        for (int i = 0; i < stringCounter.length(); i++) {
            char s = stringCounter.charAt(i);
            if (s == '{') {
                getName = true;
                waitOne = true;
            } else if (s == '|'){
                getName = false;
                getCount = true;
                waitOne = true;
            } else if (s == '}') {
                break;
            }

            if (!waitOne) {
                if (getName) {
                    n += s;
                } else if (getCount) {
                    c += s;
                }
            }

            waitOne = false;
        }

        name = n;
        count = Integer.parseInt(c);
        isSelected = false;
    }

    public Counter() {
        count = 0;
        isSelected = false;
        name = "counter";
    }

    public Counter(int count) {
        this.count = count;
        isSelected = false;
        name = "counter";
    }

    public Counter(int count, String name) {
        this.count = count;
        isSelected = false;
        this.name = name;
    }

    void setName (String name) {
        this.name = name;
    }

    String getName () {
        return name;
    }

    void setSelected(boolean s) {
        isSelected = s;
    }

    boolean isSelected() {
        return isSelected;
    }

    void increment() {
        count += AppConstants.standardDeviation;
    }

    void decrement() {
        count -= AppConstants.standardDeviation;
    }

    int getCount() {
        return count;
    }

    void setCount(int x) {
        count = x;
    }

    // returns the counter object as  "{[name],[count]}"
    String getString() {
        return "{" + name + "|" + count + "}";
    }
}
