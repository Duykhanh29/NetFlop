package com.example.netflop.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.models.CombinedCredit;
import com.example.netflop.data.models.Credit;
import com.example.netflop.data.models.MovieDetail;
import com.example.netflop.data.models.MovieImages;
import com.example.netflop.data.models.MovieVideos;
import com.example.netflop.data.models.PersonDetail;
import com.example.netflop.data.models.PersonImages;
import com.example.netflop.data.responses.RecommendationMovieResponse;
import com.example.netflop.data.responses.ReviewResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.utils.AppExecutors;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepositories {
    AppExecutors appExecutors;
    APIService apiService;
    public void getTrendingMovie(){
        final Future myHandler=AppExecutors.getInstance().getNetworkIO().submit(new Runnable() {
            @Override
            public void run() {

            }
        });
        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },4000, TimeUnit.MILLISECONDS);
    }

    public MovieRepositories() {
        apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
    }
    public LiveData<MovieDetail> getMovieDetailByID(int ID) {
        MutableLiveData<MovieDetail> mutableLiveData = new MutableLiveData<>();
        Call<MovieDetail> call=apiService.getMovieByID(ID);
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.postValue(response.body());
                } else {
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }
    public LiveData<MovieImages> getMovieImagesByID(int ID) {
        MutableLiveData<MovieImages> mutableLiveData = new MutableLiveData<>();
        Call<MovieImages> call=apiService.getMovieImages(ID);
        call.enqueue(new Callback<MovieImages>() {
            @Override
            public void onResponse(Call<MovieImages> call, Response<MovieImages> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.postValue(response.body());
                } else {
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MovieImages> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }
    public LiveData<MovieVideos> getMovieVideosByID(int ID) {
        MutableLiveData<MovieVideos> mutableLiveData = new MutableLiveData<>();
        Call<MovieVideos> call=apiService.getMovieVideos(ID);
        call.enqueue(new Callback<MovieVideos>() {
            @Override
            public void onResponse(Call<MovieVideos> call, Response<MovieVideos> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.postValue(response.body());
                } else {
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MovieVideos> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }

    public LiveData<ReviewResponse> getReviewByID(int ID,int page) {
        MutableLiveData<ReviewResponse> mutableLiveData = new MutableLiveData<>();
        Call<ReviewResponse> call=apiService.getReviewOfAMovie(ID,page);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.postValue(response.body());
                } else {
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }
    public LiveData<Credit> getCreditByID(int ID) {
        MutableLiveData<Credit> mutableLiveData = new MutableLiveData<>();
        Call<Credit> call=apiService.getMovieCredit(ID);
        call.enqueue(new Callback<Credit>() {
            @Override
            public void onResponse(Call<Credit> call, Response<Credit> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.postValue(response.body());
                } else {
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Credit> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }
    public LiveData<RecommendationMovieResponse> getRecommendationByID(int ID) {
        MutableLiveData<RecommendationMovieResponse> mutableLiveData = new MutableLiveData<>();
        Call<RecommendationMovieResponse> call=apiService.getRecommendationMovie(ID);
        call.enqueue(new Callback<RecommendationMovieResponse>() {
            @Override
            public void onResponse(Call<RecommendationMovieResponse> call, Response<RecommendationMovieResponse> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.postValue(response.body());
                } else {
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<RecommendationMovieResponse> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }
}
