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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Genre;
import com.example.netflop.data.models.ProductionCompany;
import com.example.netflop.data.models.ProductionCountry;
import com.example.netflop.data.models.TVs.CreatedBy;
import com.example.netflop.data.models.TVs.Season;
import com.example.netflop.data.models.TVs.TVSeriesDetail;
import com.example.netflop.databinding.ActivityTvseriesDetailBinding;
import com.example.netflop.helpers.CustomTextView;
import com.example.netflop.ui.adapters.ListCastAdapter;
import com.example.netflop.ui.adapters.ListCreatedByAdapter;
import com.example.netflop.ui.adapters.ListMovieCastAdapter;
import com.example.netflop.ui.adapters.ListSeasonAdapter;
import com.example.netflop.utils.CommonMethods;
import com.example.netflop.utils.OnTVClickListener;
import com.example.netflop.utils.SpacingItemDecorator;
import com.example.netflop.viewmodel.PersonViewModel;
import com.example.netflop.viewmodel.TVDetailViewModel;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class TVSeriesDetailActivity extends AppCompatActivity implements OnTVClickListener {
    ActivityTvseriesDetailBinding binding;


    //primitive variable
    int tvSeriesID;

    // selected variables



    // adapters
    ListSeasonAdapter listSeasonAdapter;
    ListCreatedByAdapter listCreatedByAdapter;
    // view model
    TVDetailViewModel tvDetailViewModel;
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
        observeDataChange();
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

    }
    private void getData(){
        Intent intent=getIntent();
        tvSeriesID=intent.getIntExtra(StringConstants.tvSeriesIDKey,-1);
    }
    private void initialize(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.black_arrow_back);
        toolbar.setNavigationContentDescription("Back");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        tvDetailViewModel= new ViewModelProvider(this).get(TVDetailViewModel.class);
        listCountry=new ArrayList<>();
        listCompany=new ArrayList<>();
        listGenre=new ArrayList<>();
        listImage=new ArrayList<>();
        slideModels=new ArrayList<>();
        listSeason=new ArrayList<>();
        listCreatedBy=new ArrayList<>();
        listCreatedByAdapter=new ListCreatedByAdapter(listCreatedBy,this);
        listSeasonAdapter=new ListSeasonAdapter(listSeason,this,this);

        createdByRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        seasonsTVSeriesRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(0,20);
        createdByRecyclerView.addItemDecoration(itemDecorator);
        seasonsTVSeriesRecyclerView.addItemDecoration(itemDecorator);
        createdByRecyclerView.setAdapter(listCreatedByAdapter);
        seasonsTVSeriesRecyclerView.setAdapter(listSeasonAdapter);
    }
    private void callAPIs(){
        tvDetailViewModel.callTVSeriesDetail(tvSeriesID);
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
            listImage.add(URLConstants.imageURL+tvSeriesDetail.getPosterPath());
        }
        if(tvSeriesDetail.getBackdropPath()!=null){
            listImage.add(URLConstants.imageURL+ tvSeriesDetail.getBackdropPath());
        }
        toolbar.setTitle(tvSeriesDetail.getName()!=null?tvSeriesDetail.getName():"null");
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
        }else{
            isHavingNextEpisodeView.setVisibility(View.VISIBLE);
            nextTVEpisodeCardView.setVisibility(View.GONE);
        }
        if(tvSeriesDetail.getCreatedBy()!=null&&!tvSeriesDetail.getCreatedBy().isEmpty()){
            listCreatedBy=tvSeriesDetail.getCreatedBy();
            listCreatedByAdapter.notifyDataSetChanged();
            listCreatedByAdapter=new ListCreatedByAdapter(listCreatedBy,this);
            createdByRecyclerView.setAdapter(listCreatedByAdapter);
        }else{
            isHavingCreatedByEpisodeView.setVisibility(View.VISIBLE);
            createdByRecyclerView.setVisibility(View.GONE);
        }
        listSeason=tvSeriesDetail.getSeasons();
        listSeasonAdapter=new ListSeasonAdapter(listSeason,this,this);
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

    }
}