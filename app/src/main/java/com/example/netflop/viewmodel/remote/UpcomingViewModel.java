package com.example.netflop.viewmodel.remote;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.data.repository.remote.UpcomingRepository;
import com.example.netflop.data.responses.movies.UpcomingResponse;

import java.util.ArrayList;
import java.util.List;

public class UpcomingViewModel extends ViewModel {
    private MutableLiveData<UpcomingResponse> upcomingData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<Movie>> listMovieData;
    List<Movie> listMovie;
    UpcomingRepository upcomingRepository;
    public UpcomingViewModel(){
        upcomingData=new MutableLiveData<>();
        listMovieData=new MutableLiveData<>();
        listMovie=new ArrayList<>();
        upcomingRepository=new UpcomingRepository();
    }
    public MutableLiveData<UpcomingResponse> getUpcomingData(){
        return  upcomingData;
    }
    public MutableLiveData<List<Movie>> getListMovieData(){
        return listMovieData;
    }
    public void loadNextPage(LifecycleOwner lifecycleOwner) {
        currentPage++;
//        callAPI();
        fetchUpcomingMovie(lifecycleOwner);
    }
    public void fetchUpcomingMovie(LifecycleOwner lifecycleOwner) {
        LiveData<UpcomingResponse> liveData = upcomingRepository.getUpcomingMovie(currentPage);
        liveData.observe(lifecycleOwner, new Observer<UpcomingResponse>() {
            @Override
            public void onChanged(UpcomingResponse upcomingResponse) {
                if (upcomingResponse!= null && upcomingResponse.getResults()!= null) {
                    upcomingData.postValue(upcomingResponse);
                    List<Movie> currentMovies = listMovieData.getValue();
                    if (currentMovies == null) {
                        currentMovies = new ArrayList<>();
                    }
                    currentMovies.addAll(upcomingResponse.getResults());
                    listMovieData.postValue(currentMovies);
                } else {
                    Log.e("TAG", "Response body is null");
                }
            }
        });

    }
//    public void callAPI(){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<UpcomingResponse> call=apiService.getUpcoming(currentPage);
//        call.enqueue(new Callback<UpcomingResponse>() {
//            @Override
//            public void onResponse(Call<UpcomingResponse> call, Response<UpcomingResponse> response) {
//                if(response.code()==200){
//                    UpcomingResponse upcomingResponse = response.body();
//                    if (upcomingResponse != null) {
//                        upcomingData.postValue(upcomingResponse);
//                        List<Movie> results = upcomingResponse.getResults();
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
//            public void onFailure(Call<UpcomingResponse> call, Throwable t) {
//                upcomingData.postValue(null);
//                listMovieData.postValue(null);
//            }
//        });
//    }
}
