package com.example.netflop.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.responses.SearchMovieResponse;
import com.example.netflop.data.responses.SearchMultiResponse;
import com.example.netflop.data.responses.SearchPersonResponse;
import com.example.netflop.data.responses.TVs.SearchTVResponse;
import com.example.netflop.data.services.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {
    private APIService apiService;

    public SearchRepository() {
        apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
    }
    public LiveData<SearchMovieResponse> searchMovies(String query, boolean includeAdult, int page){
        MutableLiveData<SearchMovieResponse> liveData = new MutableLiveData<>();
        Call<SearchMovieResponse> call=apiService.getSearchMovies(query, includeAdult, page);
        call.enqueue(new Callback<SearchMovieResponse>() {
            @Override
            public void onResponse(Call<SearchMovieResponse> call, Response<SearchMovieResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SearchMovieResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }


    public LiveData<SearchMultiResponse> searchMultiple(String query, boolean includeAdult, int page){
        MutableLiveData<SearchMultiResponse> liveData = new MutableLiveData<>();
        Call<SearchMultiResponse> call=apiService.getSearchMultiSource(query, includeAdult, page);
        call.enqueue(new Callback<SearchMultiResponse>() {
            @Override
            public void onResponse(Call<SearchMultiResponse> call, Response<SearchMultiResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SearchMultiResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    public LiveData<SearchPersonResponse> searchPeople(String query, boolean includeAdult, int page){
        MutableLiveData<SearchPersonResponse> liveData = new MutableLiveData<>();
        Call<SearchPersonResponse> call=apiService.getSearchPeople(query, includeAdult, page);
        call.enqueue(new Callback<SearchPersonResponse>() {
            @Override
            public void onResponse(Call<SearchPersonResponse> call, Response<SearchPersonResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SearchPersonResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    public LiveData<SearchTVResponse> searchTVs(String query, boolean includeAdult, int page){
        MutableLiveData<SearchTVResponse> liveData = new MutableLiveData<>();
        Call<SearchTVResponse> call=apiService.getSearchTV(query, includeAdult, page);
        call.enqueue(new Callback<SearchTVResponse>() {
            @Override
            public void onResponse(Call<SearchTVResponse> call, Response<SearchTVResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SearchTVResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
