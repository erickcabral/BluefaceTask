package com.blueface.codingtest.bluefacetask.supportClasses;

import android.util.Log;

public class OutputManager {
    private static final boolean logEnable = true;

    public static void logInfo(String tag, String message) {
        if (logEnable) {
            Log.i(tag, message);
        }
    }

    public static void logError(String tag, String message) {
        if (logEnable) {
            Log.e(tag, message);
        }
    }
}
