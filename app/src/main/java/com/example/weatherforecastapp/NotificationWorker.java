package com.example.weatherforecastapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.weatherforecastapp.network.APICLientPolution;
import com.example.weatherforecastapp.network.APIInterface;
import com.example.weatherforecastapp.pojo.WeatherModel;
import com.example.weatherforecastapp.pojo.WeatherPolutionModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.weatherforecastapp.MainActivity.LAT;
import static com.example.weatherforecastapp.MainActivity.LONG;
import static com.example.weatherforecastapp.MainActivity.myPref;
import static com.example.weatherforecastapp.Settings.MY_PREFERENCE;
import static com.example.weatherforecastapp.Settings.SWITCH;
import static com.example.weatherforecastapp.Settings.TEMPERATURE_MAX;
import static com.example.weatherforecastapp.Settings.TEMPERATURE_MIN;

public class NotificationWorker extends Worker {
    public static Context context;
    private boolean locationFlag = false;
    private boolean locationEnvironmentPollution = false;
    private boolean locationHighTemp = false;
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
    private static final String TAG = "NotificationWorker";
    private List<Address> addresses = null;
    private WeatherPolutionModel weatherPolutionModel;
    private WeatherPolutionModel.Data weatherData;
    private WeatherPolutionModel.Data.Current.Weather currentWeather;
    private WeatherPolutionModel.Data.Current.Pollution polution;
    public static final int locationRequestCode = 1000;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private FusedLocationProviderClient mFusedLocationClient;

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesSettings;
    private SharedPreferences.Editor editor;
    private boolean onNotofication;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        apiInterfacePolution = APICLientPolution.getRetrofitInstance().create(APIInterface.class);
        sharedPreferences = context.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i(TAG, "doWork: fgdesghehwh");
        getAllData();
        return Result.success();
    }

    private void displayNotification(String title, String task, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "WeathetNotification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.drawable.cloud);

        notificationManager.notify(id, notification.build());
    }

    public void loadPollutionData(final Double lat, final Double lon) {
        Log.i(TAG, "loadPollutionData: ");
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
                        String msg = sharedPreferences.getString(environmentPollution, "0");
                        String tp = sharedPreferences.getString(highTemp, "0");
                        sharedPreferences = getApplicationContext().getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
                        int maxTemp = sharedPreferences.getInt(TEMPERATURE_MAX, 32);
                        int minTemp = sharedPreferences.getInt(TEMPERATURE_MIN, 15);
                        Log.i(TAG, "onResponse: " + minTemp);
                        onNotofication = false;
                        sharedPreferencesSettings = getApplicationContext().getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
                        onNotofication = sharedPreferencesSettings.getBoolean(SWITCH, false);
                        Log.i(TAG, "onResponse: " + onNotofication);
                        if (onNotofication) {
                            if (currentWeather.getTp() > maxTemp && !tp.equals("Temperature is very high")) {
                                displayNotification("Weather Alert!", "Temperature is very high", 2);
                                editor.putString(highTemp, "Temperature is very high");
                                editor.apply();
                            } else if (currentWeather.getTp() < minTemp && !tp.equals("Temperature is very low")) {
                                displayNotification("Weather Alert!", "Temperature is very low", 2);
                                editor.putString(highTemp, "Temperature is very low");
                                editor.apply();

                            }

                            if (polution.getAqius() >= 0 && polution.getAqius() <= 50 && !msg.equals("Breath! Healthy Weather")) {
                                displayNotification("Weather Alert!", "Breath! Healthy Weather", 1);
                                editor.putString(environmentPollution, "Breath! Healthy Weather");
                                editor.apply();
                            } else if (polution.getAqius() >= 51 && polution.getAqius() <= 100 && !msg.equals("This weather is Moderate")) {
                                displayNotification("Weather Alert!", "This weather is Moderate", 1);
                                editor.putString(environmentPollution, "This weather is Moderate");
                                editor.apply();
                            } else if (polution.getAqius() >= 101 && polution.getAqius() <= 150 && !msg.equals("Unhealthy for senstive people")) {
                                displayNotification("Weather Alert!", "Unhealthy for senstive people", 1);
                                editor.putString(environmentPollution, "Unhealthy for senstive people");
                                editor.apply();

                            } else if (polution.getAqius() >= 151 && polution.getAqius() <= 200 && !msg.equals("This weather is not healthy")) {
                                displayNotification("Weather Alert!", "This weather is not healthy", 1);
                                editor.putString(environmentPollution, "This weather is not healthy");
                                editor.apply();
                            } else if (polution.getAqius() >= 201 && polution.getAqius() <= 300 && !msg.equals("This weather is very unhealthy")) {
                                displayNotification("Weather Alert!", "This weather is very unhealthy", 1);
                                editor.putString(environmentPollution, "This weather is very unhealthy");
                                editor.apply();
                            } else if (polution.getAqius() >= 301 && !msg.equals("This weather is Hazardous !!")) {
                                displayNotification("Weather Alert!", "This weather is Hazardous !!", 1);
                                editor.putString(environmentPollution, "This weather is Hazardous !!");
                                editor.apply();
                            }
                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherPolutionModel> call, Throwable t) {

            }
        });
    }

    private void getAllData() {
        Log.i(TAG, "getAllData: " + "enter this method");
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Location temp = new Location(LocationManager.GPS_PROVIDER);
                    String lat = sharedPreferences.getString(LAT, "");
                    String lon = sharedPreferences.getString(LONG, "");
                    double doubleLat = Double.parseDouble(lat);
                    double doubleLong = Double.parseDouble(lon);
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
                    Log.i(TAG, "onSuccess: Lat: " + lat);
                    loadPollutionData(doubleLat, doubleLong);

                }

            }
        });
    }

}
