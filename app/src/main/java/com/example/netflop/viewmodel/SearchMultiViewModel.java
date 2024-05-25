package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Person;
import com.example.netflop.data.models.SearchMultiModel;
import com.example.netflop.data.repository.SearchRepository;
import com.example.netflop.data.responses.SearchMultiResponse;
import com.example.netflop.data.responses.SearchPersonResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.data_source.remote_data_source.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMultiViewModel extends ViewModel {
    private MutableLiveData<SearchMultiResponse> searchMultiData;
    private MutableLiveData<List<SearchMultiModel>> listSearchMulti;
    List<SearchMultiModel> list;
    int currentPage=1;
    SearchRepository searchRepository;
    public SearchMultiViewModel(){
        this.searchMultiData = new MutableLiveData<>();
        list=new ArrayList<>();
        listSearchMulti=new MutableLiveData<>();
        searchRepository=new SearchRepository();
    }

    public MutableLiveData<List<SearchMultiModel>> getListSearchMulti() {
        return listSearchMulti;
    }

    public MutableLiveData<SearchMultiResponse> getSearchMultiData() {
        return searchMultiData;
    }
    public void loadNextPage(String query,boolean includeAdult,LifecycleOwner lifecycleOwner) {
        currentPage++;
//        callAPI(query,includeAdult);
        searchMultiple(query,includeAdult,lifecycleOwner);
    }

    public void searchMultiple(String query, boolean includeAdult, LifecycleOwner lifecycleOwner){

        searchRepository.searchMultiple(query,includeAdult,currentPage).observe(lifecycleOwner, new Observer<SearchMultiResponse>() {
            @Override
            public void onChanged(SearchMultiResponse searchMultiResponse) {
                if (searchMultiResponse!= null && searchMultiResponse.getResults()!= null) {
                    searchMultiData.postValue(searchMultiResponse);
                    List<SearchMultiModel> results = searchMultiResponse.getResults();
                    if(results!=null){
                        List<SearchMultiModel> currentList = new ArrayList<>();
                        if(currentPage!=1){
                            currentList= listSearchMulti.getValue();
                            if (currentList == null) {
                                currentList = new ArrayList<>();
                            }
                        }
                        currentList.addAll(results);
                        listSearchMulti.postValue(currentList);
                    }
                } else {
                    searchMultiData.postValue(null);
                    listSearchMulti.postValue(null);
                    Log.e("TAG", "Response body is null");
                }
            }
        });

    }
//    public void callAPI(String query,boolean includeAdult){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<SearchMultiResponse> call=apiService.getSearchMultiSource(query,includeAdult,currentPage);
//        call.enqueue(new Callback<SearchMultiResponse>() {
//            @Override
//            public void onResponse(Call<SearchMultiResponse> call, Response<SearchMultiResponse> response) {
//
//                if(response.code()==200){
//                    SearchMultiResponse searchMultiResponse = response.body();
//                    if (searchMultiResponse != null) {
//                        searchMultiData.postValue(searchMultiResponse);
//                        List<SearchMultiModel> results = searchMultiResponse.getResults();
//                        if (results != null) {
//                            List<SearchMultiModel> currentMovies =new ArrayList<>();
//                            if(currentPage!=1){
//                                currentMovies= listSearchMulti.getValue();
//                                if (currentMovies == null) {
//                                    currentMovies = new ArrayList<>();
//                                }
//                            }
//                            currentMovies.addAll(results);
//                            listSearchMulti.postValue(currentMovies);
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
//            public void onFailure(Call<SearchMultiResponse> call, Throwable t) {
//                searchMultiData.postValue(null);
//                listSearchMulti.postValue(null);
//            }
//        });
//    }
    public void resetData() {
        list.clear();
        listSearchMulti.postValue(new ArrayList<>());
        searchMultiData.postValue(null);
        currentPage = 1;
    }
}
