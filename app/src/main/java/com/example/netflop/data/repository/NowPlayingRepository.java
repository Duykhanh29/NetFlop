package com.example.netflop.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.responses.NowPlayingResponse;
import com.example.netflop.data.responses.PopularResponse;
import com.example.netflop.data.services.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingRepository {
    private APIService apiService;

    public NowPlayingRepository() {
        apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
    }
    public LiveData<NowPlayingResponse> getNowPlayingMovie(int page){
        MutableLiveData<NowPlayingResponse> liveData = new MutableLiveData<>();
        Call<NowPlayingResponse> call=apiService.getNowPlaying(page);
        call.enqueue(new Callback<NowPlayingResponse>() {
            @Override
            public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<NowPlayingResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
