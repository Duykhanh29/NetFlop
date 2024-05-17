package com.example.netflop.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.netflop.data.models.Cast;
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.models.Person;
import com.example.netflop.databinding.ActivityAllPlayingNowBinding;
import com.example.netflop.ui.adapters.GridMoviesAdapter;
import com.example.netflop.utils.ItemTouchHelperAdapter;
import com.example.netflop.utils.SpacingItemDecorator;
import com.example.netflop.viewmodel.NowPlayingViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllPlayingNowActivity extends AppCompatActivity implements ItemTouchHelperAdapter {
    ActivityAllPlayingNowBinding binding;
    RecyclerView allPlayingNowRecyclerView;
    GridMoviesAdapter gridMoviesAdapter;
    List<Movie> listMovie;
    NowPlayingViewModel nowPlayingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAllPlayingNowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getBinding();
        initialize();
        callAPI();
        observeDataChange();
        scrollListener();
    }
    private void getBinding(){
        allPlayingNowRecyclerView=binding.allPlayingNowRecyclerView;
    }
    private void initialize(){
        nowPlayingViewModel=new ViewModelProvider(this).get(NowPlayingViewModel.class);
        listMovie=new ArrayList<>();
        gridMoviesAdapter=new GridMoviesAdapter(listMovie,this,this);
        allPlayingNowRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(30,20);
        allPlayingNowRecyclerView.addItemDecoration(itemDecorator);
        allPlayingNowRecyclerView.setAdapter(gridMoviesAdapter);
    }
    private void callAPI(){
        nowPlayingViewModel.callAPI();
    }
    private void scrollListener(){
        allPlayingNowRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    nowPlayingViewModel.loadNextPage();
                    observeDataChange();
                }
            }
        });

    }
    private void updateRecyclerView(){
        gridMoviesAdapter=new GridMoviesAdapter(listMovie,this,this);
        allPlayingNowRecyclerView.setAdapter(gridMoviesAdapter);
    }
    private void observeDataChange(){
        nowPlayingViewModel.getListMovieData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if(movies!=null&&!movies.isEmpty()){
                    listMovie.clear();
                    listMovie=movies;
                    updateRecyclerView();
                }
            }
        });
    }

    @Override
    public void onMovieClick(Movie movie) {

    }

    @Override
    public void onPersonClick(Person p) {

    }

    @Override
    public void onCastClick(Cast cast) {

    }
}