package com.example.netflop.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.responses.PopularResponse;
import com.example.netflop.data.responses.TVs.PopularTVResponse;
import com.example.netflop.data.services.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMovieRepository {
    private APIService apiService;

    public PopularMovieRepository() {
        apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
    }
    public LiveData<PopularResponse> getPopularMovie(int page){
        MutableLiveData<PopularResponse> liveData = new MutableLiveData<>();
        Call<PopularResponse> call=apiService.getPopular(page);
        call.enqueue(new Callback<PopularResponse>() {
            @Override
            public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PopularResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
