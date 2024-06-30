package com.example.netflop.ui.home;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;

import com.example.netflop.R;
import com.example.netflop.constants.IntConstants;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.people.Cast;
import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.data.models.remote.people.Person;
import com.example.netflop.databinding.ActivityAllTopRatedMovieBinding;
import com.example.netflop.helpers.NoInternetToastHelpers;
import com.example.netflop.ui.TV_Detail.TVSeriesDetailActivity;
import com.example.netflop.ui.adapters.remote.GridMoviesAdapter;
import com.example.netflop.ui.base.BaseActivity;
import com.example.netflop.ui.movie_detail.MovieDetailActivity;
import com.example.netflop.utils.listeners.ItemTouchHelperAdapter;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.utils.SpacingItemDecorator;
import com.example.netflop.viewmodel.connectivity.ConnectivityViewModel;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;
import com.example.netflop.viewmodel.remote.TopRatedViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllTopRatedMovieActivity extends BaseActivity implements ItemTouchHelperAdapter {
    ActivityAllTopRatedMovieBinding binding;

    //
    Movie selectedMovie;
    GridMoviesAdapter gridMoviesAdapter;
    List<Movie> listMovie;

    // view model
    TopRatedViewModel topRatedViewModel;
    FavouriteMediaViewModel favouriteMediaViewModel;
    ConnectivityViewModel connectivityViewModel;
    // UI
    RecyclerView allTopRatedMovieRecyclerView;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAllTopRatedMovieBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_all_top_rated_movie);
        setContentView(binding.getRoot());
        getBinding();
        initialize();
        callAPI();
        fetchFavouriteData();
        observeDataChange();
        scrollListener();
        observeFavouriteDataChange();
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
        allTopRatedMovieRecyclerView=binding.allTopRatedMovieRecyclerView;
    }
    private void initialize(){
        actionBar=getSupportActionBar();
//        CustomActionBar.createActionBar(actionBar,"All popular movie");
        SpannableString spannableTitle = new SpannableString("All top rated movie");
        spannableTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(spannableTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.black_arrow_back);
        topRatedViewModel=new ViewModelProvider(this).get(TopRatedViewModel.class);
        favouriteMediaViewModel=new ViewModelProvider(this).get(FavouriteMediaViewModel.class);
        connectivityViewModel=new ViewModelProvider(this).get(ConnectivityViewModel.class);
        listMovie=new ArrayList<>();
        gridMoviesAdapter=new GridMoviesAdapter(listMovie,this,this,favouriteMediaViewModel);

        RecyclerViewUtils.setupGridRecyclerView(this,allTopRatedMovieRecyclerView,gridMoviesAdapter,2);

        allTopRatedMovieRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(30,20);
        allTopRatedMovieRecyclerView.addItemDecoration(itemDecorator);
        allTopRatedMovieRecyclerView.setAdapter(gridMoviesAdapter);

    }
    private void callAPI(){
        topRatedViewModel.fetchTopRated(this);
    }
    private void observeDataChange(){
        topRatedViewModel.getListMovieData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if(movies!=null&&!movies.isEmpty()){
//                    listMovie=movies;
//                    updateRecyclerView();
                    listMovie.clear();  // Clear the list to ensure no duplicates if needed
                    listMovie.addAll(movies);
                    gridMoviesAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    private  void observeFavouriteDataChange(){
        favouriteMediaViewModel.getFavouriteMovies().observe(this, new Observer<List<FavouriteMedia>>() {
            @Override
            public void onChanged(List<FavouriteMedia> favouriteMedia) {
//                gridMoviesAdapter.setFavouriteData(favouriteMedia);
            }
        });
    }
    private void fetchFavouriteData(){
        favouriteMediaViewModel.fetchMovieData();
    }
    private void scrollListener(){
        allTopRatedMovieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == gridMoviesAdapter.getItemCount() - IntConstants.PRE_FETCH_THRESHOLD) {
                    // Gọi phương thức để tải trang tiếp theo
                    topRatedViewModel.loadNextPage(AllTopRatedMovieActivity.this);
//                    observeDataChange();
                }
            }
        });

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
    public void onMovieClick(Movie movie) {
        if(connectivityViewModel.getState()){
            selectedMovie=movie;
            Intent intent=new Intent(this, MovieDetailActivity.class);
            intent.putExtra(StringConstants.movieDetailPageDataKey,selectedMovie.getID());
            startActivity(intent);
        }else{
            NoInternetToastHelpers.show(this);
        }
    }

    @Override
    public void onPersonClick(Person p) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume","onResume() of AllUpcomingActivity");
        fetchFavouriteData();
    }


    @Override
    public void onCastClick(Cast cast) {

    }
}