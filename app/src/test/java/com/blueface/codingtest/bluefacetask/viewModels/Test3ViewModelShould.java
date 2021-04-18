package com.blueface.codingtest.bluefacetask.viewModels;

import com.blueface.codingtest.bluefacetask.baseTestUtils.BaseTestUtils;
import com.blueface.codingtest.bluefacetask.fragView.test3.Test3ViewModel;
import com.blueface.codingtest.bluefacetask.supportClasses.repositories.WeatherRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class Test3ViewModelShould extends BaseTestUtils {

    private final String CITY = "Paris";

    private Test3ViewModel viewModel;


    @Mock
    private WeatherRepository mckdWeatherRepository;

    @Before
    public void setup() {
        this.viewModel = new Test3ViewModel(mckdWeatherRepository);
    }

    @Test
    public void call_weather_request_method() {
        String city = "Paris";
        this.viewModel.requestWeatherInfo(city);
        verify(mckdWeatherRepository, times(1)).requestWeather(city);
    }

    @Test
    public void call_weather_response_method() {
        this.viewModel.getWeatherResult();
        verify(mckdWeatherRepository, times(1)).getLvdWeatherResponse();
    }

    @Test
    public void set_live_data_progress(){
        this.viewModel.setLvdProgress(true);
        assertTrue(this.viewModel.getLvdProgress().getValue());
    }
}
