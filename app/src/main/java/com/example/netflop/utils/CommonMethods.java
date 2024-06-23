package com.example.netflop.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.movies.ProductionCompany;
import com.example.netflop.data.models.remote.movies.ProductionCountry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CommonMethods {
    public static String getDaysDifference(String startDate, String endDate) {

        String daysDifference = "";

        if (!startDate.equalsIgnoreCase("null")
                && !endDate.equalsIgnoreCase("null")
                && !startDate.equals("") && !endDate.equals("")) {

            SimpleDateFormat obj = new SimpleDateFormat("dd/MM/yyyy");

            try {
                Date date1 = obj.parse(startDate);
                Date date2 = obj.parse(endDate);
                long timeDifference = date2.getTime() - date1.getTime();
                long calDaysDifference = (timeDifference / (1000 * 60 * 60 * 24)) % 365;
                daysDifference = String.valueOf(calDaysDifference);

            } catch (ParseException e) {
                Log.e("Tag", e.getMessage());
            }

        }

        return daysDifference;
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static String getYearByReleaseDate(String releaseDate){
        List<String> elements= Arrays.asList(releaseDate.split("-"));
        return elements.get(0);
    }
    public static List<String> getListStringByListCompanyModel(List<ProductionCompany> list){
        List<String> result=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(list.get(i).getName());
        }
        return  result;
    }
    public static List<String> getListStringByListCountryModel(List<ProductionCountry> list){
        List<String> result=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(list.get(i).getName());
        }
        return  result;
    }

    // favourite
    public static boolean isMovieExisted(List<FavouriteMedia> listFavouriteMovie,int movieID){
        for (int i = 0; i < listFavouriteMovie.size(); i++) {
            if(listFavouriteMovie.get(i).getMediaID()==movieID){
                return true;
            }
        }
        return false;
    }
    public static boolean isPersonExisted(List<FavouriteMedia> listFavouritePeople,int personID){
        for (int i = 0; i < listFavouritePeople.size(); i++) {
            if(listFavouritePeople.get(i).getMediaID()==personID){
                return true;
            }
        }
        return false;
    }
    public static boolean isTVSeriesExisted(List<FavouriteMedia> listFavouriteTVSeries,int tvSeriesID){
        for (int i = 0; i < listFavouriteTVSeries.size(); i++) {
            if(listFavouriteTVSeries.get(i).getMediaID()==tvSeriesID){
                return true;
            }
        }
        return false;
    }
    public static boolean isTVSeasonExisted(List<FavouriteMedia> listFavouriteTVSeason,int tvSeriesID,int seasonNumber){
        for (int i = 0; i < listFavouriteTVSeason.size(); i++) {
            if(listFavouriteTVSeason.get(i).getMediaID()==tvSeriesID&&listFavouriteTVSeason.get(i).getSeasonNumber()==seasonNumber){
                return true;
            }
        }
        return false;
    }
    public static boolean isTVEpisodeExisted(List<FavouriteMedia> listFavouriteTVEpisode,int tvSeriesID,int seasonNumber,int episodeNumber){
        for (int i = 0; i < listFavouriteTVEpisode.size(); i++) {
            if(listFavouriteTVEpisode.get(i).getMediaID()==tvSeriesID&&listFavouriteTVEpisode.get(i).getSeasonNumber()==seasonNumber&&listFavouriteTVEpisode.get(i).getEpisodeNumber()==episodeNumber){
                return true;
            }
        }
        return false;
    }
}
