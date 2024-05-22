package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.models.Person;
import com.example.netflop.data.models.TVs.AiringTodayModel;
import com.example.netflop.data.responses.PopularPeopleResponse;
import com.example.netflop.data.responses.TVs.PopularTVResponse;
import com.example.netflop.data.services.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularTVViewModel extends ViewModel {
    private MutableLiveData<PopularTVResponse> popularTVData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<AiringTodayModel>> listPopularTV;
    List<AiringTodayModel> listTV;
    public PopularTVViewModel(){
        popularTVData=new MutableLiveData<>();
        listPopularTV=new MutableLiveData<>();
        listTV=new ArrayList<>();
    }
    public MutableLiveData<PopularTVResponse> getPopularTVData(){
        return  popularTVData;
    }
    public MutableLiveData<List<AiringTodayModel>> getListPopularTV(){
        return listPopularTV;
    }
    public void loadNextPage() {
        currentPage++;
        callAPI();
    }
    public void callAPI(){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<PopularTVResponse> call=apiService.getPopularTV(currentPage);
        call.enqueue(new Callback<PopularTVResponse>() {
            @Override
            public void onResponse(Call<PopularTVResponse> call, Response<PopularTVResponse> response) {
                if(response.code()==200){
                    PopularTVResponse popularPeopleResponse = response.body();
                    if (popularPeopleResponse != null) {
                        popularTVData.postValue(popularPeopleResponse);
                        List<AiringTodayModel> results = popularPeopleResponse.getResults();
                        if (results != null) {
                            List<AiringTodayModel> currentMovies = listPopularTV.getValue();
                            if (currentMovies == null) {
                                currentMovies = new ArrayList<>();
                            }
                            currentMovies.addAll(results);
                            listPopularTV.postValue(currentMovies);
                        }
                    } else {
                        Log.e("TAG", "Response body is null");
                    }
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                }
            }

            @Override
            public void onFailure(Call<PopularTVResponse> call, Throwable t) {
                popularTVData.postValue(null);
                listPopularTV.postValue(null);
            }
        });
    }
}
