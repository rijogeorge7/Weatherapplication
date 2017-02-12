package com.mogowebdesign.weatherapplication.Model;

/**
 * Created by rijogeorge on 2/8/17.
 */

public class Day {
    private String day;
    private int tempHigh,tempLow;

    public Day(String day, int tempHigh, int tempLow) {
        this.day = day;
        this.tempHigh = tempHigh;
        this.tempLow = tempLow;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getTempHigh() {
        return tempHigh;
    }

    public void setTempHigh(int tempHigh) {
        this.tempHigh = tempHigh;
    }

    public int getTempLow() {
        return tempLow;
    }

    public void setTempLow(int tempLow) {
        this.tempLow = tempLow;
    }
}
