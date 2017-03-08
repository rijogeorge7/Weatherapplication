package com.mogowebdesign.weatherapplication.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mogowebdesign.weatherapplication.Model.Day;
import com.mogowebdesign.weatherapplication.R;

import java.util.ArrayList;

/**
 * Created by rijogeorge on 2/8/17.
 */

public class RecyclerDayilyAdapter extends RecyclerView.Adapter<RecyclerDayilyAdapter.ViewHolder> {

    private ArrayList<Day> daysList;
    public RecyclerDayilyAdapter(ArrayList<Day> daysList) {
        this.daysList=daysList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dayily_item,parent,false);
        RecyclerDayilyAdapter.ViewHolder vh=new RecyclerDayilyAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.day.setText(daysList.get(position).getDay());
        holder.tempHigh.setText(String.valueOf(daysList.get(position).getTempHigh()));
        holder.tempLow.setText(String.valueOf(daysList.get(position).getTempLow()));
    }

    @Override
    public int getItemCount() {
        if(daysList==null)
            return 0;
        return daysList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView day,tempLow,tempHigh;
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            day=(TextView) itemView.findViewById(R.id.dayTextId);
            tempHigh=(TextView) itemView.findViewById(R.id.tempHighId);
            tempLow=(TextView) itemView.findViewById(R.id.tempLowId);
            image=(ImageView) itemView.findViewById(R.id.weatherImageId);

        }
    }

}
