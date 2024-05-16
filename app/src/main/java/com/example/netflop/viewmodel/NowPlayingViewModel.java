package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.responses.NowPlayingResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingViewModel extends ViewModel {
    private MutableLiveData<NowPlayingResponse>  nowPlayingData;
    public NowPlayingViewModel(){
        nowPlayingData=new MutableLiveData<>();
    }
    public MutableLiveData<NowPlayingResponse> getNowPlayingData(){
        return  nowPlayingData;
    }
    public void callAPI(){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<NowPlayingResponse> call=apiService.getNowPlaying();
        call.enqueue(new Callback<NowPlayingResponse>() {
            @Override
            public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
                if(response.code()==200){
                    nowPlayingData.postValue(response.body());
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<NowPlayingResponse> call, Throwable t) {
                nowPlayingData.postValue(null);
            }
        });
    }
}
