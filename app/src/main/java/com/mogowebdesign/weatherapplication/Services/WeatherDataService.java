package com.mogowebdesign.weatherapplication.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mogowebdesign.weatherapplication.Constants;
import com.mogowebdesign.weatherapplication.Model.Day;
import com.mogowebdesign.weatherapplication.Model.Days;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rijogeorge on 2/11/17.
 */

public class WeatherDataService extends Service {

    private LocalBinder mLocalBinder=new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }

    public void updateWeatherData() {
        new MyThread().start();
    }

    private String downloadDataFromServer() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.yahooWeatherUrl)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private Days parseWeatherJson(String jsonStr) {
        Days days=new Days();
        try {
            Day day;
            JSONObject jsonObj = new JSONObject(jsonStr);
            jsonObj=jsonObj.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item");
            JSONArray jsonArrayForecast=jsonObj.getJSONArray("forecast");
            for(int i=0;i<jsonArrayForecast.length();i++) {
                JSONObject jsonObjDay=jsonArrayForecast.getJSONObject(i);
                String dayName=jsonObjDay.getString("day");
                int maxTemp=jsonObjDay.getInt("high");
                int minTemp=jsonObjDay.getInt("low");
                day=new Day(dayName,maxTemp,minTemp);
                days.getDaysList().add(day);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return days;
    }

    public class LocalBinder extends Binder {
        public WeatherDataService getService() {
            return WeatherDataService.this;
        }
    }

    private class MyThread extends Thread {

        @Override
        public void run() {
            String jsonStr;
            try {
                jsonStr=downloadDataFromServer();
            } catch (IOException e) {
                e.printStackTrace();
                jsonStr=null;
            }
            Days days=parseWeatherJson(jsonStr);

        }
    }

}
