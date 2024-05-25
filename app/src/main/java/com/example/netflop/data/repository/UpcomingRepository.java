package com.example.netflop.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.responses.TrendingPeopleResponse;
import com.example.netflop.data.responses.UpcomingResponse;
import com.example.netflop.data.services.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingRepository {
    private APIService apiService;

    public UpcomingRepository() {
        apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
    }
    public LiveData<UpcomingResponse> getUpcomingMovie(int page){
        MutableLiveData<UpcomingResponse> liveData = new MutableLiveData<>();
        Call<UpcomingResponse> call=apiService.getUpcoming(page);
        call.enqueue(new Callback<UpcomingResponse>() {
            @Override
            public void onResponse(Call<UpcomingResponse> call, Response<UpcomingResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<UpcomingResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
