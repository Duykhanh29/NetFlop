package com.example.netflop.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.data.models.Cast;
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.models.Person;
import com.example.netflop.databinding.ActivityAllPopularMovieBinding;
import com.example.netflop.ui.adapters.GridMoviesAdapter;
import com.example.netflop.ui.movie_detail.MovieDetailActivity;
import com.example.netflop.utils.ItemTouchHelperAdapter;
import com.example.netflop.utils.SpacingItemDecorator;
import com.example.netflop.viewmodel.NowPlayingViewModel;
import com.example.netflop.viewmodel.PopularMovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllPopularMovieActivity extends AppCompatActivity implements ItemTouchHelperAdapter {
    ActivityAllPopularMovieBinding binding;

    //
    Movie selectedMovie;
    GridMoviesAdapter gridMoviesAdapter;
    List<Movie> listMovie;

    // view model
    PopularMovieViewModel popularMovieViewModel;
    // UI
    RecyclerView allPopularMovieRecyclerView;
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
    }
    private void getBinding(){
        allPopularMovieRecyclerView=binding.allPopularMovieRecyclerView;
    }
    private void initialize(){
        popularMovieViewModel=new ViewModelProvider(this).get(PopularMovieViewModel.class);
        listMovie=new ArrayList<>();
        gridMoviesAdapter=new GridMoviesAdapter(listMovie,this,this);
        allPopularMovieRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(30,20);
        allPopularMovieRecyclerView.addItemDecoration(itemDecorator);
        allPopularMovieRecyclerView.setAdapter(gridMoviesAdapter);
    }
    private void callAPI(){
        popularMovieViewModel.callAPI();
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
                if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == gridMoviesAdapter.getItemCount() - 1) {
                    // Gọi phương thức để tải trang tiếp theo
                    popularMovieViewModel.loadNextPage();
//                    observeDataChange();
                }
            }
        });

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