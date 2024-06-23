package com.example.netflop.data.repository.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.responses.movies.TrendingMovieResponse;
import com.example.netflop.data.services.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingMovieRepository {
    private APIService apiService;

    public TrendingMovieRepository() {
        apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
    }
    public LiveData<TrendingMovieResponse> getTrendingMovie(int page){
        MutableLiveData<TrendingMovieResponse> liveData = new MutableLiveData<>();
        Call<TrendingMovieResponse> call=apiService.getTrendingMovies(page);
        call.enqueue(new Callback<TrendingMovieResponse>() {
            @Override
            public void onResponse(Call<TrendingMovieResponse> call, Response<TrendingMovieResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TrendingMovieResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
