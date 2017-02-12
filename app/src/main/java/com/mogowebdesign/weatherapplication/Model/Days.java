package com.mogowebdesign.weatherapplication.Model;

import java.util.ArrayList;

/**
 * Created by rijogeorge on 2/8/17.
 */

public class Days {
    ArrayList<Day> daysList=new ArrayList<>();

    public ArrayList<Day> getDaysList() {
        return daysList;
    }

    public void setDaysList(ArrayList<Day> daysList) {
        this.daysList = daysList;
    }
}
