package com.example.weatherforecastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class Settings extends AppCompatActivity {
    private Switch notificationSwitch;
    private EditText refreshET;
    private Button applyBT;
    private SeekBar maxSB;
    private SeekBar minSB;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    public static final String MY_PREFERENCE = "com.example.myPreference";
    public static final String SWITCH = "com.example.switch";
    public static final String REFRESH = "com.example.refresh";
    public static final String TEMPERATURE_MAX = "com.example.temperature_max";
    public static final String TEMPERATURE_MIN = "com.example.temperature_min";
    public static final String CHECK_SETTING = "com.example.check_settings";
    private int maxVal;
    private int minVal;
    private TextView chooseTemperatureMinTV;
    private TextView chooseTemperatureMaxTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");
        chooseTemperatureMinTV = findViewById(R.id.chooseTemperatureMinTV);
        chooseTemperatureMaxTV = findViewById(R.id.chooseMaxTemperatureTV);
        maxSB = findViewById(R.id.maxTempSB);
        maxSB.setMax(100);
        minSB = findViewById(R.id.minTempSB);
        minSB.setMax(100);
        notificationSwitch = findViewById(R.id.settingsNotificationSW);
        refreshET = findViewById(R.id.settingsRefreshET);
        applyBT = findViewById(R.id.settingsApplyBT);
        sharedPreferences = this.getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();
        if (sharedPreferences.getBoolean(SWITCH, false)) {
            notificationSwitch.setChecked(true);
        }
        maxSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maxVal = progress;
                /* chooseTemperatureMaxTV.setText(maxVal);*/
                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                chooseTemperatureMaxTV.setText("" + progress);
                chooseTemperatureMaxTV.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                edit.putInt(TEMPERATURE_MAX, maxVal);
                edit.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        minSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                minVal = progress;
                edit.putInt(TEMPERATURE_MIN, minVal);
                /*  chooseTemperatureMinTV.setText(minVal);*/
                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                chooseTemperatureMinTV.setText("" + progress);
                chooseTemperatureMinTV.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                edit.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(getApplicationContext(), "on", Toast.LENGTH_SHORT).show();
                edit.putBoolean(SWITCH, true);
                edit.apply();

            } else {

                Toast.makeText(getApplicationContext(), "off", Toast.LENGTH_SHORT).show();


            }
        });

        applyBT.setOnClickListener(v -> {

            /// add code here to apply changes///
            if (refreshET.getText().toString().equals("")) {
                refreshET.setError("please put any time");
                return;
            }
            edit.putString(REFRESH, refreshET.getText().toString());
            edit.putBoolean(CHECK_SETTING, true);
            edit.apply();
            finish();
            Intent intent = new Intent(Settings.this, MainActivity.class);
            startActivity(intent);

        });
    }
}
