package com.example.netflop.viewmodel.remote;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.data.repository.remote.TopRatedMovieRepository;
import com.example.netflop.data.responses.movies.TopRatedResponse;

import java.util.ArrayList;
import java.util.List;

public class TopRatedViewModel extends ViewModel {
    private MutableLiveData<TopRatedResponse> topRatedData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<Movie>> listMovieData;
    List<Movie> listMovie;
    TopRatedMovieRepository topRatedMovieRepository;
    public TopRatedViewModel(){
        topRatedData=new MutableLiveData<>();
        listMovieData=new MutableLiveData<>();
        listMovie=new ArrayList<>();
        topRatedMovieRepository=new TopRatedMovieRepository();
    }
    public MutableLiveData<TopRatedResponse> getTopRatedData(){
        return  topRatedData;
    }
    public MutableLiveData<List<Movie>> getListMovieData(){
        return listMovieData;
    }
    public void loadNextPage(LifecycleOwner lifecycleOwner) {
            currentPage++;
//            callAPI();
        fetchTopRated(lifecycleOwner);
    }
    public void fetchTopRated(LifecycleOwner lifecycleOwner) {
        LiveData<TopRatedResponse> liveData = topRatedMovieRepository.getTopRatedMovie(currentPage);
        liveData.observe(lifecycleOwner, new Observer<TopRatedResponse>() {
            @Override
            public void onChanged(TopRatedResponse topRatedResponse) {
                if (topRatedResponse!= null && topRatedResponse.getResults()!= null) {
                    topRatedData.postValue(topRatedResponse);
                    List<Movie> currentMovies = listMovieData.getValue();
                    if (currentMovies == null) {
                        currentMovies = new ArrayList<>();
                    }
                    currentMovies.addAll(topRatedResponse.getResults());
                    listMovieData.postValue(currentMovies);
                } else {
                    Log.e("TAG", "Response body is null");
                }
            }
        });


    }
//    public void callAPI(){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<TopRatedResponse> call=apiService.getTopRated(currentPage);
//        call.enqueue(new Callback<TopRatedResponse>() {
//            @Override
//            public void onResponse(Call<TopRatedResponse> call, Response<TopRatedResponse> response) {
//
//                if(response.code()==200){
//                    topRatedData.postValue(response.body());
//
//                    TopRatedResponse topRatedResponse = response.body();
//                    if (topRatedResponse != null) {
//                        topRatedData.postValue(topRatedResponse);
//                        List<Movie> results = topRatedResponse.getResults();
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
//            public void onFailure(Call<TopRatedResponse> call, Throwable t) {
//                topRatedData.postValue(null);
//                listMovieData.postValue(null);
//            }
//        });
//    }
}
