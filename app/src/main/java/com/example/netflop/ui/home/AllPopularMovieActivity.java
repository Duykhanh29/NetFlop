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
import com.example.netflop.databinding.ActivityAllPopularMovieBinding;
import com.example.netflop.helpers.NoInternetToastHelpers;
import com.example.netflop.ui.adapters.remote.GridMoviesAdapter;
import com.example.netflop.ui.base.BaseActivity;
import com.example.netflop.ui.movie_detail.MovieDetailActivity;
import com.example.netflop.utils.listeners.ItemTouchHelperAdapter;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.viewmodel.connectivity.ConnectivityViewModel;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;
import com.example.netflop.viewmodel.remote.PopularMovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllPopularMovieActivity extends BaseActivity implements ItemTouchHelperAdapter {
    ActivityAllPopularMovieBinding binding;

    //
    Movie selectedMovie;
    GridMoviesAdapter gridMoviesAdapter;
    List<Movie> listMovie;

    // view model
    PopularMovieViewModel popularMovieViewModel;
    FavouriteMediaViewModel favouriteMediaViewModel;
    ConnectivityViewModel connectivityViewModel;
    // UI
    RecyclerView allPopularMovieRecyclerView;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAllPopularMovieBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_all_popular_movie);
        setContentView(binding.getRoot());
        getBinding();
        initialize();
        callAPI();
        observeDataChange();
        scrollListener();
        fetchFavouriteData();
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
        allPopularMovieRecyclerView=binding.allPopularMovieRecyclerView;
    }
    private void initialize(){
        actionBar=getSupportActionBar();
//        CustomActionBar.createActionBar(actionBar,"All popular movie");
        SpannableString spannableTitle = new SpannableString("All popular movie");
        spannableTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(spannableTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.black_arrow_back);
        popularMovieViewModel=new ViewModelProvider(this).get(PopularMovieViewModel.class);
        favouriteMediaViewModel=new ViewModelProvider(this).get(FavouriteMediaViewModel.class);
        connectivityViewModel=new ViewModelProvider(this).get(ConnectivityViewModel.class);
        listMovie=new ArrayList<>();
        gridMoviesAdapter=new GridMoviesAdapter(listMovie,this,this,favouriteMediaViewModel);

        RecyclerViewUtils.setupGridRecyclerView(this,allPopularMovieRecyclerView,gridMoviesAdapter,2);
//
//        allPopularMovieRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(30,20);
//        allPopularMovieRecyclerView.addItemDecoration(itemDecorator);
//        allPopularMovieRecyclerView.setAdapter(gridMoviesAdapter);
    }
    private void callAPI(){
        popularMovieViewModel.fetchPopularMovie(this);
    }
    private void observeDataChange(){
        popularMovieViewModel.getListMovieData().observe(this, new Observer<List<Movie>>() {
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
        allPopularMovieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    popularMovieViewModel.loadNextPage(AllPopularMovieActivity.this);
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