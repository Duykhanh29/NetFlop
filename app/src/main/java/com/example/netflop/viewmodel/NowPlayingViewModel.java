package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.responses.NowPlayingResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingViewModel extends ViewModel {
    private MutableLiveData<NowPlayingResponse>  nowPlayingData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<Movie>> listMovieData;
    List<Movie> listMovie;
    public NowPlayingViewModel(){
        nowPlayingData=new MutableLiveData<>();
        listMovieData=new MutableLiveData<>();
        listMovie=new ArrayList<>();
    }
    public MutableLiveData<NowPlayingResponse> getNowPlayingData(){
        return  nowPlayingData;
    }
    public MutableLiveData<List<Movie>> getListMovieData(){
        return listMovieData;
    }
    public void loadNextPage() {
        if (!isLoading) {
            isLoading = true;
            currentPage++;
            callAPI();
        }
    }
    public void callAPI(){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<NowPlayingResponse> call=apiService.getNowPlaying(currentPage);
        call.enqueue(new Callback<NowPlayingResponse>() {
            @Override
            public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
                if(response.code()==200){
                    nowPlayingData.postValue(response.body());
//                    if(nowPlayingData.getValue().getResults()!=null){
//                        listMovie=nowPlayingData.getValue().getResults();
//                        List<Movie> currentMovie=listMovieData.getValue();
//                        if(currentMovie!=null){
//                            currentMovie.addAll(listMovie);
//                            listMovieData.postValue(currentMovie);
//                            listMovie.clear();
//                        }
//                    }
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<NowPlayingResponse> call, Throwable t) {
                nowPlayingData.postValue(null);
                listMovieData.postValue(null);
            }
        });
    }
}
