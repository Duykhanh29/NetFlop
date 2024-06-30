package com.example.netflop.viewmodel.connectivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.netflop.data.repository.connectivity.ConnectivityRepository;

public class ConnectivityViewModel extends AndroidViewModel {
    ConnectivityRepository connectivityRepository;
    public ConnectivityViewModel(@NonNull Application application) {
        super(application);
        connectivityRepository=new ConnectivityRepository(application);
    }
    public void setState(boolean value){
        connectivityRepository.setState(value);
    }
    public boolean getState(){
        return  connectivityRepository.getState();
    }
}
