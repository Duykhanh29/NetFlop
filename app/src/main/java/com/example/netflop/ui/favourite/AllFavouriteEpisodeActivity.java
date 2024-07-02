package com.example.netflop.ui.favourite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.enums.WatchStatus;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.databinding.ActivityAllFavouriteEpisodeBinding;
import com.example.netflop.databinding.ActivityAllPlayingNowBinding;
import com.example.netflop.helpers.NoInternetToastHelpers;
import com.example.netflop.ui.TV_Detail.TVEpisodeDetailActivity;
import com.example.netflop.ui.adapters.local.ListFavouriteMediaAdapter;
import com.example.netflop.ui.adapters.remote.GridMoviesAdapter;
import com.example.netflop.ui.base.BaseActivity;
import com.example.netflop.utils.CustomActionBar;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.utils.listeners.FavouriteListener;
import com.example.netflop.viewmodel.connectivity.ConnectivityViewModel;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;
import com.example.netflop.viewmodel.remote.NowPlayingViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllFavouriteEpisodeActivity extends BaseActivity implements FavouriteListener {
    ActivityAllFavouriteEpisodeBinding binding;
    // adapters
    ListFavouriteMediaAdapter listFavouriteMediaAdapter;
    ConnectivityViewModel connectivityViewModel;

    // list data
    List<FavouriteMedia> listFavourite;
    ActionBar actionBar;

    // view model
    FavouriteMediaViewModel favouriteMediaViewModel;

    // UI
    RecyclerView recyclerView;
    ImageView noDataImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_favourite_episode);
        binding= ActivityAllFavouriteEpisodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getBinding();
        initialize();
        callAPI();
        observeDataChange();
    }
    private void getBinding(){
        recyclerView=binding.allFavouriteEpisodeView;
        noDataImage=binding.noDataFavouriteEpisode;
    }
    private void initialize(){
        actionBar=getSupportActionBar();
        SpannableString spannableTitle = new SpannableString("All favourite TV episode");
        spannableTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(spannableTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.black_arrow_back);
//        CustomActionBar.createActionBar(actionBar,"All playing now movie");
        favouriteMediaViewModel=new ViewModelProvider(this).get(FavouriteMediaViewModel.class);
        connectivityViewModel=new ViewModelProvider(this).get(ConnectivityViewModel.class);
        listFavourite=new ArrayList<>();
        listFavouriteMediaAdapter=new ListFavouriteMediaAdapter(listFavourite,this,this,favouriteMediaViewModel);

        RecyclerViewUtils.setupGridRecyclerView(this,recyclerView,listFavouriteMediaAdapter,2);

//        allPlayingNowRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(30,20);
//        allPlayingNowRecyclerView.addItemDecoration(itemDecorator);
//        allPlayingNowRecyclerView.setAdapter(gridMoviesAdapter);
    }
    private void callAPI(){
        favouriteMediaViewModel.fetchTVEpisodesData();
    }
    private void observeDataChange(){
        favouriteMediaViewModel.getFavouriteTVEpisodes().observe(this, new Observer<List<FavouriteMedia>>() {
            @Override
            public void onChanged(List<FavouriteMedia> favouriteMedia) {
                if(favouriteMedia!=null&&!favouriteMedia.isEmpty()){
//                    listMovie=movies;
//                    updateRecyclerView();
                    listFavourite.clear();  // Clear the list to ensure no duplicates if needed
                    listFavourite.addAll(favouriteMedia);
                    listFavouriteMediaAdapter.notifyDataSetChanged();
                    listFavouriteMediaAdapter.updateList(favouriteMedia);
                }else{
                    recyclerView.setVisibility(View.GONE);
                    noDataImage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(FavouriteMedia favouriteMedia) {
        if(connectivityViewModel.getState()){
            Intent  intent=new Intent(this, TVEpisodeDetailActivity.class);
            intent.putExtra(StringConstants.tvSeriesIDKey,favouriteMedia.getMediaID());
            intent.putExtra(StringConstants.seasonNumberKey,favouriteMedia.getSeasonNumber());
            intent.putExtra(StringConstants.episodeNumberKey,favouriteMedia.getEpisodeNumber());
            startActivity(intent);
        }else{
            NoInternetToastHelpers.show(this);
        }

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID=item.getItemId();
        if(itemID==android.R.id.home){
            finish();
        }
        if(itemID==R.id.menu_all){
            listFavouriteMediaAdapter.filterItems(null);
        }
        if(itemID==R.id.menu_unwatched){
            listFavouriteMediaAdapter.filterItems(WatchStatus.UNWATCH);
        }
        if(itemID== R.id.menu_watched){
            listFavouriteMediaAdapter.filterItems(WatchStatus.WATCHED);
        } if(itemID==R.id.menu_in_progress){
            listFavouriteMediaAdapter.filterItems(WatchStatus.IN_PROGRESS);
        }
        return  true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        callAPI();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.watch_status_menu, menu);
        return true;
    }

}