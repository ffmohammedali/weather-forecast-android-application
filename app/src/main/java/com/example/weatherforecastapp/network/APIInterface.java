package com.example.weatherforecastapp.network;

import com.example.weatherforecastapp.pojo.WeatherModel;
import com.example.weatherforecastapp.pojo.WeatherPolutionModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/forecast/{api_key}/{lati},{longi}")
    public Call<WeatherModel> getWeatherData(@Path("api_key") String api_key, @Path("lati") Double lati, @Path("longi") Double longi,

                                             @Query("units") String units);

    @GET("/v2/nearest_city")
    public Call<WeatherPolutionModel> getPolutionData(@Query("lat") Double lat,
                                                      @Query("lon") Double longitude,
                                                      @Query("key") String api_key
    );


}
