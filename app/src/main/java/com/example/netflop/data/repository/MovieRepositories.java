package com.example.netflop.data.repository;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.responses.TrendingMovieResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;
import com.example.netflop.utils.AppExecutors;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
