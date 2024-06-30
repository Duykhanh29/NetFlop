package com.example.netflop.data.repository.local;

import android.content.Context;

import com.example.netflop.data.data_source.local_data_source.DatabaseHelper;
import com.example.netflop.data.data_source.local_data_source.OnBoardingDataSource;

public class OnBoardingRepository {
    private OnBoardingDataSource onBoardingDataSource;
    public OnBoardingRepository(Context context) {
        onBoardingDataSource = new OnBoardingDataSource(context);
    }
    public boolean getState(){
        return  onBoardingDataSource.getState();
    }
    public void setState(){
        onBoardingDataSource.setState();
    }
}
