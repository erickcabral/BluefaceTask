package com.blueface.codingtest.bluefacetask.service;

import com.blueface.codingtest.bluefacetask.baseTestUtils.BaseTestUtils;
import com.blueface.codingtest.bluefacetask.supportClasses.models.City;
import com.blueface.codingtest.bluefacetask.supportClasses.models.WeatherResponse;
import com.blueface.codingtest.bluefacetask.supportClasses.weatherAPI.WeatherAPI;
import com.blueface.codingtest.bluefacetask.supportClasses.weatherAPI.WeatherService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherServiceShould extends BaseTestUtils {

    private WeatherService service;
    private final String CITY = "Paris";
    private final String dummy_API_KEY = "Dummy api KEY";

    @Mock
    private WeatherResponse mckdWeatherResponse;

    private final Observable<WeatherResponse> responseObservable = new Observable<WeatherResponse>() {
        @Override
        protected void subscribeActual(@NonNull Observer<? super WeatherResponse> observer) {
        }
    };

    @Mock
    private WeatherAPI mckAPI;

    @Before
    public void setup() {
        this.service = new WeatherService(mckAPI, dummy_API_KEY);
    }

    @Test
    public void call_get_weather_method() {
        this.service.requestWeather(CITY);
        verify(mckAPI, times(1)).getWeather(CITY, dummy_API_KEY, WeatherAPI.METRIC);
    }

    @Test
    public void call_get_city_weather_and_update_live_data_response() {
        when(mckAPI.getWeather(CITY, dummy_API_KEY, WeatherAPI.METRIC)).thenReturn(responseObservable);
        when(mckdWeatherResponse.getName()).thenReturn(CITY);

        this.service.requestWeather(CITY);
        verify(mckAPI, times(1)).getWeather(CITY, dummy_API_KEY, WeatherAPI.METRIC);

        City cityWeatherResponse = this.service.getWeatherResponse().getValue();
        assertNotNull(cityWeatherResponse);
        assertEquals(CITY, cityWeatherResponse.name);
    }
}
