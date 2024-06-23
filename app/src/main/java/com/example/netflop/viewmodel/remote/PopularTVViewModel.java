package com.example.netflop.viewmodel.remote;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.data.models.remote.TVs.AiringTodayModel;
import com.example.netflop.data.repository.remote.PopularTVRepository;
import com.example.netflop.data.responses.TVs.PopularTVResponse;

import java.util.ArrayList;
import java.util.List;

public class PopularTVViewModel extends ViewModel {
    private MutableLiveData<PopularTVResponse> popularTVData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<AiringTodayModel>> listPopularTV;
    List<AiringTodayModel> listTV;
    private PopularTVRepository repository;
    public PopularTVViewModel(){
        popularTVData=new MutableLiveData<>();
        listPopularTV=new MutableLiveData<>();
        listTV=new ArrayList<>();
        repository=new PopularTVRepository();
    }
    public MutableLiveData<PopularTVResponse> getPopularTVData(){
        return  popularTVData;
    }
    public MutableLiveData<List<AiringTodayModel>> getListPopularTV(){
        return listPopularTV;
    }
    public void loadNextPage(LifecycleOwner lifecycleOwner) {
        currentPage++;
//        callAPI();
        fetchPopularTV(lifecycleOwner);
    }
    public void fetchPopularTV(LifecycleOwner lifecycleOwner) {
        LiveData<PopularTVResponse> liveData = repository.getPopularTV(currentPage);
        liveData.observe(lifecycleOwner, new Observer<PopularTVResponse>() {
                    @Override
                    public void onChanged(PopularTVResponse popularTVResponse) {
                        if (popularTVResponse!= null && popularTVResponse.getResults()!= null) {
                            popularTVData.postValue(popularTVResponse);
                            List<AiringTodayModel> currentTV = listPopularTV.getValue();
                            if (currentTV == null) {
                                currentTV = new ArrayList<>();
                            }
                            currentTV.addAll(popularTVResponse.getResults());
                            listPopularTV.postValue(currentTV);
                        } else {
                            Log.e("TAG", "Response body is null");
                        }
                    }
                });
    }
//    public void callAPI(){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<PopularTVResponse> call=apiService.getPopularTV(currentPage);
//        call.enqueue(new Callback<PopularTVResponse>() {
//            @Override
//            public void onResponse(Call<PopularTVResponse> call, Response<PopularTVResponse> response) {
//                if(response.code()==200){
//                    PopularTVResponse popularPeopleResponse = response.body();
//                    if (popularPeopleResponse != null) {
//                        popularTVData.postValue(popularPeopleResponse);
//                        List<AiringTodayModel> results = popularPeopleResponse.getResults();
//                        if (results != null) {
//                            List<AiringTodayModel> currentMovies = listPopularTV.getValue();
//                            if (currentMovies == null) {
//                                currentMovies = new ArrayList<>();
//                            }
//                            currentMovies.addAll(results);
//                            listPopularTV.postValue(currentMovies);
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
//            public void onFailure(Call<PopularTVResponse> call, Throwable t) {
//                popularTVData.postValue(null);
//                listPopularTV.postValue(null);
//            }
//        });
//    }
}
