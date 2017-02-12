package com.mogowebdesign.weatherapplication.Model;

/**
 * Created by rijogeorge on 2/7/17.
 */

public class Hour {
    private String time;
    private int imageId;
    private int temp;

    public Hour(String time, int imageId, int temp) {
        this.time=time;
        this.imageId=imageId;
        this.temp=temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
