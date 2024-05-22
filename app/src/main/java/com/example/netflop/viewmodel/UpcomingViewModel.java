package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.responses.UpcomingResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.data_source.remote_data_source.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingViewModel extends ViewModel {
    private MutableLiveData<UpcomingResponse> upcomingData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<Movie>> listMovieData;
    List<Movie> listMovie;
    public UpcomingViewModel(){
        upcomingData=new MutableLiveData<>();
        listMovieData=new MutableLiveData<>();
        listMovie=new ArrayList<>();
    }
    public MutableLiveData<UpcomingResponse> getUpcomingData(){
        return  upcomingData;
    }
    public MutableLiveData<List<Movie>> getListMovieData(){
        return listMovieData;
    }
    public void loadNextPage() {
        currentPage++;
        callAPI();
    }
    public void callAPI(){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<UpcomingResponse> call=apiService.getUpcoming(currentPage);
        call.enqueue(new Callback<UpcomingResponse>() {
            @Override
            public void onResponse(Call<UpcomingResponse> call, Response<UpcomingResponse> response) {
                if(response.code()==200){
                    UpcomingResponse upcomingResponse = response.body();
                    if (upcomingResponse != null) {
                        upcomingData.postValue(upcomingResponse);
                        List<Movie> results = upcomingResponse.getResults();
                        if (results != null) {
                            List<Movie> currentMovies = listMovieData.getValue();
                            if (currentMovies == null) {
                                currentMovies = new ArrayList<>();
                            }
                            currentMovies.addAll(results);
                            listMovieData.postValue(currentMovies);
                        }
                    } else {
                        Log.e("TAG", "Response body is null");
                    }
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                }
            }

            @Override
            public void onFailure(Call<UpcomingResponse> call, Throwable t) {
                upcomingData.postValue(null);
                listMovieData.postValue(null);
            }
        });
    }
}
