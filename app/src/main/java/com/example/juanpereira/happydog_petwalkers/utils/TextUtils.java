package com.example.juanpereira.happydog_petwalkers.utils;

import android.util.Patterns;

public class TextUtils {

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
