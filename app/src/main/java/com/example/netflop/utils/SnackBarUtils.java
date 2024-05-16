package com.example.netflop.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarUtils {
    public static void showSnackBar(View v, String snackBarText) {
        if (v == null || snackBarText == null) {
            return;
        }
        Snackbar.make(v, snackBarText, Snackbar.LENGTH_LONG).show();
    }
}
