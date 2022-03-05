package com.example.weatherforecastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static com.example.weatherforecastapp.MainActivity.LAT;
import static com.example.weatherforecastapp.MainActivity.LONG;
import static com.example.weatherforecastapp.MainActivity.myPref;
import static com.example.weatherforecastapp.Settings.CHECK_SETTING;
import static com.example.weatherforecastapp.Settings.MY_PREFERENCE;

public class SplashScreen extends AppCompatActivity {
    private static final String TAG = "SplashScreen";
    private Handler handler;
    private FusedLocationProviderClient mFusedLocationClient;
    public static final int locationRequestCode = 1000;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesSettings;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        sharedPreferences = getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
        sharedPreferencesSettings = getSharedPreferences(myPref, Context.MODE_PRIVATE);
        editor = sharedPreferencesSettings.edit();
        getCurrentLoc();


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
                            boolean flag = sharedPreferences.getBoolean(CHECK_SETTING, false);
                            handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                    if (flag) {
                                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(SplashScreen.this, Settings.class);
                                        startActivity(intent);
                                    }

                                }
                            }, 3000);
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
                        boolean flag = sharedPreferences.getBoolean(CHECK_SETTING, false);
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                                if (flag) {
                                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(SplashScreen.this, Settings.class);
                                    startActivity(intent);
                                }

                            }
                        }, 3000);
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
                            boolean flag = sharedPreferences.getBoolean(CHECK_SETTING, false);
                            handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                    if (flag) {
                                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(SplashScreen.this, Settings.class);
                                        startActivity(intent);
                                    }

                                }
                            }, 3000);
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
}
