package com.example.netflop.viewmodel.local;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.enums.SearchType;
import com.example.netflop.data.models.local.SearchHistory;
import com.example.netflop.data.repository.local.SearchHistoryRepository;

import java.util.List;

public class SearchHistoryViewModel extends AndroidViewModel {
    private SearchHistoryRepository repository;
    private MutableLiveData<List<SearchHistory>> searchHistories;

    public SearchHistoryViewModel(@NonNull Application application) {
        super(application);
        repository=new SearchHistoryRepository(application);
        searchHistories=new MutableLiveData<>();
        getData();
    }


    public MutableLiveData<List<SearchHistory>> getSearchHistories() {
        return searchHistories;
    }
    public void insertSearchHistory(String searchKey, SearchType searchType, int isAdult) {
        boolean id = repository.insertSearchKey(searchKey,searchType,isAdult);
        if (id) {
            // Handle success
            getData();
        }
    }
    public void deleteSearchHistory(int searchID){
        boolean id = repository.deleteSearchKey(searchID);
        if(id){
            getData();
        }
    }
    public void getData(){
        searchHistories.postValue(repository.getListSearchHistory());;
    }

}
