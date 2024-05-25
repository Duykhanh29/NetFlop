package com.example.netflop.ui.TV_Detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.TVs.Episode;
import com.example.netflop.data.models.TVs.TVSeasonsDetail;
import com.example.netflop.data.models.TVs.TVSeriesDetail;
import com.example.netflop.databinding.ActivityTvseasonDetailBinding;
import com.example.netflop.ui.adapters.GridEpisodeAdapter;
import com.example.netflop.ui.adapters.ListCreatedByAdapter;
import com.example.netflop.ui.adapters.ListSeasonAdapter;
import com.example.netflop.utils.OnTVClickListener;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.utils.SpacingItemDecorator;
import com.example.netflop.utils.ToolBarUtils;
import com.example.netflop.viewmodel.TVDetailViewModel;

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
    // adapters
    GridEpisodeAdapter episodeAdapter;



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
        observeDataChange();
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
        listEpisode=new ArrayList<>();
        episodeAdapter=new GridEpisodeAdapter(listEpisode,this,this);
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
    private void updateData(TVSeasonsDetail tvSeasonsDetail){
        toolbar.setTitle(tvSeasonsDetail.getName()!=null?tvSeasonsDetail.getName():"null");
        if(tvSeasonsDetail.getPosterPath()!=null){
            String url= URLConstants.imageURL+tvSeasonsDetail.getPosterPath();
            Glide.with(this).load(url).placeholder(R.drawable.place_holder).into(posterView);
        }
        tvSeasonNumberTextView.setText(tvSeasonsDetail.getSeasonNumber()+"");
        voteRatedTVSeasonTextView.setText(tvSeasonsDetail.getVoteAverage()+"");
        tvSeasonDateTextView.setText(tvSeasonsDetail.getAirDate());
        overviewTVSeasonTextView.setText(tvSeasonsDetail.getOverview());
        if(tvSeasonsDetail.getEpisodes()!=null&&!tvSeasonsDetail.getEpisodes().isEmpty()){
            listEpisode=tvSeasonsDetail.getEpisodes();
            episodeAdapter=new GridEpisodeAdapter(listEpisode,this,this);
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
}