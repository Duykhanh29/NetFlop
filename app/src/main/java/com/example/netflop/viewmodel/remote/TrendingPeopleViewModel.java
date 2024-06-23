package com.example.netflop.viewmodel.remote;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.data.models.remote.people.Person;
import com.example.netflop.data.repository.remote.TrendingPeopleRepository;
import com.example.netflop.data.responses.people.TrendingPeopleResponse;

import java.util.ArrayList;
import java.util.List;

public class TrendingPeopleViewModel extends ViewModel {
    private MutableLiveData<TrendingPeopleResponse> trendingPeopleData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<Person>> listPeopleData;
    List<Person> listPeople;
    TrendingPeopleRepository trendingPeopleRepository;
    public TrendingPeopleViewModel(){
        trendingPeopleData=new MutableLiveData<>();
        listPeopleData=new MutableLiveData<>();
        listPeople=new ArrayList<>();
        trendingPeopleRepository=new TrendingPeopleRepository();
    }
    public MutableLiveData<TrendingPeopleResponse> getTrendingPeopleData(){
        return  trendingPeopleData;
    }
    public MutableLiveData<List<Person>> getListPeopleData(){
        return listPeopleData;
    }
    public void loadNextPage(LifecycleOwner lifecycleOwner) {
        currentPage++;
//        callAPI();
        fetchTrendingPeople(lifecycleOwner);
    }
    public void fetchTrendingPeople(LifecycleOwner lifecycleOwner) {
        LiveData<TrendingPeopleResponse> liveData = trendingPeopleRepository.getTrendingPeople(currentPage);
        liveData.observe(lifecycleOwner, new Observer<TrendingPeopleResponse>() {
            @Override
            public void onChanged(TrendingPeopleResponse trendingPeopleResponse) {
                if (trendingPeopleResponse!= null && trendingPeopleResponse.getResults()!= null) {
                    trendingPeopleData.postValue(trendingPeopleResponse);
                    List<Person> currentPeople = listPeopleData.getValue();
                    if (currentPeople == null) {
                        currentPeople = new ArrayList<>();
                    }
                    currentPeople.addAll(trendingPeopleResponse.getResults());
                    listPeopleData.postValue(currentPeople);
                } else {
                    Log.e("TAG", "Response body is null");
                }
            }
        });

    }
//    public void callAPI(){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<TrendingPeopleResponse> call=apiService.getTrendingPeople(currentPage);
//        call.enqueue(new Callback<TrendingPeopleResponse>() {
//            @Override
//            public void onResponse(Call<TrendingPeopleResponse> call, Response<TrendingPeopleResponse> response) {
//                if(response.code()==200){
//                    TrendingPeopleResponse trendingPeopleResponse = response.body();
//                    if (trendingPeopleResponse != null) {
//                        trendingPeopleData.postValue(trendingPeopleResponse);
//                        List<Person> results = trendingPeopleResponse.getResults();
//                        if (results != null) {
//                            List<Person> currentMovies = listPeopleData.getValue();
//                            if (currentMovies == null) {
//                                currentMovies = new ArrayList<>();
//                            }
//                            currentMovies.addAll(results);
//                            listPeopleData.postValue(currentMovies);
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
//            public void onFailure(Call<TrendingPeopleResponse> call, Throwable t) {
//                trendingPeopleData.postValue(null);
//                listPeopleData.postValue(null);
//            }
//        });
//    }
}
