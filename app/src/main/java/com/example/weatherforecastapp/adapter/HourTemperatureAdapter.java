package com.example.weatherforecastapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecastapp.R;
import com.example.weatherforecastapp.pojo.WeatherModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HourTemperatureAdapter extends RecyclerView.Adapter<HourTemperatureAdapter.MyViewHolder> {
    private Context context;
    private List<WeatherModel.Hourly.Datum> hourDataList;
    private OnHourClick onHourClick;
    private WeatherModel weatherModel;

    public HourTemperatureAdapter(Context context, List<WeatherModel.Hourly.Datum> hourDataList, OnHourClick onHourClick, WeatherModel weatherModel) {
        this.context = context;
        this.hourDataList = hourDataList;
        this.onHourClick = onHourClick;
        this.weatherModel = weatherModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_hour_item, parent, false);
        return new MyViewHolder(view, onHourClick);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Date date = new java.util.Date(hourDataList.get(position).getTime() * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("hh aa");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(weatherModel.getTimezone()));
        String formattedDate = sdf.format(date);
        holder.hourTV.setText(formattedDate);
        holder.hourTempTV.setText(hourDataList.get(position).getTemperature().toString() + (char) 0x00B0 + "C");
        if (hourDataList.get(position).getIcon().equals("clear-day")) {
            holder.conditionIV.setBackgroundResource(R.drawable.ic_clear_day);
        } else if (hourDataList.get(position).getIcon().equals("partly-cloudy-day")) {
            holder.conditionIV.setBackgroundResource(R.drawable.ic_partly_cloudy_day);
        } else if (hourDataList.get(position).getIcon().equals("partly_cloudy_night")) {
            holder.conditionIV.setBackgroundResource(R.drawable.ic_partly_cloudy_night);
        } else if (hourDataList.get(position).getIcon().equals("cloudy")) {
            holder.conditionIV.setBackgroundResource(R.drawable.ic_cloudy);
        } else if (hourDataList.get(position).getIcon().equals("rain")) {
            holder.conditionIV.setBackgroundResource(R.drawable.ic_rain);
        } else if (hourDataList.get(position).getIcon().equals("sleet")) {
            holder.conditionIV.setBackgroundResource(R.drawable.ic_sleet);
        } else if (hourDataList.get(position).getIcon().equals("snow")) {
            holder.conditionIV.setBackgroundResource(R.drawable.ic_snow);
        } else if (hourDataList.get(position).getIcon().equals("wind")) {
            holder.conditionIV.setBackgroundResource(R.drawable.ic_wind);
        } else if (hourDataList.get(position).getIcon().equals("fog")) {
            holder.conditionIV.setBackgroundResource(R.drawable.ic_fog);
        }

    }


    @Override
    public int getItemCount() {
        return hourDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnHourClick onHourClick;
        private TextView hourTV;
        private ImageView conditionIV;
        private TextView hourTempTV;

        public MyViewHolder(@NonNull View itemView, OnHourClick onHourClick) {
            super(itemView);
            hourTV = itemView.findViewById(R.id.hourTV);
            conditionIV = itemView.findViewById(R.id.conditionIV);
            hourTempTV = itemView.findViewById(R.id.hourTempTV);
            this.onHourClick = onHourClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            onHourClick.onClick(getAdapterPosition());

        }
    }

    public interface OnHourClick {
        void onClick(int position);
    }
}
