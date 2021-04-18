package com.blueface.codingtest.bluefacetask.supportClasses.repositories;

import androidx.lifecycle.LiveData;

import com.blueface.codingtest.bluefacetask.supportClasses.models.City;
import com.blueface.codingtest.bluefacetask.supportClasses.weatherAPI.WeatherService;

import javax.inject.Inject;

public class WeatherRepository {

    private final WeatherService service;

    @Inject
    public WeatherRepository(WeatherService weatherService) {
        this.service = weatherService;
    }

    public void requestWeather(String city) {
        this.service.requestWeather(city);
    }

    public LiveData<City> getLvdWeatherResponse() {
        return this.service.getWeatherResponse();
    }

    public void clear() {
        service.clear();
    }
}
