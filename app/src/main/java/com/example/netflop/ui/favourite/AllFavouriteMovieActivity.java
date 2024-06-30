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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.databinding.ActivityAllFavouriteMediaBinding;
import com.example.netflop.databinding.ActivityAllFavouriteMovieBinding;
import com.example.netflop.helpers.NoInternetToastHelpers;
import com.example.netflop.ui.adapters.local.ListFavouriteMediaAdapter;
import com.example.netflop.ui.base.BaseActivity;
import com.example.netflop.ui.movie_detail.MovieDetailActivity;
import com.example.netflop.utils.CustomActionBar;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.utils.listeners.FavouriteListener;
import com.example.netflop.viewmodel.connectivity.ConnectivityViewModel;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllFavouriteMovieActivity extends BaseActivity implements FavouriteListener {
    ActivityAllFavouriteMovieBinding binding;
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
//        setContentView(R.layout.activity_all_favourite_movie);
        binding= ActivityAllFavouriteMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getBinding();
        initialize();
        callAPI();
        observeDataChange();
    }
    private void getBinding(){
        recyclerView=binding.allFavouriteMovieView;
        noDataImage=binding.noDataFavouriteMovie;
    }
    private void initialize(){
        actionBar=getSupportActionBar();
        SpannableString spannableTitle = new SpannableString("All favourite movie");
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
        favouriteMediaViewModel.fetchMovieData();
    }
    private void observeDataChange(){
        favouriteMediaViewModel.getFavouriteMovies().observe(this, new Observer<List<FavouriteMedia>>() {
            @Override
            public void onChanged(List<FavouriteMedia> favouriteMedia) {
                if(favouriteMedia!=null&&!favouriteMedia.isEmpty()){
//                    listMovie=movies;
//                    updateRecyclerView();
                    listFavourite.clear();  // Clear the list to ensure no duplicates if needed
                    listFavourite.addAll(favouriteMedia);
                    listFavouriteMediaAdapter.notifyDataSetChanged();
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
            selectedFavourite=favouriteMedia;
            Intent intent=new Intent(this, MovieDetailActivity.class);
            intent.putExtra(StringConstants.movieDetailPageDataKey,selectedFavourite.getMediaID());
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
        return  true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        callAPI();
    }
}