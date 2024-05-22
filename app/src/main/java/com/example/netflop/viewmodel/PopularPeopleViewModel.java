package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.models.Person;
import com.example.netflop.data.responses.PopularPeopleResponse;
import com.example.netflop.data.responses.TrendingPeopleResponse;
import com.example.netflop.data.services.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularPeopleViewModel extends ViewModel {
    private MutableLiveData<PopularPeopleResponse> popularPeopleData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<Person>> listPeopleData;
    List<Person> listPeople;
    public PopularPeopleViewModel(){
        popularPeopleData=new MutableLiveData<>();
        listPeopleData=new MutableLiveData<>();
        listPeople=new ArrayList<>();
    }
    public MutableLiveData<PopularPeopleResponse> getPopularPeopleData(){
        return  popularPeopleData;
    }
    public MutableLiveData<List<Person>> getListPeopleData(){
        return listPeopleData;
    }
    public void loadNextPage() {
        currentPage++;
        callAPI();
    }
    public void callAPI(){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<PopularPeopleResponse> call=apiService.getPopularPeople(currentPage);
        call.enqueue(new Callback<PopularPeopleResponse>() {
            @Override
            public void onResponse(Call<PopularPeopleResponse> call, Response<PopularPeopleResponse> response) {
                if(response.code()==200){
                    PopularPeopleResponse popularPeopleResponse = response.body();
                    if (popularPeopleResponse != null) {
                        popularPeopleData.postValue(popularPeopleResponse);
                        List<Person> results = popularPeopleResponse.getResults();
                        if (results != null) {
                            List<Person> currentMovies = listPeopleData.getValue();
                            if (currentMovies == null) {
                                currentMovies = new ArrayList<>();
                            }
                            currentMovies.addAll(results);
                            listPeopleData.postValue(currentMovies);
                        }
                    } else {
                        Log.e("TAG", "Response body is null");
                    }
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                }
            }

            @Override
            public void onFailure(Call<PopularPeopleResponse> call, Throwable t) {
                popularPeopleData.postValue(null);
                listPeopleData.postValue(null);
            }
        });
    }
}
