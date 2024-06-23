package com.example.netflop.viewmodel.remote;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.data.models.remote.movies.Review;
import com.example.netflop.data.repository.remote.MovieRepositories;
import com.example.netflop.data.responses.movies.ReviewResponse;

import java.util.ArrayList;
import java.util.List;

public class ReviewViewModel extends ViewModel {
    private MutableLiveData<ReviewResponse> reviewData;
    int currentPage=1;
    private boolean isLoading = false;
    private MutableLiveData<List<Review>> listReview;
    MovieRepositories movieRepositories;
    List<Review> list;
    public ReviewViewModel(){
        reviewData=new MutableLiveData<>();
        listReview=new MutableLiveData<>();
        list=new ArrayList<>();
        movieRepositories=new MovieRepositories();
    }

    public MutableLiveData<ReviewResponse> getReviewData() {
        return reviewData;
    }

    public MutableLiveData<List<Review>> getListReview() {
        return listReview;
    }
    public void loadNextPage(int movieID,LifecycleOwner lifecycleOwner) {
        currentPage++;
//        callAPI();
        fetchReviewData(movieID,lifecycleOwner);
    }
    public void fetchReviewData(int movieID,LifecycleOwner lifecycleOwner) {
        LiveData<ReviewResponse> liveData = movieRepositories.getReviewByID(movieID,currentPage);
        liveData.observe(lifecycleOwner, new Observer<ReviewResponse>() {
            @Override
            public void onChanged(ReviewResponse reviewResponse) {
                if (reviewResponse!= null && reviewResponse.getResults()!= null) {
//                    reviewData.postValue(reviewResponse);
                    List<Review> currentReviews = listReview.getValue();
                    if (currentReviews == null) {
                        currentReviews = new ArrayList<>();
                    }
                    currentReviews.addAll(reviewResponse.getResults());
                    listReview.postValue(currentReviews);
                } else {
                    Log.e("TAG", "Response body is null");
                }
            }
        });
    }
}
