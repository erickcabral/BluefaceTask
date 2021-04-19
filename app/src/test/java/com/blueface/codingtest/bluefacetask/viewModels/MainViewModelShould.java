package com.blueface.codingtest.bluefacetask.viewModels;

import android.content.Context;

import com.blueface.codingtest.bluefacetask.baseTestUtils.BaseTestUtils;
import com.blueface.codingtest.bluefacetask.fragView.test1.Test1ViewModel;
import com.blueface.codingtest.bluefacetask.supportClasses.models.City;
import com.blueface.codingtest.bluefacetask.supportClasses.models.JsonData;
import com.blueface.codingtest.bluefacetask.supportClasses.repositories.Test_1_Repository;
import com.google.android.gms.common.GoogleApiAvailability;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainViewModelShould extends BaseTestUtils {

    private Test1ViewModel viewModel;

    @Mock
    private Test_1_Repository mckdTest_1_repository;

    @Mock
    private Context context;

    @Mock
    private GoogleApiAvailability googleApiAvailability;

    private final String fixedJson = "{\n" +
            "\"name\": \"Paris\",\n" +
            "\"rank\": 7,\n" +
            "\"location\": {\"lat\": 48.8588376, \"lon\": 2.2768491}\n" +
            "}";

    private final City currentCity;

    public MainViewModelShould() {
        this.currentCity = new City();
        this.currentCity.name = "Paris";
        this.currentCity.rank = 7;
    }

    @Before
    public void setup() {
        this.viewModel = new Test1ViewModel(mckdTest_1_repository);
    }

    @Test
    public void call_get_fixed_json_string() {
        when(mckdTest_1_repository.getFixedJson(JsonData.paris)).thenReturn(fixedJson);
        String fixedJson = this.viewModel.getFixedJsonString(JsonData.paris);
        verify(mckdTest_1_repository, times(1)).getFixedJson(JsonData.paris);

        assertNotNull(fixedJson);
        assertTrue(fixedJson.contains("name"));
        assertTrue(fixedJson.contains("rank"));
        assertTrue(fixedJson.contains("location"));
    }

    @Test
    public void call_test_1_repository_convert_json_data_method() {
        this.viewModel.convertData(fixedJson);
        verify(mckdTest_1_repository, times(1)).convertJsonData(fixedJson);
    }

    @Test
    public void get_city_live_data_response() {
        this.viewModel.getLvdCity();
        verify(this.mckdTest_1_repository, times(1)).getLvdCity();
    }

    @Test
    public void get_error_live_data_when_json_is_invalid() {
        this.viewModel.getLvdError();
        verify(this.mckdTest_1_repository, times(1)).getLvdError();
    }

}
