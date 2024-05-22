package com.example.netflop.data.repository;

import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.utils.AppExecutors;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MovieRepositories {
    AppExecutors appExecutors;
    APIService apiService;
    public void getTrendingMovie(){
        final Future myHandler=AppExecutors.getInstance().getNetworkIO().submit(new Runnable() {
            @Override
            public void run() {

            }
        });
        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },4000, TimeUnit.MILLISECONDS);
    }
}
