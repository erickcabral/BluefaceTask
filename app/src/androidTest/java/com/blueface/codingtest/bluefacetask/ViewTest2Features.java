package com.blueface.codingtest.bluefacetask;

import com.blueface.codingtest.bluefacetask.androidTestUtils.MainActivityScenarioRule;

import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed;
import static com.schibsted.spain.barista.internal.PerformActionKt.performActionOnView;

public class ViewTest2Features extends MainActivityScenarioRule {


    @Test
    public void test_2_layout_initially_displayed_correctly() {
        assertDisplayed(R.id.inpCityName);
        assertDisplayed(R.id.inpCityRank);
        assertDisplayed(R.id.btnSubmit);

        assertNotDisplayed(R.id.linCityInfo);
    }

    @Test
    public void city_info_displayed_when_city_data_submitted() {
        performActionOnView(withId(R.id.edCityName), typeText("Paris"));
        performActionOnView(withId(R.id.edCityRank), typeText("7"));
        performActionOnView(withId(R.id.btnSubmit), click());

        assertDisplayed(R.id.linCityInfo);
    }

}
