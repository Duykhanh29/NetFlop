package com.example.netflop.data.repository.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.responses.people.TrendingPeopleResponse;
import com.example.netflop.data.services.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingPeopleRepository {
    private APIService apiService;

    public TrendingPeopleRepository() {
        apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
    }
    public LiveData<TrendingPeopleResponse> getTrendingPeople(int page){
        MutableLiveData<TrendingPeopleResponse> liveData = new MutableLiveData<>();
        Call<TrendingPeopleResponse> call=apiService.getTrendingPeople(page);
        call.enqueue(new Callback<TrendingPeopleResponse>() {
            @Override
            public void onResponse(Call<TrendingPeopleResponse> call, Response<TrendingPeopleResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TrendingPeopleResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
