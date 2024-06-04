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
import android.view.MenuItem;

import com.example.netflop.R;
import com.example.netflop.constants.IntConstants;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.data.models.Cast;
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.models.Person;
import com.example.netflop.databinding.ActivityAllTopRatedMovieBinding;
import com.example.netflop.ui.adapters.GridMoviesAdapter;
import com.example.netflop.ui.movie_detail.MovieDetailActivity;
import com.example.netflop.utils.listeners.ItemTouchHelperAdapter;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.utils.SpacingItemDecorator;
import com.example.netflop.viewmodel.TopRatedViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllTopRatedMovieActivity extends AppCompatActivity implements ItemTouchHelperAdapter {
    ActivityAllTopRatedMovieBinding binding;

    //
    Movie selectedMovie;
    GridMoviesAdapter gridMoviesAdapter;
    List<Movie> listMovie;

    // view model
    TopRatedViewModel topRatedViewModel;
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
        observeDataChange();
        scrollListener();
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
        listMovie=new ArrayList<>();
        gridMoviesAdapter=new GridMoviesAdapter(listMovie,this,this);

        RecyclerViewUtils.setupGridRecyclerView(this,allTopRatedMovieRecyclerView,gridMoviesAdapter,2);

        allTopRatedMovieRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(30,20);
        allTopRatedMovieRecyclerView.addItemDecoration(itemDecorator);
        allTopRatedMovieRecyclerView.setAdapter(gridMoviesAdapter);
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isEnabled()){
                    finish();
                }
            }
        });
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
        selectedMovie=movie;
        Intent intent=new Intent(this, MovieDetailActivity.class);
        intent.putExtra(StringConstants.movieDetailPageDataKey,selectedMovie.getID());
        startActivity(intent);
    }

    @Override
    public void onPersonClick(Person p) {

    }

    @Override
    public void onCastClick(Cast cast) {

    }
}