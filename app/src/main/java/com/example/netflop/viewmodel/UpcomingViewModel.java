package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.responses.UpcomingResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingViewModel extends ViewModel {
    private MutableLiveData<UpcomingResponse> upcomingData;
    public UpcomingViewModel(){
        upcomingData=new MutableLiveData<>();
    }
    public MutableLiveData<UpcomingResponse> getUpcomingData(){
        return  upcomingData;
    }
    public void callAPI(){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<UpcomingResponse> call=apiService.getUpcoming();
        call.enqueue(new Callback<UpcomingResponse>() {
            @Override
            public void onResponse(Call<UpcomingResponse> call, Response<UpcomingResponse> response) {
                if(response.code()==200){
                    upcomingData.postValue(response.body());
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                }
            }

            @Override
            public void onFailure(Call<UpcomingResponse> call, Throwable t) {
                upcomingData.postValue(null);
            }
        });
    }
}
