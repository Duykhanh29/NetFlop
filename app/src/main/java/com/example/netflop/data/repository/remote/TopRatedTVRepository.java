package com.example.netflop.data.repository.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.responses.TVs.TopRatedTVResponse;
import com.example.netflop.data.services.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedTVRepository {
    private APIService apiService;

    public TopRatedTVRepository() {
        apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
    }
    public LiveData<TopRatedTVResponse> getTopRatedTV(int page){
        MutableLiveData<TopRatedTVResponse> liveData = new MutableLiveData<>();
        Call<TopRatedTVResponse> call=apiService.getTopRatedTV(page);
        call.enqueue(new Callback<TopRatedTVResponse>() {
            @Override
            public void onResponse(Call<TopRatedTVResponse> call, Response<TopRatedTVResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TopRatedTVResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
