package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.responses.TopRatedResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.data_source.remote_data_source.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedViewModel extends ViewModel {
    private MutableLiveData<TopRatedResponse> topRatedData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<Movie>> listMovieData;
    List<Movie> listMovie;
    public TopRatedViewModel(){
        topRatedData=new MutableLiveData<>();
        listMovieData=new MutableLiveData<>();
        listMovie=new ArrayList<>();
    }
    public MutableLiveData<TopRatedResponse> getTopRatedData(){
        return  topRatedData;
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
        Call<TopRatedResponse> call=apiService.getTopRated(currentPage);
        call.enqueue(new Callback<TopRatedResponse>() {
            @Override
            public void onResponse(Call<TopRatedResponse> call, Response<TopRatedResponse> response) {

                if(response.code()==200){
                    topRatedData.postValue(response.body());

                    TopRatedResponse topRatedResponse = response.body();
                    if (topRatedResponse != null) {
                        topRatedData.postValue(topRatedResponse);
                        List<Movie> results = topRatedResponse.getResults();
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
            public void onFailure(Call<TopRatedResponse> call, Throwable t) {
                topRatedData.postValue(null);
                listMovieData.postValue(null);
            }
        });
    }
}
