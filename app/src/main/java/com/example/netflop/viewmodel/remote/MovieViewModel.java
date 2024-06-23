package com.example.netflop.viewmodel.remote;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.data.models.remote.movies.Credit;
import com.example.netflop.data.models.remote.movies.Genre;
import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.data.models.remote.movies.MovieDetail;
import com.example.netflop.data.models.remote.movies.MovieImages;
import com.example.netflop.data.models.remote.movies.MovieVideos;
import com.example.netflop.data.repository.remote.MovieRepositories;
import com.example.netflop.data.responses.movies.RecommendationMovieResponse;
import com.example.netflop.data.responses.movies.ReviewResponse;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<Movie> movieData;
    private MutableLiveData<MovieDetail> movieDetailData;
    private MutableLiveData<MovieImages> movieImageData;
    private MutableLiveData<MovieVideos> movieVideoData;
    private MutableLiveData<List<Genre>> genreListData;
    private MutableLiveData<ReviewResponse> reviewData;
    private MutableLiveData<RecommendationMovieResponse> recommendationMovieData;
    private  MutableLiveData<Credit> creditData;
    MovieRepositories movieRepositories;
    public MovieViewModel(){
        movieData=new MutableLiveData<>();
        movieDetailData=new MutableLiveData<>();
        movieImageData=new MutableLiveData<>();
        movieVideoData=new MutableLiveData<>();
        genreListData=new MutableLiveData<>();
        reviewData=new MutableLiveData<>();
        recommendationMovieData=new MutableLiveData<>();
        creditData=new MutableLiveData<>();
        movieRepositories=new MovieRepositories();
    }
    public MutableLiveData<Credit> getCreditData(){
        return  creditData;
    }
    public MutableLiveData<Movie> getMovieData(){
        return  movieData;
    }
    public MutableLiveData<MovieDetail> getMovieDetailData(){
        return  movieDetailData;
    }
    public MutableLiveData<MovieImages> getMovieImageData(){
        return  movieImageData;
    }
    public MutableLiveData<MovieVideos> getMovieVideoData(){
        return  movieVideoData;
    }
    public MutableLiveData<List<Genre>> getGenreMovieListData(){
        return  genreListData;
    }

    public MutableLiveData<ReviewResponse> getReviewData(){
        return  reviewData;
    }
    public MutableLiveData<RecommendationMovieResponse> getRecommendationMovieData(){
        return  recommendationMovieData;
    }
    public void loadMovieDetail(int movieID,LifecycleOwner lifecycleOwner){
        movieRepositories.getMovieDetailByID(movieID).observe(lifecycleOwner, new Observer<MovieDetail>() {
            @Override
            public void onChanged(MovieDetail movieDetail) {
                movieDetailData.postValue(movieDetail);
            }
        });
    }

    public void loadMovieImages(int movieID,LifecycleOwner lifecycleOwner){
        movieRepositories.getMovieImagesByID(movieID).observe(lifecycleOwner, new Observer<MovieImages>() {
            @Override
            public void onChanged(MovieImages movieImages) {
                movieImageData.postValue(movieImages);
            }
        });
    }

    public void loadMovieVideos(int movieID,LifecycleOwner lifecycleOwner){
        movieRepositories.getMovieVideosByID(movieID).observe(lifecycleOwner, new Observer<MovieVideos>() {
                    @Override
                    public void onChanged(MovieVideos movieVideos) {
                        movieVideoData.postValue(movieVideos);
                    }
                });
    }
    public void loadMovieReview(int movieID,LifecycleOwner lifecycleOwner){
        movieRepositories.getReviewByID(movieID,1).observe(lifecycleOwner, new Observer<ReviewResponse>() {
            @Override
            public void onChanged(ReviewResponse reviewResponse) {
                reviewData.postValue(reviewResponse);
            }
        });
    }
    public void loadMovieCredit(int movieID,LifecycleOwner lifecycleOwner){
        movieRepositories.getCreditByID(movieID).observe(lifecycleOwner, new Observer<Credit>() {
            @Override
            public void onChanged(Credit credit) {
                creditData.postValue(credit);
            }
        });
    }
    public void loadRecommendation(int movieID,LifecycleOwner lifecycleOwner){
        movieRepositories.getRecommendationByID(movieID).observe(lifecycleOwner, new Observer<RecommendationMovieResponse>() {
            @Override
            public void onChanged(RecommendationMovieResponse recommendationMovieResponse) {
                recommendationMovieData.postValue(recommendationMovieResponse);
            }
        });
    }



//    public void callAPIMovieDetailByID(int movieID){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<MovieDetail> call=apiService.getMovieByID(movieID);
//        call.enqueue(new Callback<MovieDetail>() {
//            @Override
//            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
//                if(response.code()==200){
//                    movieDetailData.postValue(response.body());
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieDetail> call, Throwable t) {
//                movieDetailData.postValue(null);
//            }
//        });
//    }
//    public void callAPIMovieImageByID(int movieID){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<MovieImages> call=apiService.getMovieImages(movieID);
//        call.enqueue(new Callback<MovieImages>() {
//            @Override
//            public void onResponse(Call<MovieImages> call, Response<MovieImages> response) {
//                if(response.code()==200){
//                    movieImageData.postValue(response.body());
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieImages> call, Throwable t) {
//                movieImageData.postValue(null);
//            }
//        });
//    }
//    public void callAPIMovieVideoByID(int movieID){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<MovieVideos> call=apiService.getMovieVideos(movieID);
//        call.enqueue(new Callback<MovieVideos>() {
//            @Override
//            public void onResponse(Call<MovieVideos> call, Response<MovieVideos> response) {
//
//                if(response.code()==200){
//                    movieVideoData.postValue(response.body());
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieVideos> call, Throwable t) {
//                movieVideoData.postValue(null);
//            }
//        });
//    }
//    public void callAPIGenreMovieListData(int movieID){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<List<Genre>> call=apiService.getGenreMovieList();
//        call.enqueue(new Callback<List<Genre>>() {
//            @Override
//            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
//
//                if(response.code()==200){
//                    genreListData.postValue(response.body());
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Genre>> call, Throwable t) {
//                genreListData.postValue(null);
//            }
//        });
//    }
//
//    public void callAPIGenreTVListData(int movieID){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<List<Genre>> call=apiService.getGenreTVList();
//        call.enqueue(new Callback<List<Genre>>() {
//            @Override
//            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
//
//                if(response.code()==200){
//                    genreListData.postValue(response.body());
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Genre>> call, Throwable t) {
//                genreListData.postValue(null);
//            }
//        });
//    }
//    public void callAPIReviewResponseByMovieID(int movieID){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<ReviewResponse> call=apiService.getReviewOfAMovie(movieID);
//        call.enqueue(new Callback<ReviewResponse>() {
//            @Override
//            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
//
//                if(response.code()==200){
//                    reviewData.postValue(response.body());
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ReviewResponse> call, Throwable t) {
//                reviewData.postValue(null);
//            }
//        });
//    }
//    public void callAPIRecommendationByMovieID(int movieID){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<RecommendationMovieResponse> call=apiService.getRecommendationMovie(movieID);
//        call.enqueue(new Callback<RecommendationMovieResponse>() {
//            @Override
//            public void onResponse(Call<RecommendationMovieResponse> call, Response<RecommendationMovieResponse> response) {
//
//                if(response.code()==200){
//                    recommendationMovieData.postValue(response.body());
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RecommendationMovieResponse> call, Throwable t) {
//                recommendationMovieData.postValue(null);
//            }
//        });
//    }
//    public void callAPICreditByMovieID(int movieID){
//        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
//        Call<Credit> call=apiService.getMovieCredit(movieID);
//        call.enqueue(new Callback<Credit>() {
//            @Override
//            public void onResponse(Call<Credit> call, Response<Credit> response) {
//                if(response.code()==200){
//                   creditData.postValue(response.body());
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Credit> call, Throwable t) {
//                creditData.postValue(null);
//            }
//        });
//    }

}
