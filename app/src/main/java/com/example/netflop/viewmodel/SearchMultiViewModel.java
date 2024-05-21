package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.models.SearchMultiModel;
import com.example.netflop.data.responses.NowPlayingResponse;
import com.example.netflop.data.responses.SearchMovieResponse;
import com.example.netflop.data.responses.SearchMultiResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;

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
    public SearchMultiViewModel(){
        this.searchMultiData = new MutableLiveData<>();
        list=new ArrayList<>();
        listSearchMulti=new MutableLiveData<>();
    }

    public MutableLiveData<List<SearchMultiModel>> getListSearchMulti() {
        return listSearchMulti;
    }

    public MutableLiveData<SearchMultiResponse> getSearchMultiData() {
        return searchMultiData;
    }
    public void loadNextPage(String query,boolean includeAdult) {
        currentPage++;
        callAPI(query,includeAdult);
    }
    public void callAPI(String query,boolean includeAdult){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<SearchMultiResponse> call=apiService.getSearchMultiSource(query,includeAdult,currentPage);
        call.enqueue(new Callback<SearchMultiResponse>() {
            @Override
            public void onResponse(Call<SearchMultiResponse> call, Response<SearchMultiResponse> response) {

                if(response.code()==200){
                    SearchMultiResponse searchMultiResponse = response.body();
                    if (searchMultiResponse != null) {
                        searchMultiData.postValue(searchMultiResponse);
                        List<SearchMultiModel> results = searchMultiResponse.getResults();
                        if (results != null) {
                            List<SearchMultiModel> currentMovies =new ArrayList<>();
                            if(currentPage!=1){
                                currentMovies= listSearchMulti.getValue();
                                if (currentMovies == null) {
                                    currentMovies = new ArrayList<>();
                                }
                            }
                            currentMovies.addAll(results);
                            listSearchMulti.postValue(currentMovies);
                        }
                    } else {
                        Log.e("TAG", "Response body is null");
                    }
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                }
            }

            @Override
            public void onFailure(Call<SearchMultiResponse> call, Throwable t) {
                searchMultiData.postValue(null);
                listSearchMulti.postValue(null);
            }
        });
    }
    public void resetData() {
        list.clear();
        listSearchMulti.postValue(new ArrayList<>());
        searchMultiData.postValue(null);
        currentPage = 1;
    }
}
