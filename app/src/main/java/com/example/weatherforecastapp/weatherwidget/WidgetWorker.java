package com.example.weatherforecastapp.weatherwidget;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.weatherforecastapp.MainActivity;
import com.example.weatherforecastapp.R;
import com.example.weatherforecastapp.adapter.HourTemperatureAdapter;
import com.example.weatherforecastapp.network.APICLientPolution;
import com.example.weatherforecastapp.network.APIClient;
import com.example.weatherforecastapp.network.APIInterface;
import com.example.weatherforecastapp.pojo.WeatherModel;
import com.example.weatherforecastapp.pojo.WeatherPolutionModel;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.weatherforecastapp.MainActivity.API_KEY;
import static com.example.weatherforecastapp.MainActivity.LAT;
import static com.example.weatherforecastapp.MainActivity.LONG;
import static com.example.weatherforecastapp.MainActivity.myPref;

public class WidgetWorker extends Worker {
    private boolean locationFlag = false;
    private boolean locationEnvironmentPollution = false;
    private boolean locationHighTemp = false;
    private boolean locationLowTemp = false;
    private boolean locationWorstWeatherCity = false;
    private boolean locationBestWeatherCity = false;

    public static final String CHANNEL_ID = "com.example.weatherChannel";
    public static final String environmentPollution = "com.example.environmentpollution";
    public static final String highTemp = "com.example.highTemp";
    public static final String lowTemp = "com.example.lowTemp";
    public static final String worstWeatherCity = "com.example.worstweathercity";
    public static final String bestWeatherCity = "com.example.bestweathercity";
    public static final String API_KEY_FOR_POLUTION = "3c2b41a6-8dc3-496a-afa6-d3b83d52a1cd";
    private APIInterface apiInterface;
    private APIInterface apiInterfacePolution;
    private WeatherModel weatherModel;
    private WeatherModel.Currently currentlyWeather;
    private static final String TAG = "WidgetWorker";
    private List<Address> addresses = null;
    private WeatherPolutionModel weatherPolutionModel;
    private WeatherPolutionModel.Data weatherData;
    private WeatherPolutionModel.Data.Current.Weather currentWeather;
    private WeatherPolutionModel.Data.Current.Pollution polution;
    public static final int locationRequestCode = 1000;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private FusedLocationProviderClient mFusedLocationClient;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public WidgetWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        apiInterface = APIClient.getRetrofitInstance().create(APIInterface.class);
        apiInterfacePolution = APICLientPolution.getRetrofitInstance().create(APIInterface.class);
        sharedPreferences = context.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }


    @NonNull
    @Override
    public Result doWork() {

//        getAllData();

        String lat = sharedPreferences.getString(LAT, "");
        String lon = sharedPreferences.getString(LONG, "");
        Double doubleLat = Double.parseDouble(lat);
        Double doubleLong = Double.parseDouble(lon);
        loadData(doubleLat, doubleLong);
//        loadPolutionData(doubleLat, doubleLong);
        return Result.success();
    }

    private void getAllData() {
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Location temp = new Location(LocationManager.GPS_PROVIDER);
                    String lat = sharedPreferences.getString(LAT, "");
                    String lon = sharedPreferences.getString(LONG, "");
                    Double doubleLat = Double.parseDouble(lat);
                    Double doubleLong = Double.parseDouble(lon);
                    temp.setLatitude(doubleLat);
                    temp.setLongitude(doubleLong);
                    float distance = location.distanceTo(temp);
                    if (distance > 500) {
                        editor.putBoolean("FLAG", true);
                        editor.apply();
                        Log.i("locationC", "onSuccess: " + distance);
                    } else {

                        editor.putBoolean("FLAG", false);
                        editor.apply();
                        Log.i("locationC", "onSuccess: " + "false");

                    }
                    wayLatitude = location.getLatitude();
                    wayLongitude = location.getLongitude();
                    lat = String.valueOf(wayLatitude);
                    lon = String.valueOf(wayLongitude);
                    editor.putString(LAT, lat);
                    editor.putString(LONG, lon);
                    editor.apply();
                    loadData(wayLatitude, wayLongitude);
//                    loadPolutionData(doubleLat, doubleLong);

                    Log.i(TAG, "onSuccess: " + "Lat: " + wayLatitude + "Logn: " + wayLongitude);


                }

            }
        });
    }


   /* public void loadPolutionData(final Double lat, final Double lon) {

        Log.i(TAG, "loadPolutionData: pol " + lat + "   " + lon);

        Call<WeatherPolutionModel> call = apiInterfacePolution.getPolutionData(lat, lon, API_KEY_FOR_POLUTION);
        call.enqueue(new Callback<WeatherPolutionModel>() {
            @Override
            public void onResponse(Call<WeatherPolutionModel> call, Response<WeatherPolutionModel> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        weatherPolutionModel = response.body();
                        weatherData = weatherPolutionModel.getData();
                        currentWeather = weatherData.getCurrent().getWeather();
                        polution = weatherData.getCurrent().getPollution();
                        Log.i("Aquis", "dd " + polution.getAqius());
                        if (polution.getAqius() >= 0 && polution.getAqius() <= 50) {
                            displayNotification("Weather Alert!", "Breath! Healthy Weather", 1);
                        } else if (polution.getAqius() >= 51 && polution.getAqius() <= 100) {
                            displayNotification("Weather Alert!", "This weather is Moderate", 1);
                        } else if (polution.getAqius() >= 101 && polution.getAqius() <= 150) {
                            displayNotification("Weather Alert!", "Unhealthy for senstive people", 1);
                        } else if (polution.getAqius() >= 151 && polution.getAqius() <= 200) {
                            displayNotification("Weather Alert!", "This weather is not healthy", 1);
                        } else if (polution.getAqius() >= 201 && polution.getAqius() <= 300) {
                            displayNotification("Weather Alert!", "This weather is very unhealthy", 1);
                        } else if (polution.getAqius() >= 301) {
                            displayNotification("Weather Alert!", "This weather is Hazardous !!", 1);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherPolutionModel> call, Throwable t) {

            }
        });
    }*/

    private void loadData(final Double lat, final Double lon) {


        Log.i(TAG, "loadData: " + API_KEY + lat + lon);
        Call<WeatherModel> call = apiInterface.getWeatherData(API_KEY, lat, lon, "si");
        call.enqueue(new Callback<WeatherModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        weatherModel = response.body();
                        if (weatherModel != null) {
                            currentlyWeather = weatherModel.getCurrently();
                            Date date = new java.util.Date(currentlyWeather.getTime() * 1000L);
                            SimpleDateFormat sdf = new SimpleDateFormat("EEE,YYYY-MM-dd");
                            sdf.setTimeZone(java.util.TimeZone.getTimeZone(weatherModel.getTimezone()));
                            String formattedDate = sdf.format(date);
                            RemoteViews view = new RemoteViews("com.example.weatherforecastapp", R.layout.warther_widget);
                            view.setTextViewText(R.id.widgetDateTV, formattedDate);
                            view.setTextViewText(R.id.widgetTempTV, currentlyWeather.getTemperature().toString() + (char) 0x00B0 + "C");

                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            try {
                                addresses = geocoder.getFromLocation(lat, lon, 1);
                                String cityName = addresses.get(0).getAddressLine(0);

                                view.setTextViewText(R.id.widgetCurrentLocTV, cityName);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ComponentName theWidget = new ComponentName(getApplicationContext(), WeatherWidget.class);
                            AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
/* PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
*/
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            view.setOnClickPendingIntent(R.id.LL, pendingIntent);
                            manager.updateAppWidget(theWidget, view);


                        }


                    }

                } else {
                    Toast.makeText(getApplicationContext(), "something went to wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "something..." + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.i(TAG, "onFailure: " + t.getLocalizedMessage());

            }
        });

    }

   /* private void displayNotification(String title, String task, int id) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "WeathetNotification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.drawable.cloud);

        notificationManager.notify(id, notification.build());
    }*/


}