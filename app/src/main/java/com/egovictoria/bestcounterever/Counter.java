package com.egovictoria.bestcounterever;

public class Counter {

    private boolean isSelected;
    private int count;
    private String name;

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
}
