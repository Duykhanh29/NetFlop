package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.responses.TopRatedResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedViewModel extends ViewModel {
    private MutableLiveData<TopRatedResponse> topRatedData;
    public TopRatedViewModel(){
        topRatedData=new MutableLiveData<>();
    }
    public MutableLiveData<TopRatedResponse> getTopRatedData(){
        return  topRatedData;
    }
    public void callAPI(){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<TopRatedResponse> call=apiService.getTopRated();
        call.enqueue(new Callback<TopRatedResponse>() {
            @Override
            public void onResponse(Call<TopRatedResponse> call, Response<TopRatedResponse> response) {

                if(response.code()==200){
                    topRatedData.postValue(response.body());
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                }
            }

            @Override
            public void onFailure(Call<TopRatedResponse> call, Throwable t) {
                topRatedData.postValue(null);
            }
        });
    }
}
