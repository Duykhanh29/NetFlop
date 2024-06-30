package com.example.netflop.helpers;

import android.content.Context;
import android.widget.Toast;

public class NoInternetToastHelpers {
    static public void show(Context context){
        Toast.makeText(context, "Please try to connect internet to explore", Toast.LENGTH_SHORT).show();
    }
}
