package com.example.netflop.data.data_source.local_data_source;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.netflop.constants.StringConstants;

public class OnBoardingDataSource {
    private SharedPreferences sharedPreferences;
    private Context context;
    public OnBoardingDataSource(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(StringConstants.networkKey,context.MODE_PRIVATE);
    }
    public void setState(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(StringConstants.networkStateKey,true);
        editor.apply();
    }

    public boolean getState(){
        return sharedPreferences.getBoolean(StringConstants.networkStateKey,false);
    }
}
