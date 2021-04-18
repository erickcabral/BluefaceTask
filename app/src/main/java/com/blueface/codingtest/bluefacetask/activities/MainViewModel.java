package com.blueface.codingtest.bluefacetask.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.blueface.codingtest.bluefacetask.supportClasses.models.City;
import com.blueface.codingtest.bluefacetask.supportClasses.repositories.Test_1_Repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private static final String TAG = "<<_VM_MAIN_ACTIVITY_>>";

    private Test_1_Repository test_1_repository =null;

    @Inject
    public MainViewModel(Test_1_Repository test_1_repository) {
        this.test_1_repository = test_1_repository;
    }

    public String getFixedJsonString(String jsonData) {
        return this.test_1_repository.getFixedJson(jsonData);
    }

    public void convertData(String fixedJson) {
        this.test_1_repository.convertJsonData(fixedJson);
    }

    public LiveData<City> getLvdCity() {
        return this.test_1_repository.getLvdCity();
    }

    public LiveData<String> getLvdError() {
        return this.test_1_repository.getLvdError();
    }

}
