package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.responses.TrendingPeopleResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingPeopleViewModel extends ViewModel {
    private MutableLiveData<TrendingPeopleResponse> trendingPeopleData;
    public TrendingPeopleViewModel(){
        trendingPeopleData=new MutableLiveData<>();
    }
    public MutableLiveData<TrendingPeopleResponse> getTrendingPeopleData(){
        return  trendingPeopleData;
    }
    public void callAPI(){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<TrendingPeopleResponse> call=apiService.getTrendingPeople();
        call.enqueue(new Callback<TrendingPeopleResponse>() {
            @Override
            public void onResponse(Call<TrendingPeopleResponse> call, Response<TrendingPeopleResponse> response) {
                if(response.code()==200){
                    trendingPeopleData.postValue(response.body());
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                }
            }

            @Override
            public void onFailure(Call<TrendingPeopleResponse> call, Throwable t) {
                trendingPeopleData.postValue(null);
            }
        });
    }
}
