package com.example.weatherforecastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
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
import android.util.Log;
import android.view.Menu;
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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements HourTemperatureAdapter.OnHourClick {
    public static final String API_KEY = "";
    private final String place_API = "";
    // API keys are removed due to privacy issue
    private static final String TAG = "MainActivity";
    private int AUTOCOMPLETE_REQUEST_CODE = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    public static final int locationRequestCode = 1000;
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
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String myPref = "com.example.latlong";
    public static final String LAT = "com.example.let";
    public static final String LONG = "com.example.long";

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotificationWorker.context = MainActivity.this;
        setTitle("Main window");
        context = this;
        dashBoardPB = findViewById(R.id.dashBoardPB);
        currentTempTV = findViewById(R.id.currentTempTV);
        weatherCondTV = findViewById(R.id.weatherCondTV);
        cityTV = findViewById(R.id.cityTV);
        hourTemperatureRV = findViewById(R.id.hourTemperatureRV);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        apiInterface = APIClient.getRetrofitInstance().create(APIInterface.class);
        sharedPreferences = getSharedPreferences(myPref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        chart = findViewById(R.id.chart);
        getCurrentLoc();
        setNotification();

    }

    private void setNotification() {
        Log.i(TAG, "setNotification: " + " protick");
        Constraints constraints = new Constraints.Builder().
                setRequiresDeviceIdle(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        final PeriodicWorkRequest periodicWorkRequestNotification
                = new PeriodicWorkRequest.Builder(NotificationWorker.class, 20, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this).enqueue(periodicWorkRequestNotification);
    }


    private void loadData(final Double lat, final Double lon) {

        dashBoardPB.setVisibility(View.VISIBLE);

        Log.i(TAG, "loadData: " + API_KEY + lat + lon);
        Call<WeatherModel> call = apiInterface.getWeatherData(API_KEY, lat, lon, "si");
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
                            weeklyData = weatherModel.getDaily().getData();
// weatherModel.getCurrently().
// ArrayList<Entry>
                            entryArrayList = new ArrayList<Entry>();
                            for (int i = 0; i < weeklyData.size(); i++) {
                                double x = weeklyData.get(i).getTemperatureHigh();
                                int z = (int) x;
                                String label = weeklyData.get(i).getTime().toString();
                                entryArrayList.add(new Entry(i, z));

// entryArrayList.add(new Entry(i,weeklyData.get(i).getTemperatureHigh()));
                            }


                        }
                        adpter = new HourTemperatureAdapter(context, hourlyWeathetList, MainActivity.this, weatherModel);
                        hourTemperatureRV.setAdapter(adpter);
                        currentTempTV.setText(currentlyWeather.getTemperature().toString() + (char) 0x00B0 + "C");
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        try {
                            addresses = geocoder.getFromLocation(lat, lon, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        cityName = addresses.get(0).getAddressLine(0);
                        weatherCondTV.setText(currentlyWeather.getSummary());
                        cityTV.setText(cityName);
                        LineDataSet lineData = new LineDataSet(entryArrayList, "maximum Weekly temperature");
                        lineData.setLineWidth(2.50f);
                        LineData data = new LineData(lineData);

                        XAxis xAxis = chart.getXAxis();
                        xAxis.setDrawGridLines(false);
                        Description description = new Description();
                        description.setText("");
                        chart.setDescription(description);

                        chart.setData(data);
                        chart.animateX(1500);
                        chart.invalidate();

                    } else {
                        Toast.makeText(MainActivity.this, "something went to wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                dashBoardPB.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "something..." + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.i(TAG, "onFailure: " + t.getLocalizedMessage());

            }
        });

    }

    private void getCurrentLoc() {

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
                            String lat = String.valueOf(wayLatitude);
                            String longitude = String.valueOf(wayLongitude);
                            editor.putString(LAT, lat);
                            editor.putString(LONG, longitude);
                            editor.apply();
                            loadData(wayLatitude, wayLongitude);

                            Log.i(TAG, "onSuccess: " + "Lat: " + wayLatitude + "Logn: " + wayLongitude);

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
                        String lat = String.valueOf(wayLatitude);
                        String longitude = String.valueOf(wayLongitude);
                        editor.putString(LAT, lat);
                        editor.putString(LONG, longitude);
                        editor.apply();
                        loadData(wayLatitude, wayLongitude);

                        Log.i(TAG, "onSuccess: " + "Lat: " + wayLatitude + "Logn: " + wayLongitude);
                    }

                }
            });

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                        if (location != null) {
                            wayLatitude = location.getLatitude();
                            wayLongitude = location.getLongitude();
                            String lat = String.valueOf(wayLatitude);
                            String longitude = String.valueOf(wayLongitude);
                            editor.putString(LAT, lat);
                            editor.putString(LONG, longitude);
                            editor.apply();
                            loadData(wayLatitude, wayLongitude);
                            Log.i(TAG, "onSuccess: " + "Lat: " + wayLatitude + "Logn: " + wayLongitude);

                        }

                    });
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        searchIntent = null;
        addresses = null;
        super.onDestroy();
    }

    @Override
    public void onClick(int position) {

        WeatherModel.Hourly.Datum data = hourlyWeathetList.get(position);
        Intent intent = new Intent(this, DetailWeather.class);
        intent.putExtra("DATA", data);
        intent.putExtra("TIMEZOME", weatherModel.getTimezone());
        intent.putExtra("CITY", cityName);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.menuSettingsBT) {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.menuSearchBT) {

            if (!Places.isInitialized()) {
                Places.initialize(getApplicationContext(), place_API);
            }

            PlacesClient placesClient = Places.createClient(this);


            List<Place.Field> fields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.ID, Place.Field.NAME);

            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getLatLng());
                LatLng latLng = place.getLatLng();
                final double latti = latLng.latitude;
                final double longi = latLng.longitude;
                Intent intent = new Intent(getApplicationContext(), SearchLocation.class);
                intent.putExtra("latti", latti);
                intent.putExtra("longi", longi);
                startActivity(intent);


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

}