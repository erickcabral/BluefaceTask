package com.blueface.codingtest.bluefacetask.repositories;

import com.blueface.codingtest.bluefacetask.baseTestUtils.BaseTestUtils;
import com.blueface.codingtest.bluefacetask.supportClasses.models.City;
import com.blueface.codingtest.bluefacetask.supportClasses.models.JsonData;
import com.blueface.codingtest.bluefacetask.supportClasses.repositories.Test_1_Repository;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Test_1RepositoryShould extends BaseTestUtils {

    private Test_1_Repository repository;

    private final String defaultJson = JsonData.paris;
    private final String validJson = JsonData.parisWithTemp;

    private final String fixedJson = "{\n" +
            "\"name\": \"Paris\",\n" +
            "\"rank\": 7,\n" +
            "\"location\": {\"lat\": 48.8588376, \"lon\": 2.2768491}\n" +
            "}";
    private final String fixedJsonWithTemperature = "{\n" +
            "\"name\": \"Paris\",\n" +
            "\"rank\": 7,\n" +
            "\"temperature\": 22.3,\n" +
            "\"location\": {\"lat\": 48.8588376, \"lon\": 2.2768491}\n" +
            "}";



    private final String invalidCityJson = "{\n" +
            "\"\": \"\",\n" +
            "\"rank\": 7,\n" +
            "\"location\": {\"lat\": 48.8588376, \"lon\": 2.2768491}\n" +
            "}";

    private final String invalidRankJson = "{\n" +
            "\"name\": \"Paris\",\n" +
            "\"\": ,\n" +
            "\"location\": {\"lat\": 48.8588376, \"lon\": 2.2768491}\n" +
            "}";

    private final String invalidLocationJson = "{\n" +
            "\"name\": \"Paris\",\n" +
            "\"rank\": 7" +
            "}";

    private final City city = new City();


    @Mock
    private Gson gson;

    @Before
    public void setup() {
        this.repository = new Test_1_Repository(gson);
    }

    @Test
    public void fix_given_jsonData_and_return_string() {
        String fixedString = this.repository.getFixedJson(defaultJson);
        assertNotNull(fixedString);
        assertEquals(fixedJson, fixedString);
    }

    @Test
    public void convert_json_data_and_update_city_live_data() {
        city.name = "Paris";
        city.rank = 7;
        city.temperature = 34.5f;

        when(gson.fromJson(fixedJsonWithTemperature, City.class)).thenReturn(city);

        this.repository.convertJsonData(fixedJsonWithTemperature);
        verify(gson, times(1)).fromJson(fixedJsonWithTemperature, City.class);

        City city = this.repository.getLvdCity().getValue();
        assertNotNull(city);
        assertEquals("Paris", city.name);
        assertEquals(7, city.rank);
        assertEquals(34.5, city.temperature, 0);
    }


    // =============== UNHAPPY ================= //
    @Test
    public void convert_json_data_and_update_city_name_error_live_data() {

        this.repository.convertJsonData(invalidCityJson);
        verify(gson, times(0)).fromJson(invalidCityJson, City.class);

        String errorMessage = this.repository.getLvdError().getValue();
        assertNotNull(errorMessage);
        assertEquals("Json data has no City Name attribute", errorMessage);
    }

    @Test
    public void convert_json_data_and_update_city_rank_error_live_data() {

        this.repository.convertJsonData(invalidRankJson);
        verify(gson, times(0)).fromJson(invalidRankJson, City.class);

        String errorMessage = this.repository.getLvdError().getValue();
        assertNotNull(errorMessage);
        assertEquals("Json data has no City Rank attribute", errorMessage);
    }

    @Test
    public void convert_json_data_and_update_city_location_error_live_data() {

        this.repository.convertJsonData(invalidLocationJson);
        verify(gson, times(0)).fromJson(invalidLocationJson, City.class);

        String errorMessage = this.repository.getLvdError().getValue();
        assertNotNull(errorMessage);
        assertEquals("Json data has no Location attribute", errorMessage);
    }

    @Test
    public void convert_json_and_return_invalid_json_error_live_data_when_name_null() {
        city.name = null;
        city.rank = 7;
        city.temperature = 34.5f;

        when(gson.fromJson(validJson, City.class)).thenReturn(this.city);
        this.repository.convertJsonData(validJson);

        assertNull(this.repository.getLvdCity().getValue());

        String errorMessage = this.repository.getLvdError().getValue();
        assertNotNull(errorMessage);

        assertEquals("Json data is invalid", errorMessage);
    }

    @Test
    public void convert_json_and_return_error_live_data_when_name_null() {
        city.name = "";
        city.rank = 7;
        city.temperature = 34.5f;

        when(gson.fromJson(validJson, City.class)).thenReturn(this.city);
        this.repository.convertJsonData(validJson);

        assertNull(this.repository.getLvdCity().getValue());

        String errorMessage = this.repository.getLvdError().getValue();
        assertNotNull(errorMessage);

        assertEquals("City Name is Null or Empty", errorMessage);
    }
}
