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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.enums.WatchStatus;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.databinding.ActivityAllFavouriteTvseasonBinding;
import com.example.netflop.databinding.ActivityAllFavouriteTvseriesBinding;
import com.example.netflop.helpers.NoInternetToastHelpers;
import com.example.netflop.ui.TV_Detail.TVSeasonDetailActivity;
import com.example.netflop.ui.TV_Detail.TVSeriesDetailActivity;
import com.example.netflop.ui.adapters.local.ListFavouriteMediaAdapter;
import com.example.netflop.ui.base.BaseActivity;
import com.example.netflop.ui.person_detail.PersonDetailActivity;
import com.example.netflop.utils.CustomActionBar;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.utils.listeners.FavouriteListener;
import com.example.netflop.viewmodel.connectivity.ConnectivityViewModel;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllFavouriteTVSeriesActivity extends BaseActivity implements FavouriteListener {
    ActivityAllFavouriteTvseriesBinding binding;
    ListFavouriteMediaAdapter listFavouriteMediaAdapter;

    // list data
    List<FavouriteMedia> listFavourite;
    ActionBar actionBar;

    // view model
    FavouriteMediaViewModel favouriteMediaViewModel;
    ConnectivityViewModel connectivityViewModel;
    // UI
    RecyclerView recyclerView;
    ImageView noDataImage;

    // selected
    FavouriteMedia selectedFavourite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_favourite_tvseries);
        binding= ActivityAllFavouriteTvseriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getBinding();
        initialize();
        callAPI();
        observeDataChange();
    }
    private void getBinding(){
        recyclerView=binding.allFavouriteSeriesView;
        noDataImage=binding.noDataFavouriteSeries;
    }
    private void initialize(){
        actionBar=getSupportActionBar();
        SpannableString spannableTitle = new SpannableString("All favourite series");
        spannableTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(spannableTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Log.d("HEY HEY HEY EHEYEEYE","fdsaf");
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
        favouriteMediaViewModel.fetchTVSeriesData();
    }
    private void observeDataChange(){
        favouriteMediaViewModel.getFavouriteTVSeries().observe(this, new Observer<List<FavouriteMedia>>() {
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
    protected void onResume() {
        super.onResume();
        callAPI();
    }

    @Override
    public void onClick(FavouriteMedia favouriteMedia) {
        if(connectivityViewModel.getState()){
            selectedFavourite=favouriteMedia;
            Intent intent=new Intent(this, TVSeriesDetailActivity.class);
            intent.putExtra(StringConstants.tvSeriesIDKey,selectedFavourite.getMediaID());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.watch_status_menu, menu);
        return true;
    }
}