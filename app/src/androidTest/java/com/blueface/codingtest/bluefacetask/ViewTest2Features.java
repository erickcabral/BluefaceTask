package com.blueface.codingtest.bluefacetask;

import com.blueface.codingtest.bluefacetask.androidTestUtils.MainActivityScenarioRule;

import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.internal.PerformActionKt.performAction;

public class ViewTest2Features extends MainActivityScenarioRule {


    @Test
    public void test_2_layout_initially_displayed_correctly() {
        assertDisplayed(R.id.linBlueface);
        assertDisplayed(R.id.linDataButtons);
        assertDisplayed(R.id.linTestButtons);
    }

    @Test
    public void navigate_test_2() {
        performAction(withId(R.id.btnTest2), click());
        assertDisplayed(R.id.layoutTest2);
    }

    @Test
    public void navigate_test_3() {
        performAction(withId(R.id.btnTest3), click());
        assertDisplayed(R.id.layoutTest3);
    }
}
