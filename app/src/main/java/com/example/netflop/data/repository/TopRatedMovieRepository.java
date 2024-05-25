package com.example.netflop.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.responses.NowPlayingResponse;
import com.example.netflop.data.responses.TopRatedResponse;
import com.example.netflop.data.services.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedMovieRepository {
    private APIService apiService;

    public TopRatedMovieRepository() {
        apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
    }
    public LiveData<TopRatedResponse> getTopRatedMovie(int page){
        MutableLiveData<TopRatedResponse> liveData = new MutableLiveData<>();
        Call<TopRatedResponse> call=apiService.getTopRated(page);
        call.enqueue(new Callback<TopRatedResponse>() {
            @Override
            public void onResponse(Call<TopRatedResponse> call, Response<TopRatedResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TopRatedResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
