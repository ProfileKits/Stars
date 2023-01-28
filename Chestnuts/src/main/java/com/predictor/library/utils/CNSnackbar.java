package com.predictor.library.utils;



import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class CNSnackbar {

    public static void show(Context context,String str, View view) {
        Snackbar.make(context, view, str, Snackbar.LENGTH_SHORT).show();
    }
    
    public static void showLong(Context context,String str, View view) {
        Snackbar.make(context, view, str, Snackbar.LENGTH_LONG).show();
    }
}
