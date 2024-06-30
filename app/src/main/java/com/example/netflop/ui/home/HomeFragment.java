package com.example.netflop.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.people.Cast;
import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.data.models.remote.people.Person;
import com.example.netflop.data.models.remote.TVs.AiringTodayModel;
import com.example.netflop.data.responses.movies.NowPlayingResponse;
import com.example.netflop.data.responses.movies.PopularResponse;
import com.example.netflop.data.responses.movies.TopRatedResponse;
import com.example.netflop.data.responses.movies.TrendingMovieResponse;
import com.example.netflop.data.responses.people.TrendingPeopleResponse;
import com.example.netflop.data.responses.movies.UpcomingResponse;
import com.example.netflop.databinding.FragmentHomeBinding;
import com.example.netflop.helpers.NoInternetToastHelpers;
import com.example.netflop.ui.TV_Detail.TVSeriesDetailActivity;
import com.example.netflop.ui.adapters.remote.HorizontalShimmerAdapter;
import com.example.netflop.ui.adapters.remote.ListMovieAdapter;
import com.example.netflop.ui.adapters.remote.ListPersonAdapter;
import com.example.netflop.ui.adapters.remote.ListTVAdapter;
import com.example.netflop.ui.adapters.remote.SecondListMovieAdapter;
import com.example.netflop.ui.adapters.remote.VerticalShimmerAdapter;
import com.example.netflop.ui.base.BaseFragment;
import com.example.netflop.ui.movie_detail.MovieDetailActivity;
import com.example.netflop.ui.person_detail.PersonDetailActivity;
import com.example.netflop.utils.listeners.ItemTVOnClickListener;
import com.example.netflop.utils.listeners.ItemTouchHelperAdapter;
import com.example.netflop.utils.SeeMoreOnClickListener;
import com.example.netflop.utils.SpacingItemDecorator;
import com.example.netflop.viewmodel.connectivity.ConnectivityViewModel;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;
import com.example.netflop.viewmodel.remote.NowPlayingViewModel;
import com.example.netflop.viewmodel.remote.PopularMovieViewModel;
import com.example.netflop.viewmodel.remote.PopularPeopleViewModel;
import com.example.netflop.viewmodel.remote.PopularTVViewModel;
import com.example.netflop.viewmodel.remote.TopRatedTVViewModel;
import com.example.netflop.viewmodel.remote.TopRatedViewModel;
import com.example.netflop.viewmodel.remote.TrendingMovieViewModel;
import com.example.netflop.viewmodel.remote.TrendingPeopleViewModel;
import com.example.netflop.viewmodel.remote.UpcomingViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends BaseFragment implements ItemTouchHelperAdapter, ItemTVOnClickListener {

    private FragmentHomeBinding binding;

    // view models
    private NowPlayingViewModel nowPlayingViewModel;
    private PopularMovieViewModel popularMovieViewModel;
    private TopRatedViewModel topRatedViewModel;
    private  UpcomingViewModel upcomingViewModel;
    private TrendingMovieViewModel trendingMovieViewModel;
    private TrendingPeopleViewModel trendingPeopleViewModel;
    private PopularPeopleViewModel popularPeopleViewModel;
    private TopRatedTVViewModel topRatedTVViewModel;
    private PopularTVViewModel popularTVViewModel;
    private FavouriteMediaViewModel favouriteMediaViewModel;
    private ConnectivityViewModel connectivityViewModel;

    // lists data
    List<Movie> listNowPlaying;
    List<Movie> listPopularMovie;
    List<Movie> listTopRatedMovie;
    List<Movie> listTrendingMovie;
    List<Movie> listUpcomingMovie;
    List<Person> listTrendingPeople;
    List<Person> listPopularPeople;
    List<AiringTodayModel> listPopularTV,listTopRatedTV;

    // adapters variables

    ListMovieAdapter playingNowAdapter,popularMovieAdapter,trendingMovieAdapter;

    SecondListMovieAdapter upcomingAdapter,topRatedAdapter;

    ListPersonAdapter trendingPersonAdapter,popularPeopleAdapter;
    ListTVAdapter popularTVAdapter,topRatedTVAdapter;

    // shimmer variables
    VerticalShimmerAdapter playingNowShimmerAdapter,popularMovieShimmerAdapter,trendingMovieShimmerAdapter,trendingPeopleShimmerAdapter,popularPeopleShimmerAdapter,popularTVShimmerAdapter,topRatedTVShimmerAdapter;
    HorizontalShimmerAdapter upcomingShimmerAdapter,topRatedShimmerAdapter;


    // selected variables

    Movie selectedMovie;
    Person selectedPerson;

    // ui

    RecyclerView playingNowRecyclerView,popularMovieRecyclerView,topRatedRecyclerView,trendingMovieRecyclerView,trendingPeopleRecyclerView,upcomingRecyclerView,popularPeopleRecyclerView,popularTVRecyclerView,topRatedTVRecyclerView;

    RecyclerView playingNowShimmerRecyclerView,popularMovieShimmerRecyclerView,topRatedShimmerRecyclerView,trendingMovieShimmerRecyclerView,trendingPeopleShimmerRecyclerView,upcomingShimmerRecyclerView,popularPeopleShimmerRecyclerView,popularTVShimmerRecyclerView,topRatedTVShimmerRecyclerView;
    TextView topRated,upcoming,trendingMovie,trendingPeople,nowPlaying,popularMovie,popularPeople,popularTVTextView,topRatedTVTextView;
    TextView seeMoreTrendingMovie,seeMorePopularMovie,seeMoreUpcoming,seeMoreNowPlayingMovie,seeMoreTrendingPeople,seeMoreTopRatedMovie,seeMorePopularPeople,seeMorePopularTVTextView,seeMoreTopRatedTVTextView;
    SwipeRefreshLayout swipeRefreshLayout;
    ConstraintLayout noInternetDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding();
        init();
        callAPIs();
        observeChanges();
        fetchFavouriteData();
        observeFavouriteDataChange();
        seeMoreListeners();
        onHandleRefresh();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void binding(){
        playingNowRecyclerView=binding.recyclerViewPlayingNow;
        trendingMovieRecyclerView=binding.recyclerViewTrendingMovie;
        popularMovieRecyclerView=binding.recyclerViewPopularMovie;
        topRatedRecyclerView=binding.recyclerViewTopRatedMovie;
        upcomingRecyclerView=binding.recyclerViewUpcomingMovie;
        trendingPeopleRecyclerView=binding.recyclerViewTrendingPeople;
        popularPeopleRecyclerView=binding.recyclerViewPopularPeople;
        popularTVRecyclerView=binding.recyclerViewPopularTV;
        topRatedTVRecyclerView=binding.recyclerViewTopRatedTV;

        nowPlaying=binding.listNowPlayingID;
        trendingMovie=binding.listTrendingMovieID;
        topRated=binding.listTopRatedMovieID;
        popularMovie=binding.listPopularMovieID;
        upcoming=binding.listUpcomingID;
        trendingPeople=binding.listTrendingPeopleID;
        popularPeople=binding.listPopularPeopleID;
        popularTVTextView=binding.listPopularTVID;
        topRatedTVTextView=binding.listTopRatedTVID;

        // see more
        seeMoreTrendingMovie=binding.seeMoreTrendingMovie;
        seeMoreNowPlayingMovie=binding.seeMoreNowPlayingMovie;
        seeMorePopularMovie=binding.seeMorePopularMovie;
        seeMoreTopRatedMovie=binding.seeMoreTopRatedMovie;
        seeMoreTrendingPeople=binding.seeMoreTrendingPeople;
        seeMoreUpcoming=binding.seeMoreUpcomingMovie;
        seeMorePopularPeople=binding.seeMorePopularPeople;
        seeMorePopularTVTextView=binding.seeMorePopularTV;
        seeMoreTopRatedTVTextView=binding.seeMoreTopRatedTV;

        // shimmer
        playingNowShimmerRecyclerView=binding.recyclerViewPlayingNowShimmer;
        popularMovieShimmerRecyclerView=binding.recyclerViewPopularMovieShimmer;
        topRatedShimmerRecyclerView=binding.recyclerViewTopRatedMovieShimmer;
        trendingMovieShimmerRecyclerView=binding.recyclerViewTrendingMovieShimmer;
        trendingPeopleShimmerRecyclerView=binding.recyclerViewTrendingPeopleShimmer;
        upcomingShimmerRecyclerView=binding.recyclerViewUpcomingMovieShimmer;
        popularPeopleShimmerRecyclerView=binding.recyclerViewPopularPeopleShimmer;
        popularTVShimmerRecyclerView=binding.recyclerViewPopularTVShimmer;
        topRatedTVShimmerRecyclerView=binding.recyclerViewTopRatedTVShimmer;
        swipeRefreshLayout=binding.swipeRefreshHome;

    }
    private void init(){
        initializeDataUI();
        initializeViewModels();
        initializeListData();
        initializeAdapter();
        initializeRecyclerView();
    }
    private void seeMoreListeners(){
        // trending movie
        SeeMoreOnClickListener.getSeeMoreOnClick(seeMoreTrendingMovie,() -> {
            Intent intent=new Intent(getActivity(), AllTrendingMovieActivity.class);
            startActivity(intent);
        });
        // trending people
        SeeMoreOnClickListener.getSeeMoreOnClick(seeMoreTrendingPeople,() -> {
            Intent intent=new Intent(getActivity(), AllTrendingPeopleActivity.class);
            startActivity(intent);
        });
        // upcoming
        SeeMoreOnClickListener.getSeeMoreOnClick(seeMoreUpcoming,() -> {
            Intent intent=new Intent(getActivity(), AllUpcomingActivity.class);
            startActivity(intent);
        });
        // popular movie
        SeeMoreOnClickListener.getSeeMoreOnClick(seeMorePopularMovie,() -> {
            Intent intent=new Intent(getActivity(), AllPopularMovieActivity.class);
            startActivity(intent);
        });
        // top rated
        SeeMoreOnClickListener.getSeeMoreOnClick(seeMoreTopRatedMovie,() -> {
            Intent intent=new Intent(getActivity(), AllTopRatedMovieActivity.class);
            startActivity(intent);
        });
        // now playing
        SeeMoreOnClickListener.getSeeMoreOnClick(seeMoreNowPlayingMovie,() -> {
            Intent intent=new Intent(getActivity(), AllPlayingNowActivity.class);
            startActivity(intent);
        });
        //popular people
        SeeMoreOnClickListener.getSeeMoreOnClick(seeMorePopularPeople,() -> {
            Intent intent=new Intent(getActivity(), AllPopularPeopleActivity.class);
            startActivity(intent);
        });
        //popular TV
        SeeMoreOnClickListener.getSeeMoreOnClick(seeMorePopularTVTextView,() -> {
            Intent intent=new Intent(getActivity(), AllPopularTVActivity.class);
            startActivity(intent);
        });
        // top rated TV
        SeeMoreOnClickListener.getSeeMoreOnClick(seeMoreTopRatedTVTextView,() -> {
            Intent intent=new Intent(getActivity(), AllTopRatedTVActivity.class);
            startActivity(intent);
        });
    }
    private void initializeDataUI(){
        nowPlaying.setText("Now playing");
        trendingPeople.setText("Trending people");
        trendingMovie.setText("Trending movies");
        topRated.setText("Top rated");
        popularMovie.setText("Popular movies");
        upcoming.setText("Upcoming");
        popularPeople.setText("Popular people");
        popularTVTextView.setText("Popular TV series");
        topRatedTVTextView.setText("Top rated TV series");
    }
    private void initializeAdapter(){
        playingNowAdapter= new ListMovieAdapter(listNowPlaying,requireContext(), R.layout.single_movie_card,this,favouriteMediaViewModel);
        popularMovieAdapter= new ListMovieAdapter(listPopularMovie,requireContext(), R.layout.single_movie_card,this,favouriteMediaViewModel);
        trendingMovieAdapter= new ListMovieAdapter(listTrendingMovie,requireContext(), R.layout.single_movie_card,this,favouriteMediaViewModel);
        upcomingAdapter= new SecondListMovieAdapter(listUpcomingMovie,requireContext(), R.layout.a_movie_card,this,favouriteMediaViewModel);
        topRatedAdapter= new SecondListMovieAdapter(listTopRatedMovie,requireContext(), R.layout.a_movie_card,this,favouriteMediaViewModel);
        trendingPersonAdapter=new ListPersonAdapter(listTrendingPeople,requireContext(),R.layout.single_person_card,this,favouriteMediaViewModel);
        popularPeopleAdapter=new ListPersonAdapter(listPopularPeople,requireContext(),R.layout.single_person_card,this,favouriteMediaViewModel);
        popularTVAdapter=new ListTVAdapter(listPopularTV,requireContext(),this,favouriteMediaViewModel);
        topRatedTVAdapter=new ListTVAdapter(listTopRatedTV,requireContext(),this,favouriteMediaViewModel);

        playingNowShimmerAdapter=new VerticalShimmerAdapter(requireContext());
        popularMovieShimmerAdapter=new VerticalShimmerAdapter(requireContext());
        topRatedShimmerAdapter=new HorizontalShimmerAdapter(requireContext());
        trendingMovieShimmerAdapter=new VerticalShimmerAdapter(requireContext());
        trendingPeopleShimmerAdapter=new VerticalShimmerAdapter(requireContext());
        upcomingShimmerAdapter=new HorizontalShimmerAdapter(requireContext());
        popularPeopleShimmerAdapter=new VerticalShimmerAdapter(requireContext());
        popularTVShimmerAdapter=new VerticalShimmerAdapter(requireContext());
        topRatedTVShimmerAdapter =new VerticalShimmerAdapter(requireContext());
    }

    private void initializeRecyclerView(){


        playingNowRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        popularMovieRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        topRatedRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingMovieRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingPeopleRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        popularPeopleRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        popularTVRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        topRatedTVRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        playingNowShimmerRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        popularMovieShimmerRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        topRatedShimmerRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingMovieShimmerRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        trendingPeopleShimmerRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        upcomingShimmerRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        popularPeopleShimmerRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        popularTVShimmerRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        topRatedTVShimmerRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        // decoration
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(10,25);
        //
        playingNowRecyclerView.addItemDecoration(itemDecorator);
        popularMovieRecyclerView.addItemDecoration(itemDecorator);
        topRatedRecyclerView.addItemDecoration(itemDecorator);
        trendingMovieRecyclerView.addItemDecoration(itemDecorator);
        trendingPeopleRecyclerView.addItemDecoration(itemDecorator);
        upcomingRecyclerView.addItemDecoration(itemDecorator);
        popularPeopleRecyclerView.addItemDecoration(itemDecorator);
        popularTVRecyclerView.addItemDecoration(itemDecorator);
        topRatedTVRecyclerView.addItemDecoration(itemDecorator);

        playingNowShimmerRecyclerView.addItemDecoration(itemDecorator);
        popularMovieShimmerRecyclerView.addItemDecoration(itemDecorator);
        topRatedShimmerRecyclerView.addItemDecoration(itemDecorator);
        trendingMovieShimmerRecyclerView.addItemDecoration(itemDecorator);
        trendingPeopleShimmerRecyclerView.addItemDecoration(itemDecorator);
        upcomingShimmerRecyclerView.addItemDecoration(itemDecorator);
        popularPeopleShimmerRecyclerView.addItemDecoration(itemDecorator);
        popularTVShimmerRecyclerView.addItemDecoration(itemDecorator);
        topRatedTVShimmerRecyclerView.addItemDecoration(itemDecorator);

        setAdapterForRecyclerView();
        setAdapterShimmerForRecyclerView();



    }
    private void setAdapterShimmerForRecyclerView(){
        // make visible
        playingNowShimmerRecyclerView.setVisibility(View.VISIBLE);
        popularMovieShimmerRecyclerView.setVisibility(View.VISIBLE);
        topRatedShimmerRecyclerView.setVisibility(View.VISIBLE);
        trendingMovieShimmerRecyclerView.setVisibility(View.VISIBLE);
        trendingPeopleShimmerRecyclerView.setVisibility(View.VISIBLE);
        upcomingShimmerRecyclerView.setVisibility(View.VISIBLE);
        popularPeopleShimmerRecyclerView.setVisibility(View.VISIBLE);
        popularTVShimmerRecyclerView.setVisibility(View.VISIBLE);
        topRatedTVShimmerRecyclerView.setVisibility(View.VISIBLE);

        playingNowShimmerRecyclerView.setAdapter(playingNowShimmerAdapter);
        popularMovieShimmerRecyclerView.setAdapter(popularMovieShimmerAdapter);
        topRatedShimmerRecyclerView.setAdapter(topRatedShimmerAdapter);
        trendingMovieShimmerRecyclerView.setAdapter(trendingMovieShimmerAdapter);
        trendingPeopleShimmerRecyclerView.setAdapter(trendingPeopleShimmerAdapter);
        upcomingShimmerRecyclerView.setAdapter(upcomingShimmerAdapter);
        popularPeopleShimmerRecyclerView.setAdapter(popularPeopleShimmerAdapter);
        popularTVShimmerRecyclerView.setAdapter(popularTVShimmerAdapter);
        topRatedTVShimmerRecyclerView.setAdapter(topRatedTVShimmerAdapter);
    }
    private void setAdapterForRecyclerView(){

        // make gone
        playingNowRecyclerView.setVisibility(View.GONE);
        topRatedRecyclerView.setVisibility(View.GONE);
        upcomingRecyclerView.setVisibility(View.GONE);
        popularMovieRecyclerView.setVisibility(View.GONE);
        trendingMovieRecyclerView.setVisibility(View.GONE);
        trendingPeopleRecyclerView.setVisibility(View.GONE);
        popularPeopleRecyclerView.setVisibility(View.GONE);
        popularTVRecyclerView.setVisibility(View.GONE);
        topRatedTVRecyclerView.setVisibility(View.GONE);


        playingNowRecyclerView.setAdapter(playingNowAdapter);
        topRatedRecyclerView.setAdapter(topRatedAdapter);
        upcomingRecyclerView.setAdapter(upcomingAdapter);
        popularMovieRecyclerView.setAdapter(popularMovieAdapter);
        trendingMovieRecyclerView.setAdapter(trendingMovieAdapter);
        trendingPeopleRecyclerView.setAdapter(trendingPersonAdapter);
        popularPeopleRecyclerView.setAdapter(popularPeopleAdapter);
        popularTVRecyclerView.setAdapter(popularTVAdapter);
        topRatedTVRecyclerView.setAdapter(topRatedTVAdapter);
    }

    private void initializeViewModels(){
        nowPlayingViewModel=new ViewModelProvider(this).get(NowPlayingViewModel.class);
        popularMovieViewModel=new ViewModelProvider(this).get(PopularMovieViewModel.class);
        topRatedViewModel=new ViewModelProvider(this).get(TopRatedViewModel.class);
        upcomingViewModel=new ViewModelProvider(this).get(UpcomingViewModel.class);
        trendingMovieViewModel=new ViewModelProvider(this).get(TrendingMovieViewModel.class);
        trendingPeopleViewModel=new ViewModelProvider(this).get(TrendingPeopleViewModel.class);
        popularPeopleViewModel=new ViewModelProvider(this).get(PopularPeopleViewModel.class);
        topRatedTVViewModel=new ViewModelProvider(this).get(TopRatedTVViewModel.class);
        popularTVViewModel=new ViewModelProvider(this).get(PopularTVViewModel.class);
        favouriteMediaViewModel=new ViewModelProvider(this).get(FavouriteMediaViewModel.class);
        connectivityViewModel=new ViewModelProvider(this).get(ConnectivityViewModel.class);
    }
    private void initializeListData(){
        listNowPlaying=new ArrayList<>();
        listPopularMovie=new ArrayList<>();
        listTrendingMovie=new ArrayList<>();
        listUpcomingMovie=new ArrayList<>();
        listTopRatedMovie=new ArrayList<>();
        listTrendingPeople=new ArrayList<>();
        listPopularPeople=new ArrayList<>();
        listPopularTV=new ArrayList<>();
        listTopRatedTV=new ArrayList<>();
    }

    // update functions
    private void updatePlayingNow(){
        playingNowAdapter= new ListMovieAdapter(listNowPlaying,requireContext(), R.layout.single_movie_card,this,favouriteMediaViewModel);
        playingNowRecyclerView.setAdapter(playingNowAdapter);
    }
    private void updateTrendingMovie(){
        trendingMovieAdapter= new ListMovieAdapter(listTrendingMovie,requireContext(), R.layout.single_movie_card,this,favouriteMediaViewModel);
        trendingMovieRecyclerView.setAdapter(trendingMovieAdapter);
    }
    private void updateTrendingPeople(){
        trendingPersonAdapter=new ListPersonAdapter(listTrendingPeople,requireContext(),R.layout.single_person_card,this,favouriteMediaViewModel);
        trendingPeopleRecyclerView.setAdapter(trendingPersonAdapter);
    }
    private void updatePopularPeople(){
        popularPeopleAdapter=new ListPersonAdapter(listPopularPeople,requireContext(),R.layout.single_person_card,this,favouriteMediaViewModel);
        popularPeopleRecyclerView.setAdapter(popularPeopleAdapter);
    }
    private void updateTopRated(){
        topRatedAdapter= new SecondListMovieAdapter(listTopRatedMovie,requireContext(), R.layout.a_movie_card,this,favouriteMediaViewModel);
        topRatedRecyclerView.setAdapter(topRatedAdapter);
    }
    private void updatePopularMovie(){
        popularMovieAdapter= new ListMovieAdapter(listPopularMovie,requireContext(), R.layout.single_movie_card,this,favouriteMediaViewModel);
        popularMovieRecyclerView.setAdapter(popularMovieAdapter);
    }
    private void updateUpcomingMovie(){
        upcomingAdapter= new SecondListMovieAdapter(listUpcomingMovie,requireContext(), R.layout.a_movie_card,this,favouriteMediaViewModel);
        upcomingRecyclerView.setAdapter(upcomingAdapter);
    }
    private void updatePopularTV(){
        popularTVAdapter=new ListTVAdapter(listPopularTV,requireContext(),this,favouriteMediaViewModel);
        popularTVRecyclerView.setAdapter(popularTVAdapter);
    }
    private void updateTopRatedTV(){
        topRatedTVAdapter=new ListTVAdapter(listTopRatedTV,requireContext(),this,favouriteMediaViewModel);
        topRatedTVRecyclerView.setAdapter(topRatedTVAdapter);
    }



    private void callAPIs(){
        ExecutorService executor = Executors.newFixedThreadPool(4);
        nowPlayingViewModel.fetchNowPlaying(getViewLifecycleOwner());
        popularMovieViewModel.fetchPopularMovie(getViewLifecycleOwner());
        topRatedViewModel.fetchTopRated(getViewLifecycleOwner());
        upcomingViewModel.fetchUpcomingMovie(getViewLifecycleOwner());
        trendingMovieViewModel.fetchTrendingMovie(getViewLifecycleOwner());
        trendingPeopleViewModel.fetchTrendingPeople(getViewLifecycleOwner());
        popularPeopleViewModel.fetchPopularPeople(getViewLifecycleOwner());
        popularTVViewModel.fetchPopularTV(getViewLifecycleOwner());
        topRatedTVViewModel.fetchTopRatedTV(getViewLifecycleOwner());
        executor.shutdown();
    }
    private void observeChanges(){
        // now playing
        nowPlayingViewModel.getNowPlayingData().observe(getViewLifecycleOwner(), new Observer<NowPlayingResponse>() {
            @Override
            public void onChanged(NowPlayingResponse nowPlayingResponse) {
                playingNowShimmerRecyclerView.setVisibility(View.GONE);
                playingNowRecyclerView.setVisibility(View.VISIBLE);
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
              topRatedShimmerRecyclerView.setVisibility(View.GONE);
              topRatedRecyclerView.setVisibility(View.VISIBLE);
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
                popularMovieShimmerRecyclerView.setVisibility(View.GONE);
                popularMovieRecyclerView.setVisibility(View.VISIBLE);
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
                upcomingShimmerRecyclerView.setVisibility(View.GONE);
                upcomingRecyclerView.setVisibility(View.VISIBLE);
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
                trendingMovieShimmerRecyclerView.setVisibility(View.GONE);
                trendingMovieRecyclerView.setVisibility(View.VISIBLE);
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
                trendingPeopleShimmerRecyclerView.setVisibility(View.GONE);
                trendingPeopleRecyclerView.setVisibility(View.VISIBLE);
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

        // popular people
        popularPeopleViewModel.getListPeopleData().observe(getViewLifecycleOwner(), new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                popularPeopleShimmerRecyclerView.setVisibility(View.GONE);
                popularPeopleRecyclerView.setVisibility(View.VISIBLE);
                if(people!=null){
                    if(!people.isEmpty()&&people!=null){
                        listPopularPeople=people;
                        Log.d("listPopularPeople TAG\n","START");
                        for (int i = 0; i < listPopularPeople.size(); i++) {
                            Log.d("TAG",listPopularPeople.get(i).getName());
                        }
                        updatePopularPeople();
                    }else{
                        Log.d("listPopularPeople TAG\n","listPopularPeople empty");
                    }
                }
            }
        });
        //popular TV
        popularTVViewModel.getListPopularTV().observe(getViewLifecycleOwner(), new Observer<List<AiringTodayModel>>() {
            @Override
            public void onChanged(List<AiringTodayModel> airingTodayModels) {
                popularTVShimmerRecyclerView.setVisibility(View.GONE);
                popularTVRecyclerView.setVisibility(View.VISIBLE);
                if(airingTodayModels!=null){
                    if(!airingTodayModels.isEmpty()&&airingTodayModels!=null){
                        listPopularTV=airingTodayModels;
                        Log.d("listPopularTV TAG\n","START");
                        for (int i = 0; i < listPopularTV.size(); i++) {
                            Log.d("TAG",listPopularTV.get(i).getName());
                        }
                        updatePopularTV();
                    }else{
                        Log.d("listPopularTV TAG\n","listPopularTV empty");
                    }
                }
            }
        });

        // top rated TV
        topRatedTVViewModel.getListTopRatedTV().observe(getViewLifecycleOwner(), new Observer<List<AiringTodayModel>>() {
            @Override
            public void onChanged(List<AiringTodayModel> airingTodayModels) {
                topRatedTVShimmerRecyclerView.setVisibility(View.GONE);
                topRatedTVRecyclerView.setVisibility(View.VISIBLE);
                if(airingTodayModels!=null){
                    if(!airingTodayModels.isEmpty()&&airingTodayModels!=null){
                        listTopRatedTV=airingTodayModels;
                        Log.d("listTopRatedTV TAG\n","START");
                        for (int i = 0; i < listTopRatedTV.size(); i++) {
                            Log.d("TAG",listTopRatedTV.get(i).getName());
                        }
                        updateTopRatedTV();
                    }else{
                        Log.d("listTopRatedTV TAG\n","listTopRatedTV empty");
                    }
                }
            }
        });
    }
    private void fetchFavouriteData(){
        favouriteMediaViewModel.fetchData();
    }

    private  void observeFavouriteDataChange(){
        favouriteMediaViewModel.getFavouriteMovies().observe(getViewLifecycleOwner(), new Observer<List<FavouriteMedia>>() {
            @Override
            public void onChanged(List<FavouriteMedia> favouriteMedia) {
//                playingNowAdapter.setFavouriteData(favouriteMedia);
//                popularMovieAdapter.setFavouriteData(favouriteMedia);
//                trendingMovieAdapter.setFavouriteData(favouriteMedia);
//                upcomingAdapter.setFavouriteData(favouriteMedia);
//                topRatedAdapter.setFavouriteData(favouriteMedia);
//                updatePlayingNow();
//                updateTopRated();
//                updatePopularMovie();
//                updateUpcomingMovie();
//                updateTrendingMovie();
//                playingNowAdapter.notifyDataSetChanged();
//                popularMovieAdapter.notifyDataSetChanged();
//                trendingMovieAdapter.notifyDataSetChanged();
//                upcomingAdapter.notifyDataSetChanged();
//                topRatedAdapter.notifyDataSetChanged();
//
//
//                upcomingRecyclerView.setAdapter(upcomingAdapter);
//                topRatedRecyclerView.setAdapter(topRatedAdapter);
//                popularMovieRecyclerView.setAdapter(popularMovieAdapter);
//                trendingMovieRecyclerView.setAdapter(trendingMovieAdapter);
//                playingNowRecyclerView.setAdapter(playingNowAdapter);

            }
        });
        favouriteMediaViewModel.getFavouritePeople().observe(getViewLifecycleOwner(), new Observer<List<FavouriteMedia>>() {
            @Override
            public void onChanged(List<FavouriteMedia> favouriteMedia) {
//                trendingPersonAdapter.setFavouriteData(favouriteMedia);
//                trendingPersonAdapter.notifyDataSetChanged();
//                popularPeopleAdapter.setFavouriteData(favouriteMedia);
//                popularPeopleAdapter.notifyDataSetChanged();
//
//                trendingPeopleRecyclerView.setAdapter(trendingPersonAdapter);
//                popularPeopleRecyclerView.setAdapter(popularPeopleAdapter);
//                updatePopularPeople();
//                updateTrendingPeople();
            }
        });
        favouriteMediaViewModel.getFavouriteTVSeries().observe(getViewLifecycleOwner(), new Observer<List<FavouriteMedia>>() {
            @Override
            public void onChanged(List<FavouriteMedia> favouriteMedia) {
//                popularTVAdapter.setFavouriteData(favouriteMedia);
//                topRatedTVAdapter.setFavouriteData(favouriteMedia);
//                popularTVAdapter.notifyDataSetChanged();
//                topRatedTVAdapter.notifyDataSetChanged();
//                popularTVRecyclerView.setAdapter(popularTVAdapter);
//                topRatedTVRecyclerView.setAdapter(topRatedTVAdapter);
//                updatePopularTV();
//                updateTopRatedTV();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchFavouriteData();
    }

    @Override
    public void onMovieClick(Movie movie) {
        if(connectivityViewModel.getState()){
            selectedMovie=movie;
            Intent intent=new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra(StringConstants.movieDetailPageDataKey,selectedMovie.getID());
            startActivity(intent);
        }else{
            NoInternetToastHelpers.show(getActivity());
        }

    }

    @Override
    public void onPersonClick(Person p) {
        if(connectivityViewModel.getState()){
            selectedPerson=p;
            Intent intent=new Intent(getActivity(), PersonDetailActivity.class);
            intent.putExtra(StringConstants.personDetailDataKey,selectedPerson.getID());
            startActivity(intent);
        }else{
            NoInternetToastHelpers.show(getActivity());
        }

    }

    @Override
    public void onCastClick(Cast cast) {

    }

    @Override
    public void onTVCLick(AiringTodayModel airingTodayModel) {
        if(connectivityViewModel.getState()){
            Intent intent=new Intent(getActivity(), TVSeriesDetailActivity.class);
            intent.putExtra(StringConstants.tvSeriesIDKey,airingTodayModel.getId());
            startActivity(intent);
        }else{
            NoInternetToastHelpers.show(getActivity());
        }

    }
    private void onHandleRefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData() {
        // Đoạn code để làm mới dữ liệu (ví dụ: gọi API, cập nhật dữ liệu...)
        // Sau khi làm mới xong, bạn cần gọi lại hàm setRefreshing(false) để dừng hoạt động làm mới
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                callAPIs();
                fetchFavouriteData();
            }
        }, 2000);
    }

}