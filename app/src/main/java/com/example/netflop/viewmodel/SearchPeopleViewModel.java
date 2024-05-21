package com.example.netflop.viewmodel;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Person;
import com.example.netflop.data.models.SearchMultiModel;
import com.example.netflop.data.responses.SearchMultiResponse;
import com.example.netflop.data.responses.SearchPersonResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;


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

    public SearchPeopleViewModel() {
        this.searchData = new MutableLiveData<>();
        this.listPeopleData=new MutableLiveData<>();
        this.list=new ArrayList<>();
    }

    public MutableLiveData<SearchPersonResponse> getSearchPeopleData() {
        return searchData;
    }

    public MutableLiveData<List<Person>> getListPeopleData() {
        return listPeopleData;
    }
    public void loadNextPage(String query,boolean includeAdult) {
        currentPage++;
        callAPI(query,includeAdult);
    }

    public void callAPI(String query, boolean includeAdult){
        APIService apiService=APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<SearchPersonResponse> call=apiService.getSearchPeople(query,includeAdult,currentPage);
        call.enqueue(new Callback<SearchPersonResponse>() {
            @Override
            public void onResponse(Call<SearchPersonResponse> call, Response<SearchPersonResponse> response) {

                if(response.code()==200){
                    SearchPersonResponse searchPersonResponse = response.body();
                    if (searchPersonResponse != null) {
                        searchData.postValue(searchPersonResponse);
                        List<Person> results = searchPersonResponse.getResults();
                        if (results != null) {
                            List<Person> currentMovies = new ArrayList<>();
                            if(currentPage!=1){
                                currentMovies= listPeopleData.getValue();
                                if (currentMovies == null) {
                                    currentMovies = new ArrayList<>();
                                }
                            }
                            currentMovies.addAll(results);
                            listPeopleData.postValue(currentMovies);
                        }
                    } else {
                        Log.e("TAG", "Response body is null");
                    }
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                    searchData.postValue(null);
                    listPeopleData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<SearchPersonResponse> call, Throwable t) {
                searchData.postValue(null);
                listPeopleData.postValue(null);
            }
        });
    }
    public void resetData() {
        list.clear();
        listPeopleData.postValue(new ArrayList<>());
        searchData.postValue(null);
        currentPage = 1;
    }
}
