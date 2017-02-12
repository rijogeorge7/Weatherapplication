package com.mogowebdesign.weatherapplication.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mogowebdesign.weatherapplication.Model.Hour;
import com.mogowebdesign.weatherapplication.R;

import java.util.ArrayList;

/**
 * Created by rijogeorge on 2/7/17.
 */

public class RecyclerHourAdapter extends RecyclerView.Adapter<RecyclerHourAdapter.ViewHolder> {

    private ArrayList<Hour> hoursList;
    //private Context context;
    public RecyclerHourAdapter(ArrayList<Hour> hoursList) {
        this.hoursList=hoursList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //context=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_item,parent,false);
        ViewHolder vh=new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.time.setText(hoursList.get(position).getTime());
        //holder.img.setImageDrawable(context.getResources().getDrawable(hoursList.get(position).getImageId()));
        holder.temp.setText(String.valueOf(hoursList.get(position).getTemp()));
    }

    @Override
    public int getItemCount() {
        //return hoursList.size();
        return 15;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time,temp;
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            time=(TextView) itemView.findViewById(R.id.timeId);
            img=(ImageView) itemView.findViewById(R.id.weatherImageId);
            temp=(TextView) itemView.findViewById(R.id.tempId);
        }
    }
}
