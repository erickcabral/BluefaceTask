package com.blueface.codingtest.bluefacetask.androidTestUtils;

import android.content.res.Resources;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class UIBaseTestUtils {

    public Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
}
