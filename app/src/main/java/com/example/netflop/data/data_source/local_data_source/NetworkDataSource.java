package com.example.netflop.data.data_source.local_data_source;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.netflop.constants.StringConstants;

public class NetworkDataSource {
    private SharedPreferences sharedPreferences;
    private Context context;
    public NetworkDataSource(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(StringConstants.onBoardingKey,context.MODE_PRIVATE);
    }
    public void setState(boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(StringConstants.onBoardingKey,value);
        editor.apply();
    }

    public boolean getState(){
        return sharedPreferences.getBoolean(StringConstants.onBoardingKey,false);
    }
}
