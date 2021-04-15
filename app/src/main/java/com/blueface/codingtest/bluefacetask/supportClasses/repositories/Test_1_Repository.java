package com.blueface.codingtest.bluefacetask.supportClasses.repositories;

import androidx.lifecycle.MutableLiveData;

import com.blueface.codingtest.bluefacetask.supportClasses.models.City;
import com.google.gson.Gson;

import javax.inject.Inject;


public class Test_1_Repository {


    private final Gson gson;
    private final MutableLiveData<City> lvdCity = new MutableLiveData<>();
    private final MutableLiveData<String> lvdError = new MutableLiveData<>();

    @Inject
    public Test_1_Repository(Gson gson) {
        this.gson = gson;
    }

    public String getFixedJson(String jsonString) {
        String validJson = jsonString.substring(9, jsonString.length() - 1).trim();
        return validJson;
    }

    public void convertJsonData(String jsonData) {
        if (!jsonData.contains("name")) {
            lvdError.postValue("Json data has no City Name attribute");
            return;
        } else if (!jsonData.contains("rank")) {
            lvdError.postValue("Json data has no City Rank attribute");
            return;
        } else if (!jsonData.contains("location")) {
            lvdError.postValue("Json data has no Location attribute");
            return;
        }
        try {
            City city = this.gson.fromJson(jsonData, City.class);
            if (city.name.isEmpty()) {
                this.lvdError.postValue("City Name is Null or Empty");
                return;
            } else if (city.rank <= 0) {
                this.lvdError.postValue("City Rank is Invalid");
                return;
            }
            this.lvdCity.postValue(city);
        } catch (NullPointerException e) {
            lvdError.postValue("Json data is invalid");
        }
    }

    public MutableLiveData<City> getLvdCity() {
        return this.lvdCity;
    }

    public MutableLiveData<String> getLvdError() {
        return this.lvdError;
    }
}
