package com.blueface.codingtest.bluefacetask.supportClasses.weatherAPI;

import com.blueface.codingtest.bluefacetask.supportClasses.models.WeatherResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    String METRIC = "metric";

    @GET("weather")
    Observable<WeatherResponse> getWeather(
            @Query("q") String city,
            @Query("appid") String api_key,
            @Query("units") String unit);
}
