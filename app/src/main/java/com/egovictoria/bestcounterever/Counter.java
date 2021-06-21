package com.egovictoria.bestcounterever;

public class Counter {

    private boolean isSelected;
    private int count;

    public Counter() {
        count = 0;
        isSelected = false;
    }

    public Counter(int x) {
        count = x;
        isSelected = false;
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
