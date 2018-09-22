package com.example.nik.flickrapidemo.Utils;

// Class consisting of some common util function used throughout the app
public class CommonFunctionsUtil {
    // Utility function to check if a string is valid or not
    // Valid string means not null and has atleast 1 character
    public static boolean isValidString(String s) {
        return s != null && s.length() > 0;
    }
}
