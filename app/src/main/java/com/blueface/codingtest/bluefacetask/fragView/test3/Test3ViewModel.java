package com.blueface.codingtest.bluefacetask.fragView.test3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blueface.codingtest.bluefacetask.supportClasses.models.City;
import com.blueface.codingtest.bluefacetask.supportClasses.repositories.WeatherRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class Test3ViewModel extends ViewModel {

    private final WeatherRepository weatherRepository;
    private final MutableLiveData<Boolean> lvdProgress = new MutableLiveData<>(false);

    @Inject
    public Test3ViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void requestWeatherInfo(String city) {
        this.weatherRepository.requestWeather(city);
    }

    public LiveData<City> getWeatherResult() {
        return this.weatherRepository.getLvdWeatherResponse();
    }

    public void setLvdProgress(boolean isVisible) {
        this.lvdProgress.postValue(isVisible);
    }

    public LiveData<Boolean> getLvdProgress() {
        return this.lvdProgress;
    }

    public void clearComp() {
        this.weatherRepository.clear();
    }
}
