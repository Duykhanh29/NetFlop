package com.example.netflop.ui.movie_detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableString;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Cast;
import com.example.netflop.data.models.Credit;
import com.example.netflop.data.models.Genre;
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.models.MovieDetail;
import com.example.netflop.data.models.MovieImages;
import com.example.netflop.data.models.MovieVideos;
import com.example.netflop.data.models.Person;
import com.example.netflop.data.models.ProductionCompany;
import com.example.netflop.data.models.ProductionCountry;
import com.example.netflop.data.models.Review;
import com.example.netflop.data.models.SpokenLanguage;
import com.example.netflop.data.models.Video;
import com.example.netflop.data.responses.RecommendationMovieResponse;
import com.example.netflop.data.responses.ReviewResponse;
import com.example.netflop.databinding.ActivityMovieDetailBinding;
import com.example.netflop.helpers.CustomTextView;
import com.example.netflop.ui.adapters.ListCastAdapter;
import com.example.netflop.ui.adapters.ListMovieAdapter;
import com.example.netflop.ui.adapters.ListReviewAdapter;
import com.example.netflop.ui.adapters.ListTrailerAdapter;
import com.example.netflop.ui.person_detail.PersonDetailActivity;
import com.example.netflop.utils.ClickableSpanHandler;
import com.example.netflop.utils.ItemTouchHelperAdapter;
import com.example.netflop.utils.OnClickListener;
import com.example.netflop.utils.OnTrailerClickListener;
import com.example.netflop.utils.SeeMoreOnClickListener;
import com.example.netflop.utils.SpacingItemDecorator;
import com.example.netflop.viewmodel.MovieViewModel;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements ItemTouchHelperAdapter, OnTrailerClickListener {
    ActivityMovieDetailBinding binding;
    MovieDetail movieDetailData;
    MovieImages movieImagesData;
    MovieVideos movieVideosData;
    ReviewResponse reviewResponseData;
    Credit creditData;
    RecommendationMovieResponse recommendationMovieResponseData;

    int movieID;
    MovieViewModel movieViewModel;

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

    ImageButton backButton;
    TextView titleMovie,overviewText,voteRatedMovie,releaseDateText,runtimeText,popularityTextView,statusTextView,tagLineTextView,budgetTextView,revenueTextView,homePageTextView;
    RecyclerView castRecyclerView,crewRecyclerView,recommendationRecyclerView,trailerRecyclerView,reviewRecyclerView;
    FlexboxLayout genresFlexBox,companyFlexBox,countryFlexBox,languageFlexBox;

    TextView seeMoreTrailers;
    TextView isHavingRecommendationTV,isTrailerTextView;


    // selected variables

    Cast selectedCast;
    Video selectedTrailer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding();
        getData();
        initialize();
        callAPIs();
        observerDataChanged();
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

        castRecyclerView=binding.castRecyclerView;
        crewRecyclerView=binding.crewsRecyclerView;
        recommendationRecyclerView=binding.recommendationMovieRecyclerView;
        trailerRecyclerView=binding.trailerDetailRecyclerView;

        genresFlexBox=binding.flexboxGenre;
        companyFlexBox=binding.flexboxCompany;
        countryFlexBox=binding.flexboxCountry;
        languageFlexBox=binding.flexboxLanguage;

        isHavingRecommendationTV=binding.isRecommendationDetailHaveID;
        isTrailerTextView=binding.isTrailerView;
        // see more
        seeMoreTrailers=binding.seeMoreTrailerDetail;

    }
    private void getData(){
        Intent intent=getIntent();
        movieID=intent.getIntExtra(StringConstants.movieDetailPageDataKey,-1);
    }
    private void initialize(){
        movieViewModel= new ViewModelProvider(this).get(MovieViewModel.class);
        listImage=new ArrayList<>();
        slideModels=new ArrayList<>();
        cast=new ArrayList<>();
        crew=new ArrayList<>();
        recommendationMovie=new ArrayList<>();
        listReview=new ArrayList<>();
        trailers=new ArrayList<>();
        setSlideImageList();
        initializeAdapters();
        initializeRecyclerView();
    }
    private void initializeAdapters(){
        castAdapter=new ListCastAdapter(this,cast,this);
        crewAdapter=new ListCastAdapter(this,crew,this);
        recommendationAdapter=new ListMovieAdapter(recommendationMovie,this,R.layout.single_movie_card,this);
        listTrailerAdapter=new ListTrailerAdapter(trailers,this,this);
        listReviewAdapter=new ListReviewAdapter(listReview,this);
    }
    private void initializeRecyclerView(){
        castRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        crewRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recommendationRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // decoration
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(10,10);
        SpacingItemDecorator itemDecorator1 = new SpacingItemDecorator(10,5);
        //
        castRecyclerView.addItemDecoration(itemDecorator);
        crewRecyclerView.addItemDecoration(itemDecorator);
        recommendationRecyclerView.addItemDecoration(itemDecorator);
        trailerRecyclerView.addItemDecoration(itemDecorator1);
        setAdapterForRecyclerView();
    }
    private void setAdapterForRecyclerView(){
        castRecyclerView.setAdapter(castAdapter);
        crewRecyclerView.setAdapter(crewAdapter);
        recommendationRecyclerView.setAdapter(recommendationAdapter);
        trailerRecyclerView.setAdapter(listTrailerAdapter);
    }
    private void callAPIs(){
        movieViewModel.callAPIMovieDetailByID(movieID);
        movieViewModel.callAPIMovieImageByID(movieID);
        movieViewModel.callAPIRecommendationByMovieID(movieID);
        movieViewModel.callAPIMovieVideoByID(movieID);
        movieViewModel.callAPIReviewResponseByMovieID(movieID);
        movieViewModel.callAPICreditByMovieID(movieID);
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
        recommendationAdapter=new ListMovieAdapter(recommendationMovie,this,R.layout.single_movie_card,this);
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
                    List<Video> listVideo=movieVideosData.getResults();
                    // nav to all trailers of movie
                    navToAllTrailerActivity(listVideo);
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
            listReview=reviewResponseData.getResults();
            listReviewAdapter=new ListReviewAdapter(listReview,MovieDetailActivity.this);
//            reviewRecyclerView.setAdapter(listReviewAdapter);
        }
    }
    private void updateMovieDetail(){
        if(movieDetailData.getPosterPath()!=null){
            addListImage(movieDetailData.getPosterPath());
        }
        if(movieDetailData.getBackdropPath()!=null){
            addListImage(movieDetailData.getBackdropPath());
        }
        setSlideImageList();
        titleMovie.setText(movieDetailData.getTitle());
        overviewText.setText(movieDetailData.getOverview());
        voteRatedMovie.setText(movieDetailData.getVoteAverage()+" ("+movieDetailData.getVoteCount()+")");
        releaseDateText.setText(movieDetailData.getReleaseDate());
        runtimeText.setText(movieDetailData.getRuntime()+" minutes");
        statusTextView.setText(movieDetailData.getStatus());
        popularityTextView.setText(movieDetailData.getPopularity()+"");
        tagLineTextView.setText(movieDetailData.getTagline());
        budgetTextView.setText(movieDetailData.getBudget()+"");
        homePageTextView.setText(movieDetailData.getHomepage());
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

    @Override
    public void onMovieClick(Movie movie) {

    }

    @Override
    public void onPersonClick(Person p) {

    }

    @Override
    public void onCastClick(Cast cast) {
        selectedCast=cast;
        Intent intent=new Intent(this, PersonDetailActivity.class);
        intent.putExtra(StringConstants.personDetailDataKey,selectedCast.getID());
        startActivity(intent);
    }

    @Override
    public void onCLickVideo(Video video) {
        selectedTrailer=video;
        Intent intent=new Intent(MovieDetailActivity.this, YoutubePlayerActivity.class);
        intent.putExtra(StringConstants.youtubeURLKey,selectedTrailer.getKey());
        startActivity(intent);
    }
    private void navToAllTrailerActivity(List<Video> listVideo){
        Intent intent=new Intent(MovieDetailActivity.this,AllTrailersActivity.class);
        intent.putParcelableArrayListExtra(StringConstants.listVideoKey, (ArrayList<? extends Parcelable>) listVideo);
        startActivity(intent);
    }
}