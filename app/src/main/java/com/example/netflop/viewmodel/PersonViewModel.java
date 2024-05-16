package com.example.netflop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Person;
import com.example.netflop.data.models.PersonDetail;
import com.example.netflop.data.models.PersonImages;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;

import java.util.List;

public class PersonViewModel extends ViewModel {
    private MutableLiveData<Person> personData;
    private MutableLiveData<PersonDetail> personDetailData;
    private MutableLiveData<PersonImages> personImageData;
    public PersonViewModel(){
        personData=new MutableLiveData<>();
        personDetailData=new MutableLiveData<>();
        personImageData=new MutableLiveData<>();

    }
    public MutableLiveData<Person> getPersonData(){
        return  personData;
    }
    public MutableLiveData<PersonDetail> getPersonDetailData(){
        return  personDetailData;
    }
    public MutableLiveData<PersonImages> getPersonImageData(){
        return  personImageData;
    }
    public void callAPI(int movieID){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<TrendingMovieResponse> call=apiService.getTrendingMovies();
//        call.enqueue(new Callback<TrendingMovieResponse>() {
//            @Override
//            public void onResponse(Call<TrendingMovieResponse> call, Response<TrendingMovieResponse> response) {
//                trendingMovieData.postValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<TrendingMovieResponse> call, Throwable t) {
//                trendingMovieData.postValue(null);
//            }
//        });
    }

}
