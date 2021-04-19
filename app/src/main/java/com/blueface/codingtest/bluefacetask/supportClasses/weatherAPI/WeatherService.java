package com.blueface.codingtest.bluefacetask.supportClasses.weatherAPI;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blueface.codingtest.bluefacetask.supportClasses.OutputManager;
import com.blueface.codingtest.bluefacetask.supportClasses.models.City;
import com.blueface.codingtest.bluefacetask.supportClasses.models.WeatherResponse;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WeatherService {
    private static final String TAG = "<<_WEATHER_SERVICE_>>";

    private static final int NOT_FOUND_ERROR = 404;

    private final MutableLiveData<City> lvdCityWeatherResponse = new MutableLiveData<>();
    private final MutableLiveData<Integer> lvdServiceError = new MutableLiveData<Integer>();

    private final String api_key;
    private final WeatherAPI weatherAPI;

    private final CompositeDisposable compositeDisposable;
    private City cityWeather;

    @Inject
    public WeatherService(WeatherAPI weatherAPI, String api_key) {
        this.weatherAPI = weatherAPI;
        this.api_key = api_key;

        this.compositeDisposable = new CompositeDisposable();
    }


    public void requestWeather(String city) {
        Observable<WeatherResponse> response = this.weatherAPI.getWeather(city, api_key, WeatherAPI.METRIC);

        this.compositeDisposable.add(response.subscribeOn(Schedulers.io())
                .delay(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<WeatherResponse>() {
                    @Override
                    public void onNext(@NonNull WeatherResponse weatherResponseResponse) {
                        if (weatherResponseResponse != null) {

                            Double temperature = weatherResponseResponse.getMain().getTemp();

                            cityWeather = new City();
                            cityWeather.name = weatherResponseResponse.getName();
                            cityWeather.temperature = Float.parseFloat(temperature.toString());
                        }
                        OutputManager.logInfo(TAG, "WEATHER RESPONSE DONE");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        OutputManager.logInfo(TAG, "ERROR WHEN REQUESTING WEATHER REQUEST -> " + e.getMessage());
                        int code = 1;
                        if (e.getMessage().contains("404")) {
                            code = NOT_FOUND_ERROR;
                        }
                        lvdServiceError.postValue(code);
                    }

                    @Override
                    public void onComplete() {
                        OutputManager.logInfo(TAG, "WEATHER REQUEST IS COMPLETED");
                        lvdCityWeatherResponse.postValue(cityWeather);
                    }
                }));

    }

    public LiveData<City> getLvdWeatherResponse() {
        return this.lvdCityWeatherResponse;
    }

    public LiveData<Integer> getLvdServiceError() {
        return this.lvdServiceError;
    }

    public void clear() {
        this.compositeDisposable.clear();
    }
}
