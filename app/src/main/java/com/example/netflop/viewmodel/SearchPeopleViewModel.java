package com.example.netflop.viewmodel;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.responses.SearchPersonResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPeopleViewModel extends ViewModel {
    private MutableLiveData<SearchPersonResponse> searchData;

    public SearchPeopleViewModel() {
        this.searchData = new MutableLiveData<>();
    }
    public MutableLiveData<SearchPersonResponse> getSearchPeopleData() {
        return searchData;
    }
    public void makeAPICall(String query,boolean includeAdult,int page){
        APIService apiService=APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<SearchPersonResponse> call=apiService.getSearchPeople(query,includeAdult,page);
        call.enqueue(new Callback<SearchPersonResponse>() {
            @Override
            public void onResponse(Call<SearchPersonResponse> call, Response<SearchPersonResponse> response) {

                if(response.code()==200){
                    searchData.postValue(response.body());
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                }
            }

            @Override
            public void onFailure(Call<SearchPersonResponse> call, Throwable t) {
                searchData.postValue(null);
            }
        });
    }

}
