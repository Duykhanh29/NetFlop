package com.example.netflop.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.responses.PopularPeopleResponse;
import com.example.netflop.data.responses.PopularResponse;
import com.example.netflop.data.services.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularPeopleRepository {
    private APIService apiService;

    public PopularPeopleRepository() {
        apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
    }
    public LiveData<PopularPeopleResponse> getPopularPeople(int page){
        MutableLiveData<PopularPeopleResponse> liveData = new MutableLiveData<>();
        Call<PopularPeopleResponse> call=apiService.getPopularPeople(page);
        call.enqueue(new Callback<PopularPeopleResponse>() {
            @Override
            public void onResponse(Call<PopularPeopleResponse> call, Response<PopularPeopleResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PopularPeopleResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
