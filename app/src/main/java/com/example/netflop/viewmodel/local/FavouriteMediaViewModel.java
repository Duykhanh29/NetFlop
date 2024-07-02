package com.example.netflop.viewmodel.local;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.enums.TypeOfMedia;
import com.example.netflop.constants.enums.WatchStatus;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.local.SearchHistory;
import com.example.netflop.data.repository.local.FavouriteMediaRepository;
import com.example.netflop.data.repository.local.SearchHistoryRepository;

import java.util.List;

public class FavouriteMediaViewModel extends AndroidViewModel {
    private FavouriteMediaRepository repository;
    private MutableLiveData<List<FavouriteMedia>> favouriteTVSeries;
    private MutableLiveData<List<FavouriteMedia>> favouriteTVSeasons;
    private MutableLiveData<List<FavouriteMedia>> favouriteTVEpisodes;
    private MutableLiveData<List<FavouriteMedia>> favouriteMovies;
    private MutableLiveData<List<FavouriteMedia>> favouritePeople;
    private  MutableLiveData<List<FavouriteMedia>> favouriteData;
    public FavouriteMediaViewModel(@NonNull Application application){
        super(application);
        repository=new FavouriteMediaRepository(application);
        favouriteTVSeries=new MutableLiveData<>();
        favouriteTVSeasons=new MutableLiveData<>();
        favouriteTVEpisodes=new MutableLiveData<>();
        favouriteMovies=new MutableLiveData<>();
        favouritePeople=new MutableLiveData<>();
        favouriteData=new MutableLiveData<>();
//        fetchData();
    }

    public MutableLiveData<List<FavouriteMedia>> getFavouriteData() {
        return favouriteData;
    }

    public FavouriteMediaRepository getRepository() {
        return repository;
    }

    public MutableLiveData<List<FavouriteMedia>> getFavouriteTVSeries() {
        return favouriteTVSeries;
    }

    public MutableLiveData<List<FavouriteMedia>> getFavouriteTVSeasons() {
        return favouriteTVSeasons;
    }

    public MutableLiveData<List<FavouriteMedia>> getFavouriteTVEpisodes() {
        return favouriteTVEpisodes;
    }

    public MutableLiveData<List<FavouriteMedia>> getFavouriteMovies() {
        return favouriteMovies;
    }

    public MutableLiveData<List<FavouriteMedia>> getFavouritePeople() {
        return favouritePeople;
    }
    public void deleteFavouriteMedia(int favouriteMediaID){
        boolean id = repository.deleteFavouriteMedia(favouriteMediaID);
        if(id){
            fetchData();
        }
    }
    public void insertFavouriteMedia(int mediaID, String title, TypeOfMedia typeOfMedia, Integer seasonNumber, Integer episodeNumber, String image, WatchStatus watchStatus){
        boolean id = repository.insertFavouriteMedia(mediaID,title,typeOfMedia,seasonNumber,episodeNumber,image,watchStatus);
        if(id){
            fetchData();
        }
    }
    public void updateWatchStatus(int id,WatchStatus watchStatus){
        boolean result = repository.updateWatchStatus(id,watchStatus);
        if(result){
            fetchData();
        }
    }
    public void fetchData(){
        fetchTVSeriesData();
        fetchTVSeasonsData();
        fetchTVEpisodesData();
        fetchMovieData();
        fetchPeopleData();
        fetchFavouriteData();
    }
    public void fetchFavouriteData(){
        favouriteData.postValue(repository.getListFavouriteMedia());;
    }
    public void fetchTVSeriesData(){
        favouriteTVSeries.postValue(repository.getListFavouriteTVSeries());;
    }
    public void fetchTVSeasonsData(){
        favouriteTVSeasons.postValue(repository.getListFavouriteTVSeason());;
    }
    public void fetchTVEpisodesData(){
        favouriteTVEpisodes.postValue(repository.getListFavouriteTVEpisode());;
    }
    public void fetchMovieData(){
        favouriteMovies.postValue(repository.getListFavouriteMovie());;
    }
    public void fetchPeopleData(){
        favouritePeople.postValue(repository.getListFavouritePeople());;
    }
}
