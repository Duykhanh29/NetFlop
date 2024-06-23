package com.example.netflop.viewmodel.remote;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.data.models.remote.people.Person;
import com.example.netflop.data.repository.remote.PopularPeopleRepository;
import com.example.netflop.data.responses.people.PopularPeopleResponse;

import java.util.ArrayList;
import java.util.List;

public class PopularPeopleViewModel extends ViewModel {
    private MutableLiveData<PopularPeopleResponse> popularPeopleData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<Person>> listPeopleData;
    List<Person> listPeople;
    PopularPeopleRepository popularPeopleRepository;
    public PopularPeopleViewModel(){
        popularPeopleData=new MutableLiveData<>();
        listPeopleData=new MutableLiveData<>();
        listPeople=new ArrayList<>();
        popularPeopleRepository=new PopularPeopleRepository();
    }
    public MutableLiveData<PopularPeopleResponse> getPopularPeopleData(){
        return  popularPeopleData;
    }
    public MutableLiveData<List<Person>> getListPeopleData(){
        return listPeopleData;
    }
    public void loadNextPage(LifecycleOwner lifecycleOwner) {
        currentPage++;
//        callAPI();
        fetchPopularPeople(lifecycleOwner);
    }
    public void fetchPopularPeople(LifecycleOwner lifecycleOwner) {
        LiveData<PopularPeopleResponse> liveData = popularPeopleRepository.getPopularPeople(currentPage);
        liveData.observe(lifecycleOwner, new Observer<PopularPeopleResponse>() {
            @Override
            public void onChanged(PopularPeopleResponse popularPeopleResponse) {
                if (popularPeopleResponse!= null && popularPeopleResponse.getResults()!= null) {
                    popularPeopleData.postValue(popularPeopleResponse);
                    List<Person> currentPeople = listPeopleData.getValue();
                    if (currentPeople == null) {
                        currentPeople = new ArrayList<>();
                    }
                    currentPeople.addAll(popularPeopleResponse.getResults());
                    listPeopleData.postValue(currentPeople);
                } else {
                    Log.e("TAG", "Response body is null");
                }
            }
        });
    }
//    public void callAPI(){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<PopularPeopleResponse> call=apiService.getPopularPeople(currentPage);
//        call.enqueue(new Callback<PopularPeopleResponse>() {
//            @Override
//            public void onResponse(Call<PopularPeopleResponse> call, Response<PopularPeopleResponse> response) {
//                if(response.code()==200){
//                    PopularPeopleResponse popularPeopleResponse = response.body();
//                    if (popularPeopleResponse != null) {
//                        popularPeopleData.postValue(popularPeopleResponse);
//                        List<Person> results = popularPeopleResponse.getResults();
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
//            public void onFailure(Call<PopularPeopleResponse> call, Throwable t) {
//                popularPeopleData.postValue(null);
//                listPeopleData.postValue(null);
//            }
//        });
//    }
}
