package com.example.netflop.ui.TV_Detail;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.constants.enums.TypeOfMedia;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.TVs.Episode;
import com.example.netflop.data.models.remote.TVs.TVSeasonsDetail;
import com.example.netflop.databinding.ActivityTvseasonDetailBinding;
import com.example.netflop.ui.adapters.remote.GridEpisodeAdapter;
import com.example.netflop.utils.listeners.OnTVClickListener;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.utils.ToolBarUtils;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;
import com.example.netflop.viewmodel.remote.TVDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class TVSeasonDetailActivity extends AppCompatActivity implements OnTVClickListener {
    ActivityTvseasonDetailBinding binding;


    // UI
    Toolbar toolbar;
    ImageView posterView;
    TextView tvSeasonNumberTextView,voteRatedTVSeasonTextView,tvSeasonDateTextView,overviewTVSeasonTextView;
    RecyclerView episodeRecyclerView;
    TextView isHavingEpisodesTextView;

    // primitive variable
    int tvSeriesID;
    int seasonNumber;

    // list data
    List<Episode> listEpisode;
    // view model
    TVDetailViewModel tvDetailViewModel;
    FavouriteMediaViewModel favouriteMediaViewModel;
    // adapters
    GridEpisodeAdapter episodeAdapter;


    ImageView addFavouriteView;
    List<FavouriteMedia> listFavourite;
    boolean isFavourite=false;
    String tvSeasonTitle,imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tvseason_detail);

        binding=ActivityTvseasonDetailBinding.inflate(getLayoutInflater());
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
        toolbar=binding.toolBarTVSeasonView;
        posterView=binding.tvSeasonImageView;
        tvSeasonNumberTextView=binding.tvSeasonNumberView;
        voteRatedTVSeasonTextView=binding.voteRatedTVSeasonDetailTV;
        tvSeasonDateTextView=binding.tvSeasonDateView;
        overviewTVSeasonTextView=binding.overviewTVSeasonView;
        isHavingEpisodesTextView=binding.isHavingEpisodesView;
        episodeRecyclerView=binding.tvSeasonEpisodesRecyclerView;
        addFavouriteView=binding.addFavouriteImage;
    }
    private void getData(){
        Intent intent=getIntent();
        tvSeriesID=intent.getIntExtra(StringConstants.tvSeriesIDKey,-1);
        seasonNumber=intent.getIntExtra(StringConstants.seasonNumberKey,-1);
    }
    private void initialize(){
        setSupportActionBar(toolbar);
        ToolBarUtils.setupBasicToolbar(toolbar,() -> finish());
        tvDetailViewModel= new ViewModelProvider(this).get(TVDetailViewModel.class);
        favouriteMediaViewModel=new ViewModelProvider(this).get(FavouriteMediaViewModel.class);
        listEpisode=new ArrayList<>();
        listFavourite=new ArrayList<>();
        episodeAdapter=new GridEpisodeAdapter(tvSeriesID,listEpisode,this,this,favouriteMediaViewModel);
//
//        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2){
//            @Override
//            public boolean canScrollVertically() {
//                return true;
//            }
//        };
        RecyclerViewUtils.setupGridRecyclerView(this,episodeRecyclerView,episodeAdapter,2);
//        episodeRecyclerView.setLayoutManager(gridLayoutManager);
//        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(20,20);
//        episodeRecyclerView.addItemDecoration(itemDecorator);
//        episodeRecyclerView.setAdapter(episodeAdapter);
    }
    private void callAPIs(){
        tvDetailViewModel.loadTVSeasonDetail(tvSeriesID,seasonNumber,this);
    }
    private void observeDataChange(){
        tvDetailViewModel.getTvSeasonDetailData().observe(this, new Observer<TVSeasonsDetail>() {
            @Override
            public void onChanged(TVSeasonsDetail tvSeasonsDetail) {
                if(tvSeasonsDetail!=null){
                    updateData(tvSeasonsDetail);
                }
            }
        });
    }
    private  void observeFavouriteDataChange(){
        favouriteMediaViewModel.getFavouriteTVEpisodes().observe(this, new Observer<List<FavouriteMedia>>() {
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
                    int index=0;
                    for (int i = 0; i <listFavourite.size() ; i++) {
                        if(listFavourite.get(i).getMediaID()==tvSeriesID&&listFavourite.get(i).getSeasonNumber()==seasonNumber){
                            index=i;
                            break;
                        }
                    }
                    if(index!=0){
                        favouriteMediaViewModel.deleteFavouriteMedia(listFavourite.get(index).getId());
                        isFavourite=false;
                    }
                }else{
                    favouriteMediaViewModel.insertFavouriteMedia(tvSeriesID,tvSeasonTitle, TypeOfMedia.TVSeason,seasonNumber,null,imagePath);
                }

            }
        });
    }
    private  void loadFavouriteData(){
        favouriteMediaViewModel.fetchTVSeasonsData();
    }
    private void updateData(TVSeasonsDetail tvSeasonsDetail){
        toolbar.setTitle(tvSeasonsDetail.getName()!=null?tvSeasonsDetail.getName():"null");
        tvSeasonTitle=tvSeasonsDetail.getName()!=null?tvSeasonsDetail.getName():"null";
        if(tvSeasonsDetail.getPosterPath()!=null){
            imagePath=tvSeasonsDetail.getPosterPath();
            String url= URLConstants.imageURL+tvSeasonsDetail.getPosterPath();
            Glide.with(this).load(url).placeholder(R.drawable.place_holder).into(posterView);
        }
        tvSeasonNumberTextView.setText(tvSeasonsDetail.getSeasonNumber()+"");
        voteRatedTVSeasonTextView.setText(tvSeasonsDetail.getVoteAverage()+"");
        tvSeasonDateTextView.setText(tvSeasonsDetail.getAirDate());
        overviewTVSeasonTextView.setText(tvSeasonsDetail.getOverview());
        if(tvSeasonsDetail.getEpisodes()!=null&&!tvSeasonsDetail.getEpisodes().isEmpty()){
            listEpisode=tvSeasonsDetail.getEpisodes();
            episodeAdapter=new GridEpisodeAdapter(tvSeriesID,listEpisode,this,this,favouriteMediaViewModel);
            episodeRecyclerView.setAdapter(episodeAdapter);
        }else{
            isHavingEpisodesTextView.setVisibility(View.VISIBLE);
            episodeRecyclerView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(int number) {
        Intent intent=new Intent(this, TVEpisodeDetailActivity.class);
        intent.putExtra(StringConstants.tvSeriesIDKey,tvSeriesID);
        intent.putExtra(StringConstants.seasonNumberKey,seasonNumber);
        intent.putExtra(StringConstants.episodeNumberKey,number);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadFavouriteData();
    }
}