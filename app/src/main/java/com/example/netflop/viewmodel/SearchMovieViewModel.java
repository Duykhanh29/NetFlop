package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.models.Person;
import com.example.netflop.data.responses.SearchMovieResponse;
import com.example.netflop.data.responses.SearchPersonResponse;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMovieViewModel extends ViewModel {
    private MutableLiveData<SearchMovieResponse> searchMovieData;
    private MutableLiveData<List<Movie>> listMovieData;
    List<Movie> list;
    int currentPage=1;

    public SearchMovieViewModel(){
        this.searchMovieData = new MutableLiveData<>();
        this.listMovieData=new MutableLiveData<>();
        this.list=new ArrayList<>();
    }

    public MutableLiveData<List<Movie>> getListMovieData(){
        return listMovieData;
    }

    public MutableLiveData<SearchMovieResponse> getSearchMovieData() {
        return searchMovieData;
    }
    public void loadNextPage(String query,boolean includeAdult) {
        currentPage++;
        callAPI(query,includeAdult);
    }
    public void callAPI(String query,boolean includeAdult){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<SearchMovieResponse> call=apiService.getSearchMovies(query,includeAdult,currentPage);
        call.enqueue(new Callback<SearchMovieResponse>() {
            @Override
            public void onResponse(Call<SearchMovieResponse> call, Response<SearchMovieResponse> response) {

                if(response.code()==200){


                    SearchMovieResponse searchMovieResponse = response.body();
                    if (searchMovieResponse != null) {
                        searchMovieData.postValue(searchMovieResponse);
                        List<Movie> results = searchMovieResponse.getResults();
                        if (results != null) {
                            List<Movie> currentMovies = new ArrayList<>();
                            if(currentPage!=1){
                                currentMovies= listMovieData.getValue();
                                if (currentMovies == null) {
                                    currentMovies = new ArrayList<>();
                                }
                            }
                            currentMovies.addAll(results);
                            listMovieData.postValue(currentMovies);
                        }
                    } else {
                        Log.e("TAG", "Response body is null");
                    }
                }else{
                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
                    searchMovieData.postValue(null);
                    listMovieData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<SearchMovieResponse> call, Throwable t) {
                searchMovieData.postValue(null);
                listMovieData.postValue(null);
            }
        });
    }
    public void resetData() {
        list.clear();
        listMovieData.postValue(new ArrayList<>());
        searchMovieData.postValue(null);
        currentPage = 1;
    }
}
