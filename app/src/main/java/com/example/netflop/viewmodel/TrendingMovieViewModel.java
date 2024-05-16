package com.example.netflop.viewmodel;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.responses.TrendingMovieResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingMovieViewModel extends ViewModel {
    private MutableLiveData<TrendingMovieResponse> trendingMovieData;
    public TrendingMovieViewModel(){
        trendingMovieData=new MutableLiveData<>();
    }
    public MutableLiveData<TrendingMovieResponse> getTrendingMovieData(){
        return  trendingMovieData;
    }
    public void callAPI(){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<TrendingMovieResponse> call=apiService.getTrendingMovies();
        call.enqueue(new Callback<TrendingMovieResponse>() {
            @Override
            public void onResponse(Call<TrendingMovieResponse> call, Response<TrendingMovieResponse> response) {

                if(response.code()==200){
                    trendingMovieData.postValue(response.body());
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                }
            }

            @Override
            public void onFailure(Call<TrendingMovieResponse> call, Throwable t) {
                trendingMovieData.postValue(null);
            }
        });
    }
}
