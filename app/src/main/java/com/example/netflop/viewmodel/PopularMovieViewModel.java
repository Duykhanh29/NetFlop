package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.responses.PopularResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMovieViewModel extends ViewModel {
    private MutableLiveData<PopularResponse> popularMovieData;
    public PopularMovieViewModel(){
        popularMovieData=new MutableLiveData<>();
    }
    public MutableLiveData<PopularResponse> getPopularMovieData(){
        return  popularMovieData;
    }
    public void callAPI(){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<PopularResponse> call=apiService.getPopular();
        call.enqueue(new Callback<PopularResponse>() {
            @Override
            public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {

                if(response.code()==200){
                    popularMovieData.postValue(response.body());
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                }
            }

            @Override
            public void onFailure(Call<PopularResponse> call, Throwable t) {
                popularMovieData.postValue(null);
            }
        });
    }
}
