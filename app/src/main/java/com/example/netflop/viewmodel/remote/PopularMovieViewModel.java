package com.example.netflop.viewmodel.remote;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.data.repository.remote.PopularMovieRepository;
import com.example.netflop.data.responses.movies.PopularResponse;

import java.util.ArrayList;
import java.util.List;

public class PopularMovieViewModel extends ViewModel {
    private MutableLiveData<PopularResponse> popularMovieData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<Movie>> listMovieData;
    List<Movie> listMovie;
    PopularMovieRepository popularMovieRepository;
    public PopularMovieViewModel(){
        popularMovieData=new MutableLiveData<>();
        listMovieData=new MutableLiveData<>();
        listMovie=new ArrayList<>();
        popularMovieRepository=new PopularMovieRepository();
    }
    public MutableLiveData<List<Movie>> getListMovieData(){
        return  listMovieData;
    }
    public MutableLiveData<PopularResponse> getPopularMovieData(){
        return  popularMovieData;
    }
    public void loadNextPage(LifecycleOwner lifecycleOwner) {
            currentPage++;
//            callAPI();
        fetchPopularMovie(lifecycleOwner);
    }

    public void fetchPopularMovie(LifecycleOwner lifecycleOwner) {
        LiveData<PopularResponse> liveData = popularMovieRepository.getPopularMovie(currentPage);
        liveData.observe(lifecycleOwner, new Observer<PopularResponse>() {
            @Override
            public void onChanged(PopularResponse popularResponse) {
                if (popularResponse!= null && popularResponse.getResults()!= null) {
                    popularMovieData.postValue(popularResponse);
                    List<Movie> currentMovies = listMovieData.getValue();
                    if (currentMovies == null) {
                        currentMovies = new ArrayList<>();
                    }
                    currentMovies.addAll(popularResponse.getResults());
                    listMovieData.postValue(currentMovies);
                } else {
                    Log.e("TAG", "Response body is null");
                }
            }
        });

    }
//    public void callAPI(){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<PopularResponse> call=apiService.getPopular(currentPage);
//        call.enqueue(new Callback<PopularResponse>() {
//            @Override
//            public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
//
//                if(response.code()==200){
//                    PopularResponse popularResponse = response.body();
//                    if (popularResponse != null) {
//                        popularMovieData.postValue(popularResponse);
//                        List<Movie> results = popularResponse.getResults();
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
//
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PopularResponse> call, Throwable t) {
//                popularMovieData.postValue(null);
//                listMovieData.postValue(null);
//            }
//        });
//    }
}
