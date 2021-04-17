package com.blueface.codingtest.bluefacetask.supportClasses;

import android.util.Log;

public class OutputManager {
    private static boolean logEnable = true;

    public static void enableLog(boolean isLogEnabled) {
        logEnable = isLogEnabled;
    }

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

    public static void logWarning(String tag, String message) {
        if (logEnable) {
            Log.w(tag, message);
        }
    }
}
