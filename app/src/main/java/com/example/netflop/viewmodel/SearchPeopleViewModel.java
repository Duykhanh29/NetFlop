package com.example.netflop.viewmodel;


import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Person;
import com.example.netflop.data.models.TVs.AiringTodayModel;
import com.example.netflop.data.repository.SearchRepository;
import com.example.netflop.data.responses.SearchPersonResponse;
import com.example.netflop.data.responses.TVs.SearchTVResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.data_source.remote_data_source.APIService;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPeopleViewModel extends ViewModel {
    private MutableLiveData<SearchPersonResponse> searchData;
    private MutableLiveData<List<Person>> listPeopleData;
    List<Person> list;
    int currentPage=1;
    SearchRepository searchRepository;

    public SearchPeopleViewModel() {
        this.searchData = new MutableLiveData<>();
        this.listPeopleData=new MutableLiveData<>();
        this.list=new ArrayList<>();
        searchRepository=new SearchRepository();
    }

    public MutableLiveData<SearchPersonResponse> getSearchPeopleData() {
        return searchData;
    }

    public MutableLiveData<List<Person>> getListPeopleData() {
        return listPeopleData;
    }
    public void loadNextPage(String query,boolean includeAdult ,LifecycleOwner lifecycleOwner) {
        currentPage++;
//        callAPI(query,includeAdult);
        searchPeople(query,includeAdult,lifecycleOwner);
    }

    public void searchPeople(String query, boolean includeAdult, LifecycleOwner lifecycleOwner){

        searchRepository.searchPeople(query,includeAdult,currentPage).observe(lifecycleOwner, new Observer<SearchPersonResponse>() {
            @Override
            public void onChanged(SearchPersonResponse searchPersonResponse) {
                if (searchPersonResponse!= null && searchPersonResponse.getResults()!= null) {
                    searchData.postValue(searchPersonResponse);
                    List<Person> results = searchPersonResponse.getResults();
                    if(results!=null){
                        List<Person> currentPeople = new ArrayList<>();
                        if(currentPage!=1){
                            currentPeople= listPeopleData.getValue();
                            if (currentPeople == null) {
                                currentPeople = new ArrayList<>();
                            }
                        }
                        currentPeople.addAll(results);
                        listPeopleData.postValue(currentPeople);
                    }
                } else {
                    searchData.postValue(null);
                    listPeopleData.postValue(null);
                    Log.e("TAG", "Response body is null");
                }
            }
        });

    }

//    public void callAPI(String query, boolean includeAdult){
//        APIService apiService=APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<SearchPersonResponse> call=apiService.getSearchPeople(query,includeAdult,currentPage);
//        call.enqueue(new Callback<SearchPersonResponse>() {
//            @Override
//            public void onResponse(Call<SearchPersonResponse> call, Response<SearchPersonResponse> response) {
//
//                if(response.code()==200){
//                    SearchPersonResponse searchPersonResponse = response.body();
//                    if (searchPersonResponse != null) {
//                        searchData.postValue(searchPersonResponse);
//                        List<Person> results = searchPersonResponse.getResults();
//                        if (results != null) {
//                            List<Person> currentMovies = new ArrayList<>();
//                            if(currentPage!=1){
//                                currentMovies= listPeopleData.getValue();
//                                if (currentMovies == null) {
//                                    currentMovies = new ArrayList<>();
//                                }
//                            }
//                            currentMovies.addAll(results);
//                            listPeopleData.postValue(currentMovies);
//                        }
//                    } else {
//                        Log.e("TAG", "Response body is null");
//                    }
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                    searchData.postValue(null);
//                    listPeopleData.postValue(null);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SearchPersonResponse> call, Throwable t) {
//                searchData.postValue(null);
//                listPeopleData.postValue(null);
//            }
//        });
//    }
    public void resetData() {
        list.clear();
        listPeopleData.postValue(new ArrayList<>());
        searchData.postValue(null);
        currentPage = 1;
    }
}
