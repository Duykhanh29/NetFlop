package com.example.netflop.viewmodel.remote;


import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.data.repository.remote.TrendingMovieRepository;
import com.example.netflop.data.responses.movies.TrendingMovieResponse;

import java.util.ArrayList;
import java.util.List;

public class TrendingMovieViewModel extends ViewModel {
    private MutableLiveData<TrendingMovieResponse> trendingMovieData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<Movie>> listMovieData;
    List<Movie> listMovie;
    TrendingMovieRepository trendingMovieRepository;
    public TrendingMovieViewModel(){
        trendingMovieData=new MutableLiveData<>();
        listMovieData=new MutableLiveData<>();
        listMovie=new ArrayList<>();
        trendingMovieRepository=new TrendingMovieRepository();
    }
    public MutableLiveData<TrendingMovieResponse> getTrendingMovieData(){
        return  trendingMovieData;
    }
    public MutableLiveData<List<Movie>> getListMovieData(){
        return listMovieData;
    }
    public void loadNextPage(LifecycleOwner lifecycleOwner) {
            currentPage++;
//            callAPI();
        fetchTrendingMovie(lifecycleOwner);
    }
    public void fetchTrendingMovie(LifecycleOwner lifecycleOwner) {
        LiveData<TrendingMovieResponse> liveData = trendingMovieRepository.getTrendingMovie(currentPage);
        liveData.observe(lifecycleOwner, new Observer<TrendingMovieResponse>() {
            @Override
            public void onChanged(TrendingMovieResponse trendingMovieResponse) {
                if (trendingMovieResponse!= null && trendingMovieResponse.getResults()!= null) {
                    trendingMovieData.postValue(trendingMovieResponse);
                    List<Movie> currentMovies = listMovieData.getValue();
                    if (currentMovies == null) {
                        currentMovies = new ArrayList<>();
                    }
                    currentMovies.addAll(trendingMovieResponse.getResults());
                    listMovieData.postValue(currentMovies);
                } else {
                    Log.e("TAG", "Response body is null");
                }
            }
        });
    }
//    public void callAPI(){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<TrendingMovieResponse> call=apiService.getTrendingMovies(currentPage);
//        call.enqueue(new Callback<TrendingMovieResponse>() {
//            @Override
//            public void onResponse(Call<TrendingMovieResponse> call, Response<TrendingMovieResponse> response) {
//
//                if(response.code()==200){
//                    TrendingMovieResponse trendingMovieResponse = response.body();
//                    if (trendingMovieResponse != null) {
//                        trendingMovieData.postValue(trendingMovieResponse);
//                        List<Movie> results = trendingMovieResponse.getResults();
//                        if (results != null) {
//                            List<Movie> currentMovies = listMovieData.getValue();
//                            if (currentMovies == null) {
//                                currentMovies = new ArrayList<>();
//                            }
//                            currentMovies.addAll(results);
//                            listMovieData.postValue(currentMovies);
//                        }
//                    } else {
//                        Log.e("TAG", "Response body is null");
//                    }
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TrendingMovieResponse> call, Throwable t) {
//                trendingMovieData.postValue(null);
//                listMovieData.postValue(null);
//            }
//        });
//    }
}
