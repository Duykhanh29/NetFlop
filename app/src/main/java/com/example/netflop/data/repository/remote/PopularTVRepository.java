package com.example.netflop.data.repository.remote;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.responses.TVs.PopularTVResponse;
import com.example.netflop.data.services.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularTVRepository {
    private APIService apiService;

    public PopularTVRepository() {
        apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
    }
    public LiveData<PopularTVResponse> getPopularTV(int page){
        MutableLiveData<PopularTVResponse> liveData = new MutableLiveData<>();
        Call<PopularTVResponse> call=apiService.getPopularTV(page);
        call.enqueue(new Callback<PopularTVResponse>() {
            @Override
            public void onResponse(Call<PopularTVResponse> call, Response<PopularTVResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PopularTVResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
