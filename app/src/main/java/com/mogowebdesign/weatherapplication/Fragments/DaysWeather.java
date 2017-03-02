package com.mogowebdesign.weatherapplication.Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mogowebdesign.weatherapplication.Adapters.RecyclerDayilyAdapter;
import com.mogowebdesign.weatherapplication.Model.Day;
import com.mogowebdesign.weatherapplication.Model.Days;
import com.mogowebdesign.weatherapplication.Providers.DayWeatherProviderContract;
import com.mogowebdesign.weatherapplication.R;

/**
 * Created by rijogeorge on 3/2/17.
 */

public class DaysWeather extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_days_weather, container, false);
        RecyclerView mRecyclerViewDay=(RecyclerView) view.findViewById(R.id.my_recycler_view_dayily);
        mRecyclerViewDay.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManagerDay=new LinearLayoutManager(getContext());
        mRecyclerViewDay.setLayoutManager(mLayoutManagerDay);
        String[] projection={"day"};
        Cursor cursor=getActivity().getContentResolver().query(DayWeatherProviderContract.CONTENT_URI_GET_ALL,null,null,null,null);
        //fill dummy dayily data
        Days days=fillDaylist(cursor);
        cursor.close();
        RecyclerView.Adapter mAdapterDay=new RecyclerDayilyAdapter(days.getDaysList());
        mRecyclerViewDay.setAdapter(mAdapterDay);
        return view;
    }

    private Days fillDaylist(Cursor cursor) {
        String day;
        int min,max;
        Days days=new Days();
        while(cursor.moveToNext()) {
            day=cursor.getString(cursor.getColumnIndex(DayWeatherProviderContract.DayWeatherDB.COLUMN_NAME_DAY));
            max=cursor.getInt(cursor.getColumnIndex(DayWeatherProviderContract.DayWeatherDB.COLUMN_NAME_MAX_TEMP));
            min=cursor.getInt(cursor.getColumnIndex(DayWeatherProviderContract.DayWeatherDB.COLUMN_NAME_MIN_TEMP));
            days.getDaysList().add(new Day(day,max,min));
        }
        cursor.close();
        return days;
    }

}