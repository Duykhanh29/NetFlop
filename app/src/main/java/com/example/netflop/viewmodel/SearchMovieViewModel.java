package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.responses.SearchMovieResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMovieViewModel extends ViewModel {
    private MutableLiveData<SearchMovieResponse> searchMovieData;
    public SearchMovieViewModel(){
        this.searchMovieData = new MutableLiveData<>();
    }

    public MutableLiveData<SearchMovieResponse> getSearchMovieData() {
        return searchMovieData;
    }
    public void makeAPICall(String query,boolean includeAdult,int page){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<SearchMovieResponse> call=apiService.getSearchMovies(query,includeAdult,page);
        call.enqueue(new Callback<SearchMovieResponse>() {
            @Override
            public void onResponse(Call<SearchMovieResponse> call, Response<SearchMovieResponse> response) {

                if(response.code()==200){
                    searchMovieData.postValue(response.body());
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                }
            }

            @Override
            public void onFailure(Call<SearchMovieResponse> call, Throwable t) {
                searchMovieData.postValue(null);
            }
        });
    }
}
