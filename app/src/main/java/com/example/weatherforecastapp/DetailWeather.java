package com.example.weatherforecastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherforecastapp.pojo.WeatherModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailWeather extends AppCompatActivity {
    private TextView deatilTimeTV;
    private ImageView detailIconIV;
    private TextView detailCurrentTempTV;
    private TextView detailWeatherCondTV;
    private TextView detailCityTV;
    private TextView detailHumidityTV;
    private TextView detailWindSpeedTV;
    private TextView detailCloudCoverTV;
    private TextView detailprecipTypeTV;
    private String timeZone;
    private String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);
        deatilTimeTV = findViewById(R.id.deatilTimeTV);
        detailIconIV = findViewById(R.id.detailIconIV);
        detailCurrentTempTV = findViewById(R.id.detailCurrentTempTV);
        detailWeatherCondTV = findViewById(R.id.detailWeatherCondTV);
        detailCityTV = findViewById(R.id.detailCityTV);
        detailHumidityTV = findViewById(R.id.detailHumidityTV);
        detailWindSpeedTV = findViewById(R.id.detailWindSpeedTV);
        detailCloudCoverTV = findViewById(R.id.detailCloudCoverTV);
        detailprecipTypeTV = findViewById(R.id.detailprecipTypeTV);

        Intent intent = getIntent();
        WeatherModel.Hourly.Datum data = (WeatherModel.Hourly.Datum) intent.getSerializableExtra("DATA");
        timeZone = intent.getStringExtra("TIMEZOME");
        city = intent.getStringExtra("CITY");
        detailCurrentTempTV.setText(data.getTemperature().toString() + (char) 0x00B0 + "C");
        Date date = new java.util.Date(data.getTime() * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("hh aa");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));
        String formattedDate = sdf.format(date);
        detailCityTV.setText(city);
        deatilTimeTV.setText("Time " + formattedDate);
        detailWeatherCondTV.setText(data.getSummary());
        detailHumidityTV.setText("Humidity " + data.getHumidity().toString() + "%");
        detailWindSpeedTV.setText("Wind Speed " + data.getWindSpeed().toString() + "%");
        detailCloudCoverTV.setText("Cloud Cover" + data.getCloudCover().toString() + "%");
        detailprecipTypeTV.setText("Precipitation " + data.getPrecipProbability() + "%");

        if (data.getIcon().equals("clear-day")) {
            detailIconIV.setBackgroundResource(R.drawable.ic_clear_day);
        } else if (data.getIcon().equals("partly-cloudy-day")) {
            detailIconIV.setBackgroundResource(R.drawable.ic_partly_cloudy_day);
        } else if (data.getIcon().equals("partly_cloudy_night")) {
            detailIconIV.setBackgroundResource(R.drawable.ic_partly_cloudy_night);
        } else if (data.getIcon().equals("cloudy")) {
            detailIconIV.setBackgroundResource(R.drawable.ic_cloudy);
        } else if (data.getIcon().equals("rain")) {
            detailIconIV.setBackgroundResource(R.drawable.ic_rain);
        } else if (data.getIcon().equals("sleet")) {
            detailIconIV.setBackgroundResource(R.drawable.ic_sleet);
        } else if (data.getIcon().equals("snow")) {
            detailIconIV.setBackgroundResource(R.drawable.ic_snow);
        } else if (data.getIcon().equals("wind")) {
            detailIconIV.setBackgroundResource(R.drawable.ic_wind);
        } else if (data.getIcon().equals("fog")) {
            detailIconIV.setBackgroundResource(R.drawable.ic_fog);
        }


    }
}
