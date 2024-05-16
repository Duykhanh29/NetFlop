package com.example.netflop.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.http.Tag;

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
}
