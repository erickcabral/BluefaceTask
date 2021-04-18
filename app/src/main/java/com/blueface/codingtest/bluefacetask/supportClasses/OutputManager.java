package com.blueface.codingtest.bluefacetask.supportClasses;

import android.content.Context;
import android.util.Log;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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


    public static void displayDialog(Context context, String title, String message) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .create()
                .show();
    }
}
