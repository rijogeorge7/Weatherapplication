package com.mogowebdesign.weatherapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mogowebdesign.weatherapplication.Adapters.RecyclerDayilyAdapter;
import com.mogowebdesign.weatherapplication.Adapters.RecyclerHourAdapter;
import com.mogowebdesign.weatherapplication.Model.Day;
import com.mogowebdesign.weatherapplication.Model.Days;
import com.mogowebdesign.weatherapplication.Model.Hour;
import com.mogowebdesign.weatherapplication.Model.Hours;
import com.mogowebdesign.weatherapplication.Services.WeatherDataService;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private WeatherDataService mWeatherDataService;

    private RecyclerView mRecyclerViewHour;
    private RecyclerView.Adapter mAdapterHour;
    private RecyclerView.LayoutManager mLayoutManagerHour;
    private RecyclerView mRecyclerViewDay;
    private RecyclerView.Adapter mAdapterDay;
    private RecyclerView.LayoutManager mLayoutManagerDay;

    @Override
    protected void onResume() {
        super.onResume();
        Intent serviceBindIntent=new Intent(this,WeatherDataService.class);
        bindService(serviceBindIntent,this,BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {

        super.onPause();
        if(mWeatherDataService!=null) {
            unbindService(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerViewHour =(RecyclerView) findViewById(R.id.my_recycler_view_hour);
        mRecyclerViewHour.setHasFixedSize(true);
        mLayoutManagerHour = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerViewHour.setLayoutManager(mLayoutManagerHour);
        //for filling dummy data
        Hours hours=fillHourlist();
        mAdapterHour =new RecyclerHourAdapter(hours.getHoursList());
        mRecyclerViewHour.setAdapter(mAdapterHour);
        mRecyclerViewDay=(RecyclerView) findViewById(R.id.my_recycler_view_dayily);
        mRecyclerViewDay.setHasFixedSize(true);
        mLayoutManagerDay=new LinearLayoutManager(this);
        mRecyclerViewDay.setLayoutManager(mLayoutManagerDay);
        //fill dummy dayily data
        Days days=fillDaylist();
        mAdapterDay=new RecyclerDayilyAdapter(days.getDaysList());
        mRecyclerViewDay.setAdapter(mAdapterDay);
    }

    private Hours fillHourlist() {
        Hours hours=new Hours();
        for (int i=10;i<=25;i++) {
            hours.getHoursList().add(new Hour("11",R.drawable.sunny,55));
        }
        return hours;
    }
    private Days fillDaylist() {
        Days days=new Days();
        for (int i=10;i<=25;i++) {
            days.getDaysList().add(new Day("Saturday",62,51));
        }
        return days;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mWeatherDataService=((WeatherDataService.LocalBinder) iBinder).getService();
        mWeatherDataService.updateWeatherData();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mWeatherDataService=null;
    }
}
