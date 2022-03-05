package com.example.weatherforecastapp.network;

import com.example.weatherforecastapp.pojo.WeatherPolutionModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIINterfacePollution {

    @GET("/v2/nearest_city")
    Call<WeatherPolutionModel> getPolutionData(@Query("lat") Double lat,
                                               @Query("longitude") Double longitude,
                                               @Query("api_key") String api_key
    );
}
