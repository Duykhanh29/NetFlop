package com.example.netflop.viewmodel.remote;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.data.repository.remote.SearchRepository;
import com.example.netflop.data.responses.search.SearchMovieResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchMovieViewModel extends ViewModel {
    private MutableLiveData<SearchMovieResponse> searchMovieData;
    private MutableLiveData<List<Movie>> listMovieData;
    List<Movie> list;
    int currentPage=1;
    SearchRepository searchRepository;

    public SearchMovieViewModel(){
        this.searchMovieData = new MutableLiveData<>();
        this.listMovieData=new MutableLiveData<>();
        this.list=new ArrayList<>();
        searchRepository=new SearchRepository();
    }

    public MutableLiveData<List<Movie>> getListMovieData(){
        return listMovieData;
    }

    public MutableLiveData<SearchMovieResponse> getSearchMovieData() {
        return searchMovieData;
    }
    public void loadNextPage(String query,boolean includeAdult,LifecycleOwner lifecycleOwner) {
        currentPage++;
//        callAPI(query,includeAdult);
        searchMovie(query,includeAdult,lifecycleOwner);
    }
    public void resetCurrentPage(){
        currentPage=1;
    }
    public void searchMovie(String query, boolean includeAdult, LifecycleOwner lifecycleOwner){

        searchRepository.searchMovies(query,includeAdult,currentPage).observe(lifecycleOwner, new Observer<SearchMovieResponse>() {
            @Override
            public void onChanged(SearchMovieResponse searchMovieResponse) {
                if (searchMovieResponse!= null && searchMovieResponse.getResults()!= null) {
                    searchMovieData.postValue(searchMovieResponse);
                    List<Movie> results = searchMovieResponse.getResults();
                    if(results!=null){
                        List<Movie> currentList = new ArrayList<>();
                        if(currentPage!=1){
                            currentList= listMovieData.getValue();
                            if (currentList == null) {
                                currentList = new ArrayList<>();
                            }
                        }
                        currentList.addAll(results);
                        listMovieData.postValue(currentList);
                    }
                } else {
                    searchMovieData.postValue(null);
                    listMovieData.postValue(null);
                    Log.e("TAG", "Response body is null");
                }
            }
        });

    }
//    public void callAPI(String query,boolean includeAdult){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<SearchMovieResponse> call=apiService.getSearchMovies(query,includeAdult,currentPage);
//        call.enqueue(new Callback<SearchMovieResponse>() {
//            @Override
//            public void onResponse(Call<SearchMovieResponse> call, Response<SearchMovieResponse> response) {
//
//                if(response.code()==200){
//
//
//                    SearchMovieResponse searchMovieResponse = response.body();
//                    if (searchMovieResponse != null) {
//                        searchMovieData.postValue(searchMovieResponse);
//                        List<Movie> results = searchMovieResponse.getResults();
//                        if (results != null) {
//                            List<Movie> currentMovies = new ArrayList<>();
//                            if(currentPage!=1){
//                                currentMovies= listMovieData.getValue();
//                                if (currentMovies == null) {
//                                    currentMovies = new ArrayList<>();
//                                }
//                            }
//                            currentMovies.addAll(results);
//                            listMovieData.postValue(currentMovies);
//                        }
//                    } else {
//                        Log.e("TAG", "Response body is null");
//                    }
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                    searchMovieData.postValue(null);
//                    listMovieData.postValue(null);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SearchMovieResponse> call, Throwable t) {
//                searchMovieData.postValue(null);
//                listMovieData.postValue(null);
//            }
//        });
//    }
    public void resetData() {
        list.clear();
        listMovieData.postValue(new ArrayList<>());
        searchMovieData.postValue(null);
        currentPage = 1;
    }
}
