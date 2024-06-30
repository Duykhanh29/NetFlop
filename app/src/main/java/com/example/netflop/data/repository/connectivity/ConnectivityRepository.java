package com.example.netflop.data.repository.connectivity;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.netflop.constants.StringConstants;
import com.example.netflop.data.data_source.local_data_source.NetworkDataSource;

public class ConnectivityRepository {
    NetworkDataSource networkDataSource;

    public ConnectivityRepository(Context context) {
        this.networkDataSource =new NetworkDataSource(context);
    }
    public void setState(boolean value){
        networkDataSource.setState(value);
    }

    public boolean getState(){
        return networkDataSource.getState();
    }
}
