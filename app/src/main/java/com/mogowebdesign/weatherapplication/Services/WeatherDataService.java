package com.mogowebdesign.weatherapplication.Services;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Trace;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.mogowebdesign.weatherapplication.Constants;
import com.mogowebdesign.weatherapplication.Model.Day;
import com.mogowebdesign.weatherapplication.Model.Days;
import com.mogowebdesign.weatherapplication.Providers.DayWeatherProviderContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

    private void putIntoDatabase(Days days) {

        ContentValues[] contentValuesArray=new ContentValues[days.getDaysList().size()];
        //ArrayList<ContentValues> contentValuesList = new ArrayList<>();
        int count =0;
        for(Day day : days.getDaysList()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DayWeatherProviderContract.DayWeatherDB.COLUMN_NAME_DAY,day.getDay());
            contentValues.put(DayWeatherProviderContract.DayWeatherDB.COLUMN_NAME_MAX_TEMP,day.getTempHigh());
            contentValues.put(DayWeatherProviderContract.DayWeatherDB.COLUMN_NAME_MIN_TEMP,day.getTempLow());
            contentValuesArray[count] = contentValues;
            count++;
            //contentValuesList.add(contentValues);
            //contentValues.clear();
        }
        //contentValues.put(DayWeatherProviderContract.DayWeatherDB.COLUMN_NAME_DAY,days.getDaysList().get(0).getDay());
        //contentValues.put(DayWeatherProviderContract.DayWeatherDB.COLUMN_NAME_MAX_TEMP,days.getDaysList().get(0).getTempHigh());
        //contentValues.put(DayWeatherProviderContract.DayWeatherDB.COLUMN_NAME_MIN_TEMP,days.getDaysList().get(0).getTempLow());

        //Uri uri = getContentResolver().insert(DayWeatherProviderContract.CONTENT_URI,contentValues);
        //Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();

        int inserted = getContentResolver().bulkInsert(DayWeatherProviderContract.CONTENT_URI,contentValuesArray);
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
            //Trace.beginSection("ProcessPeople");
            Days days=parseWeatherJson(jsonStr);
            //Trace.endSection();
            putIntoDatabase(days);

        }
    }

}
