package com.example.netflop.viewmodel.local;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netflop.data.data_source.local_data_source.OnBoardingDataSource;
import com.example.netflop.data.repository.local.OnBoardingRepository;

public class OnBoardingViewModel extends AndroidViewModel {
    private OnBoardingDataSource repository;
    public OnBoardingViewModel(@NonNull Application application) {
        super(application);
        repository=new OnBoardingDataSource(application);
    }


    public void setState(){
        repository.setState();
    }
    public boolean getState(){
       return  repository.getState();
    }
}
