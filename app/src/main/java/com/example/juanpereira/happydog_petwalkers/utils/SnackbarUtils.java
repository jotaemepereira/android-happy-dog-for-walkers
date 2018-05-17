package com.example.juanpereira.happydog_petwalkers.utils;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.juanpereira.happydog_petwalkers.R;

public class SnackbarUtils {

    public static void showErrorMessage(String message, View parentView) {
        Snackbar snackbar = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(ContextCompat.getColor(parentView.getContext(), R.color.red_error));
        snackbar.show();
    }
}
