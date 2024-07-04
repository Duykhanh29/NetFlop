package com.example.netflop.ui.TV_Detail;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.text.util.Linkify;
import android.util.Log;
import android.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.constants.enums.TypeOfMedia;
import com.example.netflop.constants.enums.WatchStatus;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.movies.Genre;
import com.example.netflop.data.models.remote.movies.ProductionCompany;
import com.example.netflop.data.models.remote.movies.ProductionCountry;
import com.example.netflop.data.models.remote.TVs.CreatedBy;
import com.example.netflop.data.models.remote.TVs.Season;
import com.example.netflop.data.models.remote.TVs.TVSeriesDetail;
import com.example.netflop.databinding.ActivityTvseriesDetailBinding;
import com.example.netflop.helpers.CustomTextView;
import com.example.netflop.helpers.NoInternetToastHelpers;
import com.example.netflop.ui.adapters.remote.ListCreatedByAdapter;
import com.example.netflop.ui.adapters.remote.ListSeasonAdapter;
import com.example.netflop.ui.base.BaseActivity;
import com.example.netflop.ui.person_detail.PersonDetailActivity;
import com.example.netflop.utils.listeners.OnClickIDListener;
import com.example.netflop.utils.listeners.OnTVClickListener;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.utils.ToolBarUtils;
import com.example.netflop.viewmodel.connectivity.ConnectivityViewModel;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;
import com.example.netflop.viewmodel.remote.TVDetailViewModel;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class TVSeriesDetailActivity extends BaseActivity implements OnTVClickListener, OnClickIDListener {
    ActivityTvseriesDetailBinding binding;


    //primitive variable
    int tvSeriesID;

    // selected variables



    // adapters
    ListSeasonAdapter listSeasonAdapter;
    ListCreatedByAdapter listCreatedByAdapter;
    // view model
    TVDetailViewModel tvDetailViewModel;
    FavouriteMediaViewModel favouriteMediaViewModel;
    ConnectivityViewModel connectivityViewModel;
    // list data
    List<SlideModel> slideModels;
    List<String> listImage;
    List<String> listCountry,listCompany,listGenre;
    List<Season> listSeason;
    List<CreatedBy> listCreatedBy;
    // UI
    Toolbar toolbar;
    ImageSlider imageSlider;

    TextView isAdultTVSeriesTextView,votedRateTextView,popularityTextView,statusTextView,typeOfTVSeriesTextView,runTimeTextView,overviewTextView,tagLineTextView,numberOfSeasonTextView,numberOfEpisodeTextView,firstDateTextView,homepageTextView;
    FlexboxLayout flexboxCountry,flexboxCompany,flexboxGenre;
    RecyclerView createdByRecyclerView,seasonsTVSeriesRecyclerView;
    CardView latestTVEpisodeCardView,nextTVEpisodeCardView;
    TextView nameLatestTextView,latestTypeOfTVEpisodeTextView,latestNumberOfTVEpisodeTextView,nameNextEpisodeTextView,nextTypeOfTVEpisodeTextView,nextNumberOfTVEpisodeTextView;
    ImageView latestTVEpisodeImageView,nextTVEpisodeImageView;
    TextView isHavingLatestEpisodeView,isHavingNextEpisodeView,isHavingCreatedByEpisodeView;

    ImageView addFavouriteView;
    List<FavouriteMedia> listFavourite;
    boolean isFavourite=false;
    String tvSeriesTitle,imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   setContentView(R.layout.activity_tvseries_detail);

        binding=ActivityTvseriesDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getBinding();
        getData();
        initialize();
        callAPIs();
        loadFavouriteData();
        observeDataChange();
        observeFavouriteDataChange();
        handleFavouriteCLick();
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isEnabled()){
                    finish();
                }
            }
        });
    }
    private void getBinding(){
        toolbar=binding.toolBarTVSeriesView;
        imageSlider=binding.imageSliderTVSeriesDetail;
        // textview
        isAdultTVSeriesTextView=binding.isAdultTVSeriesView;
        votedRateTextView=binding.voteRatedTVSeriesDetailTV;
        popularityTextView=binding.tvSeriesPopularityView;
        statusTextView=binding.tvSeriesStatusView;
        typeOfTVSeriesTextView=binding.typeOfTVSeriesView;
        runTimeTextView=binding.runTimeOfTVSeriesView;
        overviewTextView=binding.overviewTVSeriesView;
        tagLineTextView=binding.tagLineTVSeriesView;
        numberOfSeasonTextView=binding.numberOfSeasonTVSeriesView;
        numberOfEpisodeTextView=binding.numberOfEpisodeTVSeriesView;
        firstDateTextView=binding.firstDateTVSeriesView;
        homepageTextView=binding.homepageTVSeriesView;

        flexboxCountry=binding.flexboxCountryTVSeries;
        flexboxCompany=binding.flexboxCompanyTVSeries;
        flexboxGenre=binding.flexboxGenreTVSeries;

        createdByRecyclerView=binding.createdByTVSeriesView;
        seasonsTVSeriesRecyclerView=binding.seasonsTVSeriesView;

        latestTVEpisodeCardView=binding.latestTVEpisodeView;
        nextTVEpisodeCardView=binding.nextTVEpisodeView;

        nameLatestTextView=binding.nameLatestEpisodeView;
        latestTypeOfTVEpisodeTextView=binding.latestTypeOfTVEpisodeView;
        latestNumberOfTVEpisodeTextView=binding.latestNumberOfTVEpisodeView;
        nameNextEpisodeTextView=binding.nameNextEpisodeView;
        nextTypeOfTVEpisodeTextView=binding.nextTypeOfTVEpisodeView;
        nextNumberOfTVEpisodeTextView=binding.nextNumberOfTVEpisodeView;

        latestTVEpisodeImageView=binding.latestTVEpisodeImageView;
        nextTVEpisodeImageView=binding.nextTVEpisodeImageView;

        isHavingLatestEpisodeView=binding.isHavingLatestEpisodeView;
        isHavingNextEpisodeView=binding.isHavingNextEpisodeView;
        isHavingCreatedByEpisodeView=binding.isHavingCreatedByEpisodeView;

        addFavouriteView=binding.addFavouriteImage;
    }
    private void getData(){
        Intent intent=getIntent();
        tvSeriesID=intent.getIntExtra(StringConstants.tvSeriesIDKey,-1);
    }
    private void initialize(){
        setSupportActionBar(toolbar);
        ToolBarUtils.setupBasicToolbar(toolbar,() -> finish());


        tvDetailViewModel= new ViewModelProvider(this).get(TVDetailViewModel.class);
        favouriteMediaViewModel= new ViewModelProvider(this).get(FavouriteMediaViewModel.class);
        connectivityViewModel=new ViewModelProvider(this).get(ConnectivityViewModel.class);
        listFavourite=new ArrayList<>();
        listCountry=new ArrayList<>();
        listCompany=new ArrayList<>();
        listGenre=new ArrayList<>();
        listImage=new ArrayList<>();
        slideModels=new ArrayList<>();
        listSeason=new ArrayList<>();
        listCreatedBy=new ArrayList<>();
        listCreatedByAdapter=new ListCreatedByAdapter(listCreatedBy,this,this);
        listSeasonAdapter=new ListSeasonAdapter(tvSeriesID,listSeason,this,this,favouriteMediaViewModel);
        RecyclerViewUtils.setupHorizontalRecyclerView(this,createdByRecyclerView,listCreatedByAdapter,LinearLayoutManager.HORIZONTAL,false);
        RecyclerViewUtils.setupHorizontalRecyclerView(this,seasonsTVSeriesRecyclerView,listSeasonAdapter,LinearLayoutManager.HORIZONTAL,false);

//        createdByRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        seasonsTVSeriesRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(0,20);
//        createdByRecyclerView.addItemDecoration(itemDecorator);
//        seasonsTVSeriesRecyclerView.addItemDecoration(itemDecorator);
//        createdByRecyclerView.setAdapter(listCreatedByAdapter);
//        seasonsTVSeriesRecyclerView.setAdapter(listSeasonAdapter);
    }
    private void callAPIs(){
        tvDetailViewModel.loadTVSeriesDetail(tvSeriesID,this);
    }
    private void observeDataChange(){
        tvDetailViewModel.getTvSeriesDetailData().observe(this, new Observer<TVSeriesDetail>() {
            @Override
            public void onChanged(TVSeriesDetail tvSeriesDetail) {
                if(tvSeriesDetail!=null){
//                    listCountry= CommonMethods.getListStringByListCountryModel(tvSeriesDetail.getProductionCountries());
//                    listCompany=CommonMethods.getListStringByListCompanyModel(tvSeriesDetail.getProductionCompanies());
                    updateData(tvSeriesDetail);
                    Log.d("Fuck","oke");
                }else{
                    Log.d("Fuck1","oke");
                }
            }
        });
    }
    private  void observeFavouriteDataChange(){
        favouriteMediaViewModel.getFavouriteTVSeries().observe(this, new Observer<List<FavouriteMedia>>() {
            @Override
            public void onChanged(List<FavouriteMedia> favouriteMedia) {
                listFavourite=favouriteMedia;
                for (int i = 0; i < favouriteMedia.size(); i++) {
                    if(favouriteMedia.get(i).getMediaID()==tvSeriesID){
                        isFavourite=true;
                        addFavouriteView.setImageResource(R.drawable.favoourite_no_border);
                    }
                }
                if(!isFavourite){
                    addFavouriteView.setImageResource(R.drawable.favorite_red_border);
                }
            }
        });
    }
    private  void handleFavouriteCLick(){
        addFavouriteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavourite){
                    int index=-1;
                    for (int i = 0; i <listFavourite.size() ; i++) {
                        if(listFavourite.get(i).getMediaID()==tvSeriesID){
                            index=i;
                            break;
                        }
                    }
                    if(index!=-1){
                        favouriteMediaViewModel.deleteFavouriteMedia(listFavourite.get(index).getId());
                        isFavourite=false;
                    }
                }else{
                    favouriteMediaViewModel.insertFavouriteMedia(tvSeriesID,tvSeriesTitle, TypeOfMedia.TVSeries,null,null,imagePath, WatchStatus.UNWATCH);
                }

            }
        });
    }
    private  void loadFavouriteData(){
        favouriteMediaViewModel.fetchTVSeriesData();
    }
    private void updateData(TVSeriesDetail tvSeriesDetail){
        overviewTextView.setText(tvSeriesDetail.getOverview());
        isAdultTVSeriesTextView.setText(tvSeriesDetail.getAdult()? "Any" :"Only adult");
        votedRateTextView.setText(tvSeriesDetail.getVoteAverage()+" ("+tvSeriesDetail.getVoteCount()+")");
        popularityTextView.setText(tvSeriesDetail.getPopularity()+"");
        statusTextView.setText("Status: "+tvSeriesDetail.getStatus());
        typeOfTVSeriesTextView.setText(tvSeriesDetail.getType());
        if(tvSeriesDetail.getEpisodeRunTime()!=null&&!tvSeriesDetail.getEpisodeRunTime().isEmpty()){
            runTimeTextView.setText(tvSeriesDetail.getEpisodeRunTime().get(0)+" minutes");
        }
        if(tvSeriesDetail.getHomepage()!=null){
            homepageTextView.setAutoLinkMask(Linkify.WEB_URLS);
        }else{
//            homepageTextView.setAutoLinkMask(0);
        }
        homepageTextView.setText(tvSeriesDetail.getHomepage()!=null ?tvSeriesDetail.getHomepage():"null" );
        tagLineTextView.setText(tvSeriesDetail.getTagline());
        numberOfSeasonTextView.setText(tvSeriesDetail.getNumberOfSeasons()+"");
        numberOfEpisodeTextView.setText(tvSeriesDetail.getNumberOfEpisodes()+"");
        firstDateTextView.setText(tvSeriesDetail.getFirstAirDate());
        if(tvSeriesDetail.getPosterPath()!=null){
            imagePath=tvSeriesDetail.getPosterPath();
            listImage.add(URLConstants.imageURL+tvSeriesDetail.getPosterPath());
        }
        if(tvSeriesDetail.getBackdropPath()!=null){
            listImage.add(URLConstants.imageURL+ tvSeriesDetail.getBackdropPath());
        }
        toolbar.setTitle(tvSeriesDetail.getName()!=null?tvSeriesDetail.getName():"null");
        tvSeriesTitle=tvSeriesDetail.getName()!=null ?tvSeriesDetail.getName():"";
        setSlideImageList();
        for (Genre genre:tvSeriesDetail.getGenres()) {
            Context context = new ContextThemeWrapper(this, R.style.NormalTextStyle);
            TextView textView = new TextView(context);
            textView.setText(genre.getName());
            textView= CustomTextView.chipTextView(textView);
            flexboxGenre.addView(textView);
        }
        for (ProductionCompany company:tvSeriesDetail.getProductionCompanies()) {
            Context context = new ContextThemeWrapper(this, R.style.NormalTextStyle);
            TextView textView = new TextView(context);
            textView.setText(company.getName());
            textView= CustomTextView.chipTextView(textView);
            flexboxCompany.addView(textView);
        }
        for (ProductionCountry country:tvSeriesDetail.getProductionCountries()) {
            Context context = new ContextThemeWrapper(this, R.style.NormalTextStyle);
            TextView textView = new TextView(context);
            textView.setText(country.getName());
            textView= CustomTextView.chipTextView(textView);
            flexboxCountry.addView(textView);
        }
        // latest and next episode
        if(tvSeriesDetail.getLastEpisodeToAir()!=null){
            nameLatestTextView.setText(tvSeriesDetail.getLastEpisodeToAir().getName()!=null?tvSeriesDetail.getLastEpisodeToAir().getName():"null");
            latestTypeOfTVEpisodeTextView.setText(tvSeriesDetail.getLastEpisodeToAir().getEpisodeType());
            latestNumberOfTVEpisodeTextView.setText(tvSeriesDetail.getNumberOfEpisodes()+"");
            String url= URLConstants.imageURL+tvSeriesDetail.getLastEpisodeToAir().getStillPath();
            Glide.with(this).load(url).placeholder(R.drawable.place_holder).into(latestTVEpisodeImageView);
            latestTVEpisodeCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(TVSeriesDetailActivity.this, TVEpisodeDetailActivity.class);
                    intent.putExtra(StringConstants.tvSeriesIDKey,tvSeriesID);
                    intent.putExtra(StringConstants.seasonNumberKey,tvSeriesDetail.getLastEpisodeToAir().getSeasonNumber());
                    intent.putExtra(StringConstants.episodeNumberKey,tvSeriesDetail.getLastEpisodeToAir().getEpisodeNumber());
                    startActivity(intent);
                }
            });
        }else{
            isHavingLatestEpisodeView.setVisibility(View.VISIBLE);
            latestTVEpisodeCardView.setVisibility(View.GONE);
        }
        if(tvSeriesDetail.getNextEpisodeToAir()!=null){
            nameNextEpisodeTextView.setText(tvSeriesDetail.getNextEpisodeToAir().getName()!=null?tvSeriesDetail.getNextEpisodeToAir().getName():"null");
            nextTypeOfTVEpisodeTextView.setText(tvSeriesDetail.getNextEpisodeToAir().getEpisodeType());
            nextNumberOfTVEpisodeTextView.setText(tvSeriesDetail.getNextEpisodeToAir().getEpisodeNumber()+"");
            String url= URLConstants.imageURL+tvSeriesDetail.getNextEpisodeToAir().getStillPath();
            Glide.with(this).load(url).placeholder(R.drawable.place_holder).into(nextTVEpisodeImageView);
            nextTVEpisodeCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(TVSeriesDetailActivity.this, TVEpisodeDetailActivity.class);
                    intent.putExtra(StringConstants.tvSeriesIDKey,tvSeriesID);
                    intent.putExtra(StringConstants.seasonNumberKey,tvSeriesDetail.getNextEpisodeToAir().getSeasonNumber());
                    intent.putExtra(StringConstants.episodeNumberKey,tvSeriesDetail.getNextEpisodeToAir().getEpisodeNumber());
                    startActivity(intent);
                }
            });
        }else{
            isHavingNextEpisodeView.setVisibility(View.VISIBLE);
            nextTVEpisodeCardView.setVisibility(View.GONE);
        }
        if(tvSeriesDetail.getCreatedBy()!=null&&!tvSeriesDetail.getCreatedBy().isEmpty()){
            listCreatedBy=tvSeriesDetail.getCreatedBy();
            listCreatedByAdapter.notifyDataSetChanged();
            listCreatedByAdapter=new ListCreatedByAdapter(listCreatedBy,this,this);
            createdByRecyclerView.setAdapter(listCreatedByAdapter);
        }else{
            isHavingCreatedByEpisodeView.setVisibility(View.VISIBLE);
            createdByRecyclerView.setVisibility(View.GONE);
        }
        listSeason=tvSeriesDetail.getSeasons();
        listSeasonAdapter=new ListSeasonAdapter(tvSeriesID,listSeason,this,this,favouriteMediaViewModel);
        seasonsTVSeriesRecyclerView.setAdapter(listSeasonAdapter);
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

    @Override
    public void onClick(int number) {
        if(connectivityViewModel.getState()){
            Intent intent=new Intent(this, TVSeasonDetailActivity.class);
            intent.putExtra(StringConstants.tvSeriesIDKey,tvSeriesID);
            intent.putExtra(StringConstants.seasonNumberKey,number);
            startActivity(intent);
        }else{
            NoInternetToastHelpers.show(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavouriteData();
    }

    @Override
    public void onCLick(int id, String type) {
        if(type.equals(StringConstants.personType)){
            Intent intent=new Intent(this, PersonDetailActivity.class);
            intent.putExtra(StringConstants.personDetailDataKey,id);
            startActivity(intent);
        }
    }
}