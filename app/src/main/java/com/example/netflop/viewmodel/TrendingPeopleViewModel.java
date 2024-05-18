package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.models.Person;
import com.example.netflop.data.responses.NowPlayingResponse;
import com.example.netflop.data.responses.TrendingPeopleResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingPeopleViewModel extends ViewModel {
    private MutableLiveData<TrendingPeopleResponse> trendingPeopleData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<Person>> listPeopleData;
    List<Person> listPeople;
    public TrendingPeopleViewModel(){
        trendingPeopleData=new MutableLiveData<>();
        listPeopleData=new MutableLiveData<>();
        listPeople=new ArrayList<>();
    }
    public MutableLiveData<TrendingPeopleResponse> getTrendingPeopleData(){
        return  trendingPeopleData;
    }
    public MutableLiveData<List<Person>> getListPeopleData(){
        return listPeopleData;
    }
    public void loadNextPage() {
        currentPage++;
        callAPI();
    }
    public void callAPI(){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<TrendingPeopleResponse> call=apiService.getTrendingPeople(currentPage);
        call.enqueue(new Callback<TrendingPeopleResponse>() {
            @Override
            public void onResponse(Call<TrendingPeopleResponse> call, Response<TrendingPeopleResponse> response) {
                if(response.code()==200){
                    TrendingPeopleResponse trendingPeopleResponse = response.body();
                    if (trendingPeopleResponse != null) {
                        trendingPeopleData.postValue(trendingPeopleResponse);
                        List<Person> results = trendingPeopleResponse.getResults();
                        if (results != null) {
                            List<Person> currentMovies = listPeopleData.getValue();
                            if (currentMovies == null) {
                                currentMovies = new ArrayList<>();
                            }
                            currentMovies.addAll(results);
                            listPeopleData.postValue(currentMovies);
                        }
                    } else {
                        Log.e("TAG", "Response body is null");
                    }
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                }
            }

            @Override
            public void onFailure(Call<TrendingPeopleResponse> call, Throwable t) {
                trendingPeopleData.postValue(null);
                listPeopleData.postValue(null);
            }
        });
    }
}
