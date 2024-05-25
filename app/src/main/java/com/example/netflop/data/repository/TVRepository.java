package com.example.netflop.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.models.TVs.TVEpisodeCredit;
import com.example.netflop.data.models.TVs.TVEpisodeDetail;
import com.example.netflop.data.models.TVs.TVEpisodeImage;
import com.example.netflop.data.models.TVs.TVEpisodeVideo;
import com.example.netflop.data.models.TVs.TVSeasonsDetail;
import com.example.netflop.data.models.TVs.TVSeriesDetail;
import com.example.netflop.data.responses.UpcomingResponse;
import com.example.netflop.data.services.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVRepository {
    private APIService apiService;

    public TVRepository() {
        apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
    }
    public LiveData<TVSeriesDetail> getTVSeriesDetail(int id){
        MutableLiveData<TVSeriesDetail> liveData = new MutableLiveData<>();
        Call<TVSeriesDetail> call=apiService.getTVSeriesDetail(id);
        call.enqueue(new Callback<TVSeriesDetail>() {
            @Override
            public void onResponse(Call<TVSeriesDetail> call, Response<TVSeriesDetail> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TVSeriesDetail> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }


    public LiveData<TVSeasonsDetail> getTVSeasonDetail(int id,int number){
        MutableLiveData<TVSeasonsDetail> liveData = new MutableLiveData<>();
        Call<TVSeasonsDetail> call=apiService.getTVSeasonsDetail(id,number);
        call.enqueue(new Callback<TVSeasonsDetail>() {
            @Override
            public void onResponse(Call<TVSeasonsDetail> call, Response<TVSeasonsDetail> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TVSeasonsDetail> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    public LiveData<TVEpisodeDetail> getTVEpisodeDetail(int id,int seasonNumber,int episodeNumber){
        MutableLiveData<TVEpisodeDetail> liveData = new MutableLiveData<>();
        Call<TVEpisodeDetail> call=apiService.getTVEpisodeDetail(id,seasonNumber,episodeNumber);
        call.enqueue(new Callback<TVEpisodeDetail>() {
            @Override
            public void onResponse(Call<TVEpisodeDetail> call, Response<TVEpisodeDetail> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TVEpisodeDetail> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    public LiveData<TVEpisodeImage> getTVEpisodeImage(int id,int seasonNumber,int episodeNumber){
        MutableLiveData<TVEpisodeImage> liveData = new MutableLiveData<>();
        Call<TVEpisodeImage> call=apiService.getTVEpisodeImages(id,seasonNumber,episodeNumber);
        call.enqueue(new Callback<TVEpisodeImage>() {
            @Override
            public void onResponse(Call<TVEpisodeImage> call, Response<TVEpisodeImage> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TVEpisodeImage> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }


    public LiveData<TVEpisodeVideo> getTVEpisodeVideo(int id,int seasonNumber,int episodeNumber){
        MutableLiveData<TVEpisodeVideo> liveData = new MutableLiveData<>();
        Call<TVEpisodeVideo> call=apiService.getTVEpisodeVideos(id,seasonNumber,episodeNumber);
        call.enqueue(new Callback<TVEpisodeVideo>() {
            @Override
            public void onResponse(Call<TVEpisodeVideo> call, Response<TVEpisodeVideo> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TVEpisodeVideo> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }


    public LiveData<TVEpisodeCredit> getTVEpisodeCredit(int id, int seasonNumber, int episodeNumber){
        MutableLiveData<TVEpisodeCredit> liveData = new MutableLiveData<>();
        Call<TVEpisodeCredit> call=apiService.getTVEpisodeCredits(id,seasonNumber,episodeNumber);
        call.enqueue(new Callback<TVEpisodeCredit>() {
            @Override
            public void onResponse(Call<TVEpisodeCredit> call, Response<TVEpisodeCredit> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TVEpisodeCredit> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
