package com.blueface.codingtest.bluefacetask.repositories;

import com.blueface.codingtest.bluefacetask.baseTestUtils.BaseTestUtils;
import com.blueface.codingtest.bluefacetask.supportClasses.repositories.WeatherRepository;
import com.blueface.codingtest.bluefacetask.supportClasses.weatherAPI.WeatherService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WeatherRepositoryShould extends BaseTestUtils {

    private WeatherRepository repository;

    private final String api_key = "DUMMY KEY HERE";
    private final String CITY = "Paris";

    @Mock
    private WeatherService mckdService;

    @Before
    public void setup() {
        this.repository = new WeatherRepository(mckdService);
    }


    @Test
    public void request_weather() {
        this.repository.requestWeather(CITY);
        verify(mckdService, times(1)).requestWeather(CITY);
    }

    @Test
    public void get_weather_response() {
        this.repository.getLvdWeatherResponse();
        verify(mckdService, times(1)).getLvdWeatherResponse();
    }
}
