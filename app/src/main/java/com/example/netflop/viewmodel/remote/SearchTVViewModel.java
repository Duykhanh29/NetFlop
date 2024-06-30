package com.example.netflop.viewmodel.remote;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.data.models.remote.TVs.AiringTodayModel;
import com.example.netflop.data.repository.remote.SearchRepository;
import com.example.netflop.data.responses.TVs.SearchTVResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchTVViewModel extends ViewModel {
    private MutableLiveData<SearchTVResponse> searchData;
    private MutableLiveData<List<AiringTodayModel>> listAiringTodayModelData;
    List<AiringTodayModel> list;
    int currentPage=1;
    SearchRepository searchRepository;

    public SearchTVViewModel() {
        this.searchData = new MutableLiveData<>();
        this.listAiringTodayModelData=new MutableLiveData<>();
        this.list=new ArrayList<>();
        searchRepository=new SearchRepository();
    }
    public void resetCurrentPage(){
        currentPage=1;
    }

    public MutableLiveData<SearchTVResponse> getSearchData() {
        return searchData;
    }

    public MutableLiveData<List<AiringTodayModel>> getListAiringTodayModelData() {
        return listAiringTodayModelData;
    }

    public void loadNextPage(String query, boolean includeAdult, LifecycleOwner lifecycleOwner) {
        currentPage++;
//        callAPI(query,includeAdult);
        searchTVShows(query,includeAdult,lifecycleOwner);
    }
    public void searchTVShows(String query, boolean includeAdult, LifecycleOwner lifecycleOwner){
        searchRepository.searchTVs(query,includeAdult,currentPage).observe(lifecycleOwner, new Observer<SearchTVResponse>() {
            @Override
            public void onChanged(SearchTVResponse searchTVResponse) {
                if (searchTVResponse!= null && searchTVResponse.getResults()!= null) {
                    searchData.postValue(searchTVResponse);
                    List<AiringTodayModel> results = searchTVResponse.getResults();
                    if(results!=null){
                        List<AiringTodayModel> currentTV = new ArrayList<>();
                        if(currentPage!=1){
                            currentTV= listAiringTodayModelData.getValue();
                            if (currentTV == null) {
                                currentTV = new ArrayList<>();
                            }
                        }
                        currentTV.addAll(results);
                        listAiringTodayModelData.postValue(currentTV);
                    }
                } else {
                    searchData.postValue(null);
                    listAiringTodayModelData.postValue(null);
                    Log.e("TAG", "Response body is null");
                }
            }
        });
    }

//    public void callAPI(String query, boolean includeAdult){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<SearchTVResponse> call=apiService.getSearchTV(query,includeAdult,currentPage);
//        call.enqueue(new Callback<SearchTVResponse>() {
//            @Override
//            public void onResponse(Call<SearchTVResponse> call, Response<SearchTVResponse> response) {
//
//                if(response.code()==200){
//                    SearchTVResponse searchTVResponse = response.body();
//                    if (searchTVResponse != null) {
//                        searchData.postValue(searchTVResponse);
//                        List<AiringTodayModel> results = searchTVResponse.getResults();
//                        if (results != null) {
//                            List<AiringTodayModel> currentMovies = new ArrayList<>();
//                            if(currentPage!=1){
//                                currentMovies= listAiringTodayModelData.getValue();
//                                if (currentMovies == null) {
//                                    currentMovies = new ArrayList<>();
//                                }
//                            }
//                            currentMovies.addAll(results);
//                            listAiringTodayModelData.postValue(currentMovies);
//                        }
//                    } else {
//                        Log.e("TAG", "Response body is null");
//                    }
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                    searchData.postValue(null);
//                    listAiringTodayModelData.postValue(null);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SearchTVResponse> call, Throwable t) {
//                searchData.postValue(null);
//                listAiringTodayModelData.postValue(null);
//            }
//        });
//    }
    public void resetData() {
        list.clear();
        listAiringTodayModelData.postValue(new ArrayList<>());
        searchData.postValue(null);
        currentPage = 1;
    }
}
