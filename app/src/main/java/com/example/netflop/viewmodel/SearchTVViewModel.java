package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.models.TVs.AiringTodayModel;
import com.example.netflop.data.responses.TVs.SearchTVResponse;
import com.example.netflop.data.services.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTVViewModel extends ViewModel {
    private MutableLiveData<SearchTVResponse> searchData;
    private MutableLiveData<List<AiringTodayModel>> listAiringTodayModelData;
    List<AiringTodayModel> list;
    int currentPage=1;

    public SearchTVViewModel() {
        this.searchData = new MutableLiveData<>();
        this.listAiringTodayModelData=new MutableLiveData<>();
        this.list=new ArrayList<>();
    }

    public MutableLiveData<SearchTVResponse> getSearchData() {
        return searchData;
    }

    public MutableLiveData<List<AiringTodayModel>> getListAiringTodayModelData() {
        return listAiringTodayModelData;
    }

    public void loadNextPage(String query, boolean includeAdult) {
        currentPage++;
        callAPI(query,includeAdult);
    }

    public void callAPI(String query, boolean includeAdult){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<SearchTVResponse> call=apiService.getSearchTV(query,includeAdult,currentPage);
        call.enqueue(new Callback<SearchTVResponse>() {
            @Override
            public void onResponse(Call<SearchTVResponse> call, Response<SearchTVResponse> response) {

                if(response.code()==200){
                    SearchTVResponse searchTVResponse = response.body();
                    if (searchTVResponse != null) {
                        searchData.postValue(searchTVResponse);
                        List<AiringTodayModel> results = searchTVResponse.getResults();
                        if (results != null) {
                            List<AiringTodayModel> currentMovies = new ArrayList<>();
                            if(currentPage!=1){
                                currentMovies= listAiringTodayModelData.getValue();
                                if (currentMovies == null) {
                                    currentMovies = new ArrayList<>();
                                }
                            }
                            currentMovies.addAll(results);
                            listAiringTodayModelData.postValue(currentMovies);
                        }
                    } else {
                        Log.e("TAG", "Response body is null");
                    }
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                    searchData.postValue(null);
                    listAiringTodayModelData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<SearchTVResponse> call, Throwable t) {
                searchData.postValue(null);
                listAiringTodayModelData.postValue(null);
            }
        });
    }
    public void resetData() {
        list.clear();
        listAiringTodayModelData.postValue(new ArrayList<>());
        searchData.postValue(null);
        currentPage = 1;
    }
}
