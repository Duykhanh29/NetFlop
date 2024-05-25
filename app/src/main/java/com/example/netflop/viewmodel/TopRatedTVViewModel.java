package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.models.TVs.AiringTodayModel;
import com.example.netflop.data.repository.TopRatedTVRepository;
import com.example.netflop.data.responses.TVs.PopularTVResponse;
import com.example.netflop.data.responses.TVs.TopRatedTVResponse;
import com.example.netflop.data.services.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedTVViewModel extends ViewModel {
    private MutableLiveData<TopRatedTVResponse> topRatedTVData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<AiringTodayModel>> listTopRatedTV;
    List<AiringTodayModel> listTV;
    TopRatedTVRepository topRatedTVRepository;
    public TopRatedTVViewModel(){
        topRatedTVData=new MutableLiveData<>();
        listTopRatedTV=new MutableLiveData<>();
        listTV=new ArrayList<>();
        topRatedTVRepository=new TopRatedTVRepository();
    }
    public MutableLiveData<TopRatedTVResponse> getTopRatedTVData(){
        return  topRatedTVData;
    }
    public MutableLiveData<List<AiringTodayModel>> getListTopRatedTV(){
        return listTopRatedTV;
    }
    public void loadNextPage(LifecycleOwner lifecycleOwner) {
        currentPage++;
//        callAPI();
        fetchTopRatedTV(lifecycleOwner);
    }
    public void fetchTopRatedTV(LifecycleOwner lifecycleOwner) {
        LiveData<TopRatedTVResponse> liveData = topRatedTVRepository.getTopRatedTV(currentPage);
        liveData.observe(lifecycleOwner, new Observer<TopRatedTVResponse>() {
            @Override
            public void onChanged(TopRatedTVResponse topRatedTVResponse) {
                if (topRatedTVResponse!= null && topRatedTVResponse.getResults()!= null) {
                    topRatedTVData.postValue(topRatedTVResponse);
                    List<AiringTodayModel> currentTV = listTopRatedTV.getValue();
                    if (currentTV == null) {
                        currentTV = new ArrayList<>();
                    }
                    currentTV.addAll(topRatedTVResponse.getResults());
                    listTopRatedTV.postValue(currentTV);
                } else {
                    Log.e("TAG", "Response body is null");
                }
            }
        });

    }
//    public void callAPI(){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<TopRatedTVResponse> call=apiService.getTopRatedTV(currentPage);
//        call.enqueue(new Callback<TopRatedTVResponse>() {
//            @Override
//            public void onResponse(Call<TopRatedTVResponse> call, Response<TopRatedTVResponse> response) {
//                if(response.code()==200){
//                    TopRatedTVResponse topRatedTVResponse = response.body();
//                    if (topRatedTVResponse != null) {
//                        topRatedTVData.postValue(topRatedTVResponse);
//                        List<AiringTodayModel> results = topRatedTVResponse.getResults();
//                        if (results != null) {
//                            List<AiringTodayModel> currentMovies = listTopRatedTV.getValue();
//                            if (currentMovies == null) {
//                                currentMovies = new ArrayList<>();
//                            }
//                            currentMovies.addAll(results);
//                            listTopRatedTV.postValue(currentMovies);
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
//            public void onFailure(Call<TopRatedTVResponse> call, Throwable t) {
//                topRatedTVData.postValue(null);
//                listTopRatedTV.postValue(null);
//            }
//        });
//    }
}
