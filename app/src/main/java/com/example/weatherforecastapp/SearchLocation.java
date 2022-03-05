package com.example.weatherforecastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherforecastapp.adapter.HourTemperatureAdapter;
import com.example.weatherforecastapp.network.APIClient;
import com.example.weatherforecastapp.network.APIInterface;
import com.example.weatherforecastapp.pojo.WeatherModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchLocation extends AppCompatActivity implements HourTemperatureAdapter.OnHourClick {
    private static final String TAG = "MainActivity";
    private final String apiKey = "a904ffebe97fe0df6761b6c4aba3c35a";
    private final String place_API = "AIzaSyCUVLz4EaQY1SSnGJasqPKeGvyXoLfeRDY";
    private int AUTOCOMPLETE_REQUEST_CODE = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    private int locationRequestCode = 1000;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private TextView currentTempTV;
    private TextView weatherCondTV;
    private TextView cityTV;
    private ProgressBar dashBoardPB;
    private APIInterface apiInterface;
    private WeatherModel weatherModel;
    private WeatherModel.Currently currentlyWeather;
    private RecyclerView hourTemperatureRV;
    private HourTemperatureAdapter adpter;
    private List<WeatherModel.Hourly.Datum> hourlyWeathetList;
    private Context context;
    private Intent searchIntent;
    private String cityName;
    private List<Address> addresses = null;
    private LineChart chart;
    private List<WeatherModel.Daily.Datum_> weeklyData;
    private ArrayList<Entry> entryArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Search result");
        context = this;
        dashBoardPB = findViewById(R.id.dashBoardPB);
        currentTempTV = findViewById(R.id.currentTempTV);
        weatherCondTV = findViewById(R.id.weatherCondTV);
        cityTV = findViewById(R.id.cityTV);
        hourTemperatureRV = findViewById(R.id.hourTemperatureRV);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        apiInterface = APIClient.getRetrofitInstance().create(APIInterface.class);
        chart = findViewById(R.id.chart);

        //    getCurrentLoc();
        Intent intent = getIntent();
        double latti = intent.getDoubleExtra("latti", 0);
        double longi = intent.getDoubleExtra("longi", 0);
        loadData(latti, longi);


    }


    private void loadData(final Double lat, final Double lon) {

        dashBoardPB.setVisibility(View.VISIBLE);

        Log.i(TAG, "loadData: " + apiKey + lat + lon);
        Call<WeatherModel> call = apiInterface.getWeatherData(apiKey, lat, lon, "si");
        call.enqueue(new Callback<WeatherModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (response.isSuccessful()) {
                    dashBoardPB.setVisibility(View.GONE);
                    if (response.code() == 200) {
                        weatherModel = response.body();
                        if (weatherModel != null) {
                            currentlyWeather = weatherModel.getCurrently();
                            hourlyWeathetList = weatherModel.getHourly().getData();
                            weeklyData = weatherModel.getDaily().getData();
                            //      weatherModel.getCurrently().
                            //  ArrayList<Entry>
                            entryArrayList = new ArrayList<Entry>();
                            for (int i = 0; i < weeklyData.size(); i++) {
                                double x = weeklyData.get(i).getTemperatureHigh();
                                int z = (int) x;
                                String label = weeklyData.get(i).getTime().toString();
                                entryArrayList.add(new Entry(i, z));

                                //  entryArrayList.add(new Entry(i,weeklyData.get(i).getTemperatureHigh()));
                            }


                        }
                        adpter = new HourTemperatureAdapter(context, hourlyWeathetList, SearchLocation.this, weatherModel);
                        hourTemperatureRV.setAdapter(adpter);
                        currentTempTV.setText(currentlyWeather.getTemperature().toString() + (char) 0x00B0 + "C");
                        Geocoder geocoder = new Geocoder(SearchLocation.this, Locale.getDefault());
                        try {
                            addresses = geocoder.getFromLocation(lat, lon, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        cityName = addresses.get(0).getAddressLine(0);
                        weatherCondTV.setText(currentlyWeather.getSummary());
                        cityTV.setText(cityName);
                        LineDataSet lineData = new LineDataSet(entryArrayList, "Weekly temperature");

                        LineData data = new LineData(lineData);
                        chart.setData(data);
                        chart.animateX(1500);
                        chart.invalidate();



                    } else {
                        Toast.makeText(SearchLocation.this, "something went  wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                dashBoardPB.setVisibility(View.GONE);
                Toast.makeText(SearchLocation.this, "something..." + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.i(TAG, "onFailure: " + t.getLocalizedMessage());

            }
        });

    }

  /*  private void getCurrentLoc() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // check permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // reuqest for permission
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, locationRequestCode);

            } else {
                // already permission granted
                mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            wayLatitude = location.getLatitude();
                            wayLongitude = location.getLongitude();
                            loadData(wayLatitude, wayLongitude);
                            Log.i(TAG, "onSuccess: " + "Lat: " + wayLatitude + "Logn: " + wayLongitude);
//                            Toast.makeText(MainActivity.this, "Lat: " + wayLatitude + "Logn: " + wayLongitude, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        loadData(wayLatitude, wayLongitude);
                        Log.i(TAG, "onSuccess: " + "Lat: " + wayLatitude + "Logn: " + wayLongitude);
//                        Toast.makeText(MainActivity.this, "Lat: " + wayLatitude + "Logn: " + wayLongitude, Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }*/

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                wayLatitude = location.getLatitude();
                                wayLongitude = location.getLongitude();
                                loadData(wayLatitude, wayLongitude);
                                Log.i(TAG, "onSuccess: " + "Lat: " + wayLatitude + "Logn: " + wayLongitude);
//                                Toast.makeText(MainActivity.this, "Lat: " + wayLatitude + "Logn: " + wayLongitude, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }*/


    @Override
    public void onClick(int position) {

        WeatherModel.Hourly.Datum data = hourlyWeathetList.get(position);
        Intent intent = new Intent(this, DetailWeather.class);
        intent.putExtra("DATA", data);
        intent.putExtra("TIMEZOME", weatherModel.getTimezone());
        intent.putExtra("CITY", cityName);
        startActivity(intent);

    }


}
