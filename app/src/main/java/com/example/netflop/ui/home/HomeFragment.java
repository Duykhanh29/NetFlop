package com.example.netflop.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.data.models.Cast;
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.models.Person;
import com.example.netflop.data.responses.NowPlayingResponse;
import com.example.netflop.data.responses.PopularResponse;
import com.example.netflop.data.responses.TopRatedResponse;
import com.example.netflop.data.responses.TrendingMovieResponse;
import com.example.netflop.data.responses.TrendingPeopleResponse;
import com.example.netflop.data.responses.UpcomingResponse;
import com.example.netflop.databinding.FragmentHomeBinding;
import com.example.netflop.ui.adapters.ListMovieAdapter;
import com.example.netflop.ui.adapters.ListPersonAdapter;
import com.example.netflop.ui.adapters.SecondListMovieAdapter;
import com.example.netflop.ui.movie_detail.MovieDetailActivity;
import com.example.netflop.utils.ItemTouchHelperAdapter;
import com.example.netflop.utils.VerticalSpacingItemDecorator;
import com.example.netflop.viewmodel.NowPlayingViewModel;
import com.example.netflop.viewmodel.PopularMovieViewModel;
import com.example.netflop.viewmodel.TopRatedViewModel;
import com.example.netflop.viewmodel.TrendingMovieViewModel;
import com.example.netflop.viewmodel.TrendingPeopleViewModel;
import com.example.netflop.viewmodel.UpcomingViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ItemTouchHelperAdapter {

    private FragmentHomeBinding binding;
    private NowPlayingViewModel nowPlayingViewModel;
    private PopularMovieViewModel popularMovieViewModel;
    private TopRatedViewModel topRatedViewModel;
    private  UpcomingViewModel upcomingViewModel;
    private TrendingMovieViewModel trendingMovieViewModel;
    private TrendingPeopleViewModel trendingPeopleViewModel;
    List<Movie> listNowPlaying;
    List<Movie> listPopularMovie;
    List<Movie> listTopRatedMovie;
    List<Movie> listTrendingMovie;
    List<Movie> listUpcomingMovie;
    List<Person> listTrendingPeople;

    ListMovieAdapter playingNowAdapter,popularMovieAdapter,trendingMovieAdapter;
    SecondListMovieAdapter upcomingAdapter,topRatedAdapter;

    ListPersonAdapter trendingPersonAdapter;

    Movie selectedMovie;
    Person selectedPerson;

    // ui

    RecyclerView playingNowRecyclerView,popularMovieRecyclerView,topRatedRecyclerView,trendingMovieRecyclerView,trendingPeopleRecyclerView,upcomingRecyclerView;
    TextView topRated,upcoming,trendingMovie,trendingPeople,nowPlaying,popularMovie;
    TextView textView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding();
        init();
        callAPIs();
        observeChanges();
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void binding(){
        textView=binding.textHome;
        playingNowRecyclerView=binding.recyclerViewPlayingNow;
        trendingMovieRecyclerView=binding.recyclerViewTrendingMovie;
        popularMovieRecyclerView=binding.recyclerViewPopularMovie;
        topRatedRecyclerView=binding.recyclerViewTopRatedMovie;
        upcomingRecyclerView=binding.recyclerViewUpcomingMovie;
        trendingPeopleRecyclerView=binding.recyclerViewTrendingPeople;


        nowPlaying=binding.listNowPlayingID;
        trendingMovie=binding.listTrendingMovieID;
        topRated=binding.listTopRatedMovieID;
        popularMovie=binding.listPopularMovieID;
        upcoming=binding.listUpcomingID;
        trendingPeople=binding.listTrendingPeopleID;
    }
    private void init(){
        initializeDataUI();
        initializeViewModels();
        initializeListData();
        initializeAdapter();
        initializeRecyclerView();
    }
    private void initializeDataUI(){
        nowPlaying.setText("Now playing");
        trendingPeople.setText("Trending people");
        trendingMovie.setText("Trending movies");
        topRated.setText("Top rated");
        popularMovie.setText("Popular movies");
        upcoming.setText("Upcoming");
    }
    private void initializeAdapter(){
        playingNowAdapter= new ListMovieAdapter(listNowPlaying,requireContext(), R.layout.single_movie_card,this);
        popularMovieAdapter= new ListMovieAdapter(listPopularMovie,requireContext(), R.layout.single_movie_card,this);
        trendingMovieAdapter= new ListMovieAdapter(listTrendingMovie,requireContext(), R.layout.single_movie_card,this);
        upcomingAdapter= new SecondListMovieAdapter(listUpcomingMovie,requireContext(), R.layout.a_movie_card,this);
        topRatedAdapter= new SecondListMovieAdapter(listTopRatedMovie,requireContext(), R.layout.a_movie_card,this);
        trendingPersonAdapter=new ListPersonAdapter(listTrendingPeople,requireContext(),R.layout.single_person_card);
    }

    private void initializeRecyclerView(){
        playingNowRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        popularMovieRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        topRatedRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingMovieRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingPeopleRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        // decoration
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10,10);
        //
        playingNowRecyclerView.addItemDecoration(itemDecorator);
        popularMovieRecyclerView.addItemDecoration(itemDecorator);
        topRatedRecyclerView.addItemDecoration(itemDecorator);
        trendingMovieRecyclerView.addItemDecoration(itemDecorator);
        trendingPeopleRecyclerView.addItemDecoration(itemDecorator);
        upcomingRecyclerView.addItemDecoration(itemDecorator);
        setAdapterForRecyclerView();

    }
    private void setAdapterForRecyclerView(){
        playingNowRecyclerView.setAdapter(playingNowAdapter);
        topRatedRecyclerView.setAdapter(topRatedAdapter);
        upcomingRecyclerView.setAdapter(upcomingAdapter);
        popularMovieRecyclerView.setAdapter(popularMovieAdapter);
        trendingMovieRecyclerView.setAdapter(trendingMovieAdapter);
        trendingPeopleRecyclerView.setAdapter(trendingPersonAdapter);
    }

    private void initializeViewModels(){
        nowPlayingViewModel=new ViewModelProvider(this).get(NowPlayingViewModel.class);
        popularMovieViewModel=new ViewModelProvider(this).get(PopularMovieViewModel.class);
        topRatedViewModel=new ViewModelProvider(this).get(TopRatedViewModel.class);
        upcomingViewModel=new ViewModelProvider(this).get(UpcomingViewModel.class);
        trendingMovieViewModel=new ViewModelProvider(this).get(TrendingMovieViewModel.class);
        trendingPeopleViewModel=new ViewModelProvider(this).get(TrendingPeopleViewModel.class);

    }
    private void initializeListData(){
        listNowPlaying=new ArrayList<>();
        listPopularMovie=new ArrayList<>();
        listTrendingMovie=new ArrayList<>();
        listUpcomingMovie=new ArrayList<>();
        listTopRatedMovie=new ArrayList<>();
        listTrendingPeople=new ArrayList<>();
    }

    // update functions
    private void updatePlayingNow(){
        playingNowAdapter= new ListMovieAdapter(listNowPlaying,requireContext(), R.layout.single_movie_card,this);
        playingNowRecyclerView.setAdapter(playingNowAdapter);
    }
    private void updateTrendingMovie(){
        trendingMovieAdapter= new ListMovieAdapter(listTrendingMovie,requireContext(), R.layout.single_movie_card,this);
        trendingMovieRecyclerView.setAdapter(trendingMovieAdapter);
    }
    private void updateTrendingPeople(){
        trendingPersonAdapter=new ListPersonAdapter(listTrendingPeople,requireContext(),R.layout.single_person_card);
        trendingPeopleRecyclerView.setAdapter(trendingPersonAdapter);
    }
    private void updateTopRated(){
        topRatedAdapter= new SecondListMovieAdapter(listTopRatedMovie,requireContext(), R.layout.a_movie_card,this);
        topRatedRecyclerView.setAdapter(topRatedAdapter);
    }
    private void updatePopularMovie(){
        popularMovieAdapter= new ListMovieAdapter(listPopularMovie,requireContext(), R.layout.single_movie_card,this);
        popularMovieRecyclerView.setAdapter(popularMovieAdapter);
    }
    private void updateUpcomingMovie(){
        upcomingAdapter= new SecondListMovieAdapter(listUpcomingMovie,requireContext(), R.layout.a_movie_card,this);
        upcomingRecyclerView.setAdapter(upcomingAdapter);
    }



    private void callAPIs(){
        nowPlayingViewModel.callAPI();
        popularMovieViewModel.callAPI();
        topRatedViewModel.callAPI();
        upcomingViewModel.callAPI();
        trendingMovieViewModel.callAPI();
        trendingPeopleViewModel.callAPI();
    }
    private void observeChanges(){
        // now playing
        nowPlayingViewModel.getNowPlayingData().observe(getViewLifecycleOwner(), new Observer<NowPlayingResponse>() {
            @Override
            public void onChanged(NowPlayingResponse nowPlayingResponse) {
                if(nowPlayingResponse!=null){
                    if(!nowPlayingResponse.getResults().isEmpty()&&nowPlayingResponse.getResults()!=null){
                        listNowPlaying=nowPlayingResponse.getResults();
                        Log.d("listNowPlaying TAG\n","START");
                        for (int i = 0; i < listNowPlaying.size(); i++) {
                            Log.d("TAG",listNowPlaying.get(i).getTitle());
                        }
                        updatePlayingNow();

                    }else{
                        Log.d("listNowPlaying TAG\n","listNowPlaying empty");
                    }
                }else{
                    Log.d("listNowPlaying TAG\n","listNowPlaying Eror");
                }
            }
        });

        //top rated
      topRatedViewModel.getTopRatedData().observe(getViewLifecycleOwner(), new Observer<TopRatedResponse>() {
          @Override
          public void onChanged(TopRatedResponse topRatedResponse) {
              if(topRatedResponse!=null){
                  if(!topRatedResponse.getResults().isEmpty()&&topRatedResponse.getResults()!=null){
                      listTopRatedMovie=topRatedResponse.getResults();
                      Log.d("listTopRatedMovie TAG\n","START");
                      for (int i = 0; i < listTopRatedMovie.size(); i++) {
                          Log.d("TAG",listTopRatedMovie.get(i).getTitle());
                      }
                      updateTopRated();

                  }else{
                      Log.d("listTopRatedMovie TAG\n","listTopRatedMovie empty");
                  }
              }
          }
      });

      // popular movie
        popularMovieViewModel.getPopularMovieData().observe(getViewLifecycleOwner(), new Observer<PopularResponse>() {
            @Override
            public void onChanged(PopularResponse popularResponse) {
                if(popularResponse!=null){
                    if(!popularResponse.getResults().isEmpty()&&popularResponse.getResults()!=null){
                        listPopularMovie=popularResponse.getResults();
                        Log.d("listPopularMovie TAG\n","START");
                        for (int i = 0; i < listPopularMovie.size(); i++) {
                            Log.d("TAG",listPopularMovie.get(i).getTitle());
                        }
                        updatePopularMovie();

                    }else{
                        Log.d("listPopularMovie TAG\n","listPopularMovie empty");
                    }
                }
            }
        });

        // upcomingViewModel
        upcomingViewModel.getUpcomingData().observe(getViewLifecycleOwner(), new Observer<UpcomingResponse>() {
            @Override
            public void onChanged(UpcomingResponse upcomingResponse) {
                if(upcomingResponse!=null){
                    if(!upcomingResponse.getResults().isEmpty()&&upcomingResponse.getResults()!=null){
                        listUpcomingMovie=upcomingResponse.getResults();
                        Log.d("listUpcomingMovie TAG\n","START");
                        for (int i = 0; i < listUpcomingMovie.size(); i++) {
                            Log.d("TAG",listUpcomingMovie.get(i).getTitle());
                        }
                        updateUpcomingMovie();

                    }else{
                        Log.d("listUpcomingMovie TAG\n","listUpcomingMovie empty");
                    }
                }
            }
        });

        // trending movie
        trendingMovieViewModel.getTrendingMovieData().observe(getViewLifecycleOwner(), new Observer<TrendingMovieResponse>() {
            @Override
            public void onChanged(TrendingMovieResponse trendingMovieResponse) {
                if(trendingMovieResponse!=null){
                    if(!trendingMovieResponse.getResults().isEmpty()&&trendingMovieResponse.getResults()!=null){
                        listTrendingMovie=trendingMovieResponse.getResults();
                        Log.d("listTrendingMovie TAG\n","START");
                        for (int i = 0; i < listTrendingMovie.size(); i++) {
                            Log.d("TAG",listTrendingMovie.get(i).getTitle());
                        }
                        updateTrendingMovie();

                    }else{
                        Log.d("listTrendingMovie TAG\n","listTrendingMovie empty");
                    }
                }
            }
        });

        // trending people
        trendingPeopleViewModel.getTrendingPeopleData().observe(getViewLifecycleOwner(), new Observer<TrendingPeopleResponse>() {
            @Override
            public void onChanged(TrendingPeopleResponse trendingPeopleResponse) {
                if(trendingPeopleResponse!=null){
                    if(!trendingPeopleResponse.getResults().isEmpty()&&trendingPeopleResponse.getResults()!=null){
                        listTrendingPeople=trendingPeopleResponse.getResults();
                        Log.d("listTrendingPeople TAG\n","START");
                        for (int i = 0; i < listTrendingPeople.size(); i++) {
                            Log.d("TAG",listTrendingPeople.get(i).getName());
                        }
                        updateTrendingPeople();
                    }else{
                        Log.d("listTrendingPeople TAG\n","listTrendingPeople empty");
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMovieClick(Movie movie) {
        selectedMovie=movie;
        Intent intent=new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(StringConstants.movieDetailDataKey,selectedMovie.getID());
        startActivity(intent);
    }

    @Override
    public void onPersonClick(Person p) {
        selectedPerson=p;
    }

    @Override
    public void onCastClick(Cast cast) {

    }
}