package com.example.nik.flickrapidemo.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

// Class consisting of some common util function used throughout the app
public class CommonFunctionsUtil {
    // Utility function to check if a string is valid or not
    // Valid string means not null and has atleast 1 character
    public static boolean isValidString(String s) {
        return s != null && s.length() > 0;
    }

    // Utility function to show keyboard
    public static void showKeyboard(Activity activity, View view) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            try {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Utility function to hide keyboard
    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            try {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Utility function to check if internet is available
    public static boolean isNetworkConnectivityAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            // If no connectivity, return false
            return false;
        }
        return true;
    }
}
