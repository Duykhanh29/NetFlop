package com.example.netflop.viewmodel.remote;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.data.repository.remote.NowPlayingRepository;
import com.example.netflop.data.responses.movies.NowPlayingResponse;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingViewModel extends ViewModel {
    private MutableLiveData<NowPlayingResponse>  nowPlayingData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<Movie>> listMovieData;
    NowPlayingRepository nowPlayingRepository;
    List<Movie> listMovie;
    public NowPlayingViewModel(){
        nowPlayingData=new MutableLiveData<>();
        listMovieData=new MutableLiveData<>();
        listMovie=new ArrayList<>();
        nowPlayingRepository=new NowPlayingRepository();
    }
    public MutableLiveData<NowPlayingResponse> getNowPlayingData(){
        return  nowPlayingData;
    }
    public MutableLiveData<List<Movie>> getListMovieData(){
        return listMovieData;
    }
    public void loadNextPage(LifecycleOwner lifecycleOwner) {
//        if (!isLoading) {
//            isLoading = true;
            currentPage++;
//            callAPI();
        fetchNowPlaying(lifecycleOwner);
//        }
    }
    public void fetchNowPlaying(LifecycleOwner lifecycleOwner) {
        LiveData<NowPlayingResponse> liveData = nowPlayingRepository.getNowPlayingMovie(currentPage);

        liveData.observe(lifecycleOwner, new Observer<NowPlayingResponse>() {
            @Override
            public void onChanged(NowPlayingResponse nowPlayingResponse) {
                if (nowPlayingResponse!= null && nowPlayingResponse.getResults()!= null) {
                    nowPlayingData.postValue(nowPlayingResponse);
                    List<Movie> currentMovies = listMovieData.getValue();
                    if (currentMovies == null) {
                        currentMovies = new ArrayList<>();
                    }
                    currentMovies.addAll(nowPlayingResponse.getResults());
                    listMovieData.postValue(currentMovies);
                } else {
                    Log.e("TAG", "Response body is null");
                }
            }
        });
    }
//    public void callAPI(){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<NowPlayingResponse> call=apiService.getNowPlaying(currentPage);
//        call.enqueue(new Callback<NowPlayingResponse>() {
//            @Override
//            public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
//                if(response.code()==200){
//                    NowPlayingResponse nowPlayingResponse = response.body();
//                    if (nowPlayingResponse != null) {
//                        nowPlayingData.postValue(nowPlayingResponse);
//                        List<Movie> results = nowPlayingResponse.getResults();
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
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.errorBody());
//                }
//            }
//            @Override
//            public void onFailure(Call<NowPlayingResponse> call, Throwable t) {
//                nowPlayingData.postValue(null);
//                listMovieData.postValue(null);
//            }
//        });
//    }
}
