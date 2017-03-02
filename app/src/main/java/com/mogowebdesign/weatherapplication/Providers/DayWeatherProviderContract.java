package com.mogowebdesign.weatherapplication.Providers;


import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by rijogeorge on 2/22/17.
 */

public class DayWeatherProviderContract {

    public static final String AUTHORITY = "com.mogowebdesign.weatherapplication";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri CONTENT_URI_GET_ALL = Uri.parse("content://" + AUTHORITY+"/dayWeatherListGetAll");

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DayWeatherProviderContract() {}

    public static class DayWeatherDB implements BaseColumns {
        public static final String TABLE_NAME = "day_weather";
        public static final String COLUMN_NAME_DAY = "day";
        public static final String COLUMN_NAME_MAX_TEMP = "max_temp";
        public static final String COLUMN_NAME_MIN_TEMP = "min_temp";
    }
}
