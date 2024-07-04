package com.example.netflop.ui.movie_detail;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.constants.enums.TypeOfMedia;
import com.example.netflop.constants.enums.WatchStatus;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.people.Cast;
import com.example.netflop.data.models.remote.movies.Credit;
import com.example.netflop.data.models.remote.movies.Genre;
import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.data.models.remote.movies.MovieDetail;
import com.example.netflop.data.models.remote.movies.MovieImages;
import com.example.netflop.data.models.remote.movies.MovieVideos;
import com.example.netflop.data.models.remote.people.Person;
import com.example.netflop.data.models.remote.movies.ProductionCompany;
import com.example.netflop.data.models.remote.movies.ProductionCountry;
import com.example.netflop.data.models.remote.movies.Review;
import com.example.netflop.data.models.remote.movies.SpokenLanguage;
import com.example.netflop.data.models.remote.movies.Video;
import com.example.netflop.data.responses.movies.RecommendationMovieResponse;
import com.example.netflop.data.responses.movies.ReviewResponse;
import com.example.netflop.databinding.ActivityMovieDetailBinding;
import com.example.netflop.helpers.CustomTextView;
import com.example.netflop.helpers.NoInternetToastHelpers;
import com.example.netflop.ui.adapters.remote.ListCastAdapter;
import com.example.netflop.ui.adapters.remote.ListMovieAdapter;
import com.example.netflop.ui.adapters.remote.ListReviewAdapter;
import com.example.netflop.ui.adapters.remote.ListTrailerAdapter;
import com.example.netflop.ui.base.BaseActivity;
import com.example.netflop.ui.person_detail.PersonDetailActivity;
import com.example.netflop.utils.listeners.ItemTouchHelperAdapter;
import com.example.netflop.utils.listeners.OnTrailerClickListener;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.utils.SeeMoreOnClickListener;
import com.example.netflop.viewmodel.connectivity.ConnectivityViewModel;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;
import com.example.netflop.viewmodel.remote.MovieViewModel;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends BaseActivity implements ItemTouchHelperAdapter, OnTrailerClickListener {
    ActivityMovieDetailBinding binding;
    MovieDetail movieDetailData;
    MovieImages movieImagesData;
    MovieVideos movieVideosData;
    ReviewResponse reviewResponseData;
    Credit creditData;
    RecommendationMovieResponse recommendationMovieResponseData;

    int movieID;

    // view models
    MovieViewModel movieViewModel;
    FavouriteMediaViewModel favouriteMediaViewModel;
    ConnectivityViewModel connectivityViewModel;

    private List<Cast> cast;
    private List<Cast> crew;
    private List<Movie> recommendationMovie;
    private List<Review> listReview;
    private List<Video> trailers;

    // adapters
    ListCastAdapter castAdapter,crewAdapter;
    ListMovieAdapter recommendationAdapter;
    ListTrailerAdapter listTrailerAdapter;
    ListReviewAdapter listReviewAdapter;




    // UI variables
    ImageSlider imageSlider;
    List<String> listImage;
    List<SlideModel> slideModels;
    List<FavouriteMedia> listFavouriteMovie;
    boolean isFavourite=false;

    ImageButton backButton;
    ImageView favouriteImageButton;
    TextView titleMovie,overviewText,voteRatedMovie,releaseDateText,runtimeText,popularityTextView,statusTextView,tagLineTextView,budgetTextView,revenueTextView,homePageTextView;
    RecyclerView castRecyclerView,crewRecyclerView,recommendationRecyclerView,trailerRecyclerView,reviewRecyclerView;
    FlexboxLayout genresFlexBox,companyFlexBox,countryFlexBox,languageFlexBox;

    TextView seeMoreTrailers,seeMoreReviewView;
    TextView isHavingRecommendationTV,isTrailerTextView,isHavingReviewTextView;


    // selected variables

    Cast selectedCast;
    Video selectedTrailer;
    Movie selectedMovie;


    String titleOfMovie,posterImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding();
        getData();
        initialize();
        callAPIs();
        loadFavouriteData();
        observerDataChanged();
        observeFavouriteDataChange();
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isEnabled()){
                    finish();
                }
            }
        });
        onBackHandle();
    }
    private void binding(){
        imageSlider=binding.imageSliderMovieDetail;
        backButton=binding.backImageButton;
        titleMovie=binding.titleOfMovieDetail;
        overviewText=binding.overviewMovieTV;
        voteRatedMovie=binding.voteRatedMovieDetailTV;
        releaseDateText=binding.yearOfMovieTV;
        runtimeText=binding.runtimeOfMovieTV;
        popularityTextView=binding.popularityDetailMovie;
        statusTextView=binding.statusDetailMovie;
        tagLineTextView=binding.tagLineDetail;
        budgetTextView=binding.budgetDetail;
        homePageTextView=binding.homePageDetail;
        revenueTextView=binding.revenueDetail;
        favouriteImageButton=binding.addFavouriteImage;

        castRecyclerView=binding.castRecyclerView;
        crewRecyclerView=binding.crewsRecyclerView;
        recommendationRecyclerView=binding.recommendationMovieRecyclerView;
        trailerRecyclerView=binding.trailerDetailRecyclerView;
        reviewRecyclerView=binding.reviewDetailRecyclerView;

        genresFlexBox=binding.flexboxGenre;
        companyFlexBox=binding.flexboxCompany;
        countryFlexBox=binding.flexboxCountry;
        languageFlexBox=binding.flexboxLanguage;

        isHavingRecommendationTV=binding.isRecommendationDetailHaveID;
        isTrailerTextView=binding.isTrailerView;
        isHavingReviewTextView=binding.isReviewView;
        // see more
        seeMoreTrailers=binding.seeMoreTrailerDetail;
        seeMoreReviewView=binding.seeMoreReviewDetail;

    }
    private void getData(){
        Intent intent=getIntent();
        movieID=intent.getIntExtra(StringConstants.movieDetailPageDataKey,-1);
    }
    private void initialize(){
        movieViewModel= new ViewModelProvider(this).get(MovieViewModel.class);
        favouriteMediaViewModel=new ViewModelProvider(this).get(FavouriteMediaViewModel.class);
        connectivityViewModel=new ViewModelProvider(this).get(ConnectivityViewModel.class);
        listImage=new ArrayList<>();
        slideModels=new ArrayList<>();
        listFavouriteMovie=new ArrayList<>();
        cast=new ArrayList<>();
        crew=new ArrayList<>();
        recommendationMovie=new ArrayList<>();
        listReview=new ArrayList<>();
        trailers=new ArrayList<>();
        setSlideImageList();
        handleFavouriteCLick();
        initializeAdapters();
        initializeRecyclerView();
    }
    private void initializeAdapters(){
        castAdapter=new ListCastAdapter(this,cast,this);
        crewAdapter=new ListCastAdapter(this,crew,this);
        recommendationAdapter=new ListMovieAdapter(recommendationMovie,this,R.layout.single_movie_card,this,favouriteMediaViewModel);
        listTrailerAdapter=new ListTrailerAdapter(trailers,this,this);
        listReviewAdapter=new ListReviewAdapter(listReview,this);
    }
    private void initializeRecyclerView(){
        RecyclerViewUtils.setupHorizontalRecyclerView(this,castRecyclerView,castAdapter,LinearLayoutManager.HORIZONTAL,false);
        RecyclerViewUtils.setupHorizontalRecyclerView(this,crewRecyclerView,crewAdapter,LinearLayoutManager.HORIZONTAL,false);
        RecyclerViewUtils.setupHorizontalRecyclerView(this,recommendationRecyclerView,recommendationAdapter,LinearLayoutManager.HORIZONTAL,false);
        RecyclerViewUtils.setupVerticalRecyclerView(this,trailerRecyclerView,listTrailerAdapter,LinearLayoutManager.VERTICAL,false);
        RecyclerViewUtils.setupVerticalRecyclerView(this,reviewRecyclerView,listReviewAdapter,LinearLayoutManager.VERTICAL,false);

//        castRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        crewRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        recommendationRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
////        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        // decoration
//        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(10,10);
//        SpacingItemDecorator itemDecorator1 = new SpacingItemDecorator(10,5);
//        //
//        castRecyclerView.addItemDecoration(itemDecorator);
//        crewRecyclerView.addItemDecoration(itemDecorator);
//        recommendationRecyclerView.addItemDecoration(itemDecorator);
//        trailerRecyclerView.addItemDecoration(itemDecorator1);
//        setAdapterForRecyclerView();
    }
    private void setAdapterForRecyclerView(){
        castRecyclerView.setAdapter(castAdapter);
        crewRecyclerView.setAdapter(crewAdapter);
        recommendationRecyclerView.setAdapter(recommendationAdapter);
        trailerRecyclerView.setAdapter(listTrailerAdapter);
    }
    private void callAPIs(){
        movieViewModel.loadMovieDetail(movieID,this);
        movieViewModel.loadMovieImages(movieID,this);
        movieViewModel.loadMovieVideos(movieID,this);
        movieViewModel.loadMovieCredit(movieID,this);
        movieViewModel.loadMovieReview(movieID,this);
        movieViewModel.loadRecommendation(movieID,this);
    }
    private void addListImage(String newData){
        listImage.add(URLConstants.imageURL+newData);
    }
    private void addToSlideModels(){
        for (int i = 0; i <listImage.size() ; i++) {
            slideModels.add(new SlideModel(listImage.get(i), ScaleTypes.FIT));
        }
    }
    private void setSlideImageList(){
        addToSlideModels();
        imageSlider.setImageList(slideModels);
    }
    private void updateRecommendationMovie(){
        if(recommendationMovieResponseData.getResults()!=null&&!recommendationMovieResponseData.getResults().isEmpty()){
            recommendationMovie=recommendationMovieResponseData.getResults();
        }
        recommendationAdapter=new ListMovieAdapter(recommendationMovie,this,R.layout.single_movie_card,this,favouriteMediaViewModel);
        recommendationRecyclerView.setAdapter(recommendationAdapter);
    }
    private void updateCredit(){
        if(creditData.getCast()!=null&&!creditData.getCast().isEmpty()){
            cast=creditData.getCast();
        }
        if(creditData.getCrew()!=null&&!creditData.getCrew().isEmpty()){
            crew=creditData.getCrew();
        }
        updateListCast();
        updateListCrew();
    }

    private void updateListCast(){
        castAdapter=new ListCastAdapter(this,cast,this);
        castRecyclerView.setAdapter(castAdapter);
    }
    private void updateListCrew(){
        crewAdapter=new ListCastAdapter(this,crew,this);
        crewRecyclerView.setAdapter(crewAdapter);
    }

    private  void updateMovieVideo(){
        if(movieVideosData.getResults()!=null&&!movieVideosData.getResults().isEmpty()){
            if(movieVideosData.getResults().size()>5){
                for (int i = 0; i < 5; i++) {
                    trailers.add(movieVideosData.getResults().get(i));
                }
                listTrailerAdapter=new ListTrailerAdapter(trailers,this,this);
                trailerRecyclerView.setAdapter(listTrailerAdapter);
                seeMoreTrailers.setVisibility(View.VISIBLE);
                SeeMoreOnClickListener.getSeeMoreOnClick(seeMoreTrailers,() -> {
                    if(connectivityViewModel.getState()){
                        List<Video> listVideo=movieVideosData.getResults();
                        // nav to all trailers of movie
                        navToAllTrailerActivity(listVideo);
                    }else{
                        NoInternetToastHelpers.show(this);
                    }

                });

            }else{
                trailers=movieVideosData.getResults();
                listTrailerAdapter=new ListTrailerAdapter(trailers,this,this);
                trailerRecyclerView.setAdapter(listTrailerAdapter);
            }
        }else{
            isTrailerTextView.setVisibility(View.VISIBLE);
        }
    }
    private void updateReview(){
        if(reviewResponseData.getResults()!=null&&!reviewResponseData.getResults().isEmpty()){
            if(reviewResponseData.getResults().size()>5){
                for (int i = 0; i < 5; i++) {
                    listReview.add(reviewResponseData.getResults().get(i));
                }
                listReviewAdapter=new ListReviewAdapter(listReview,MovieDetailActivity.this);
                reviewRecyclerView.setAdapter(listReviewAdapter);
                seeMoreReviewView.setVisibility(View.VISIBLE);
                SeeMoreOnClickListener.getSeeMoreOnClick(seeMoreReviewView,() -> {
                    if(connectivityViewModel.getState()){
                        Intent intent=new Intent(this,AllReviewActivity.class);
                        intent.putExtra(StringConstants.movieIDDataKey,movieID);
                        startActivity(intent);
                    }else{
                        NoInternetToastHelpers.show(this);
                    }

                });
            }else{
                listReview=reviewResponseData.getResults();
                listReviewAdapter=new ListReviewAdapter(listReview,MovieDetailActivity.this);
                reviewRecyclerView.setAdapter(listReviewAdapter);
            }
        }else{
            isHavingReviewTextView.setVisibility(View.VISIBLE);
        }
    }
    private void updateMovieDetail(){
        if(movieDetailData.getPosterPath()!=null){
            addListImage(movieDetailData.getPosterPath());
            posterImage=movieDetailData.getPosterPath();
        }
        if(movieDetailData.getBackdropPath()!=null){
            addListImage(movieDetailData.getBackdropPath());
        }
        setSlideImageList();
        titleMovie.setText(movieDetailData.getTitle());
        titleOfMovie=movieDetailData.getTitle();
        overviewText.setText(movieDetailData.getOverview());
        voteRatedMovie.setText(movieDetailData.getVoteAverage()+" ("+movieDetailData.getVoteCount()+")");
        releaseDateText.setText(movieDetailData.getReleaseDate());
        runtimeText.setText(movieDetailData.getRuntime()+" minutes");
        statusTextView.setText(movieDetailData.getStatus());
        popularityTextView.setText(movieDetailData.getPopularity()+"");
        tagLineTextView.setText(movieDetailData.getTagline());
        budgetTextView.setText(movieDetailData.getBudget()+"");
        if(movieDetailData.getHomepage()==null){
            homePageTextView.setAutoLinkMask(0); // disable autoLink
        }else{
//            homePageTextView.setAutoLinkMask(Linkify.WEB_URLS);   // enable autoLink
        }
        homePageTextView.setText(movieDetailData.getHomepage()!=null ?movieDetailData.getHomepage() :"null");
        revenueTextView.setText(movieDetailData.getRevenue()+"");

        for (Genre genre:movieDetailData.getGenres()) {
            Context context = new ContextThemeWrapper(this, R.style.NormalDetailTextStyle);
            TextView textView = new TextView(context);
            textView.setText(genre.getName());
            textView= CustomTextView.chipTextView(textView);
            genresFlexBox.addView(textView);
        }

        for (ProductionCompany company:movieDetailData.getProductionCompanies()) {
            Context context = new ContextThemeWrapper(this, R.style.NormalDetailTextStyle);
            TextView textView = new TextView(context);
            textView.setText(company.getName());
            textView= CustomTextView.chipTextView(textView);
            companyFlexBox.addView(textView);
        }

        for (ProductionCountry country:movieDetailData.getProductionCountries()) {
            Context context = new ContextThemeWrapper(this, R.style.NormalDetailTextStyle);
            TextView textView = new TextView(context);
            textView.setText(country.getName());
            textView= CustomTextView.chipTextView(textView);
            countryFlexBox.addView(textView);
        }

        for (SpokenLanguage language:movieDetailData.getSpokenLanguages()) {
            Context context = new ContextThemeWrapper(this, R.style.NormalDetailTextStyle);
            TextView textView = new TextView(context);
            textView.setText(language.getEnglishName());
            textView= CustomTextView.chipTextView(textView);
            languageFlexBox.addView(textView);
        }
    }
    private void observerDataChanged(){
        movieViewModel.getMovieDetailData().observe(this, new Observer<MovieDetail>() {
            @Override
            public void onChanged(MovieDetail movieDetail) {
                if(movieDetail!=null){
                    movieDetailData=movieDetail;
                    updateMovieDetail();
                }
            }
        });
        movieViewModel.getMovieImageData().observe(this, new Observer<MovieImages>() {
            @Override
            public void onChanged(MovieImages movieImages) {
                if(movieImages!=null){
                    movieImagesData=movieImages;
                    if(movieImagesData.getPosters()!=null&&!movieImagesData.getPosters().isEmpty()){
                        for (int i = 0; i <movieImagesData.getPosters().size() ; i++) {
                            if(movieImagesData.getPosters().get(i).getFilePath()!=null){
                                addListImage(movieImagesData.getPosters().get(i).getFilePath());
                            }
                        }
                        setSlideImageList();
                    }
                }
            }
        });
        movieViewModel.getMovieVideoData().observe(this, new Observer<MovieVideos>() {
            @Override
            public void onChanged(MovieVideos movieVideos) {
                if(movieVideos!=null){
                    movieVideosData=movieVideos;
                    updateMovieVideo();
                }else{
                    isTrailerTextView.setVisibility(View.VISIBLE);
                }
            }
        });
        movieViewModel.getRecommendationMovieData().observe(this, new Observer<RecommendationMovieResponse>() {
            @Override
            public void onChanged(RecommendationMovieResponse recommendationMovieResponse) {
                if(recommendationMovieResponse!=null){
                    recommendationMovieResponseData=recommendationMovieResponse;
                    updateRecommendationMovie();
                }else{
                    isHavingRecommendationTV.setVisibility(View.VISIBLE);
                    isHavingRecommendationTV.setText("No recommendation movie");
                    isHavingRecommendationTV=CustomTextView.chipTextView(isHavingRecommendationTV);
                }
            }
        });
        movieViewModel.getReviewData().observe(this, new Observer<ReviewResponse>() {
            @Override
            public void onChanged(ReviewResponse reviewResponse) {
                if(reviewResponse!=null){
                    reviewResponseData=reviewResponse;
                    updateReview();
                }

            }
        });
        movieViewModel.getCreditData().observe(this, new Observer<Credit>() {
            @Override
            public void onChanged(Credit credit) {
                if(credit!=null){
                    creditData=credit;
                    updateCredit();
                }
            }
        });
    }
    private  void observeFavouriteDataChange(){
        favouriteMediaViewModel.getFavouriteMovies().observe(this, new Observer<List<FavouriteMedia>>() {
            @Override
            public void onChanged(List<FavouriteMedia> favouriteMedia) {
                listFavouriteMovie=favouriteMedia;

                for (int i = 0; i < favouriteMedia.size(); i++) {
                    if(favouriteMedia.get(i).getMediaID()==movieID){
                        isFavourite=true;
                        favouriteImageButton.setImageResource(R.drawable.favoourite_no_border);
                    }
                }
                if(!isFavourite){
                    favouriteImageButton.setImageResource(R.drawable.favourite);
                }
            }
        });
    }
    private  void loadFavouriteData(){
        favouriteMediaViewModel.fetchMovieData();
    }
    private void onBackHandle(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void handleFavouriteCLick(){
        favouriteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavourite){
                    int index=-1;
                    for (int i = 0; i <listFavouriteMovie.size() ; i++) {
                        if(listFavouriteMovie.get(i).getMediaID()==movieID){
                            index=i;
                            break;
                        }
                    }
                    if(index!=-1){
                        favouriteMediaViewModel.deleteFavouriteMedia(listFavouriteMovie.get(index).getId());
                        isFavourite=false;
                    }
                }else{
                    favouriteMediaViewModel.insertFavouriteMedia(movieID,titleOfMovie,TypeOfMedia.movie,null,null,posterImage, WatchStatus.UNWATCH);
                }

            }
        });
    }

    @Override
    public void onMovieClick(Movie movie) {
        if(connectivityViewModel.getState()){
            selectedMovie=movie;
            Intent intent=new Intent(this, MovieDetailActivity.class);
            intent.putExtra(StringConstants.movieDetailPageDataKey,movie.getID());
            startActivity(intent);
        }else{
            NoInternetToastHelpers.show(this);
        }

    }

    @Override
    public void onPersonClick(Person p) {

    }

    @Override
    public void onCastClick(Cast cast) {
        if(connectivityViewModel.getState()){
            selectedCast=cast;
            Intent intent=new Intent(this, PersonDetailActivity.class);
            intent.putExtra(StringConstants.personDetailDataKey,selectedCast.getID());
            startActivity(intent);
        }else{
            NoInternetToastHelpers.show(this);
        }

    }

    @Override
    public void onCLickVideo(Video video) {
        if(connectivityViewModel.getState()){
            selectedTrailer=video;
            Intent intent=new Intent(MovieDetailActivity.this, YoutubePlayerActivity.class);
            intent.putExtra(StringConstants.youtubeURLKey,selectedTrailer.getKey());
            intent.putExtra(StringConstants.youtubeTitleKey,selectedTrailer.getName());
            startActivity(intent);
        }else{
            NoInternetToastHelpers.show(this);
        }

    }
    private void navToAllTrailerActivity(List<Video> listVideo){
        Intent intent=new Intent(MovieDetailActivity.this,AllTrailersActivity.class);
        intent.putParcelableArrayListExtra(StringConstants.listVideoKey, (ArrayList<? extends Parcelable>) listVideo);
        startActivity(intent);
    }
}