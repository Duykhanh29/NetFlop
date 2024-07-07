package com.example.netflop.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflop.constants.IntConstants;
import com.example.netflop.constants.RequestCode;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.enums.SearchType;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.people.Cast;
import com.example.netflop.data.models.remote.movies.Movie;
import com.example.netflop.data.models.remote.people.Person;
import com.example.netflop.data.models.remote.movies.SearchMultiModel;
import com.example.netflop.data.models.remote.TVs.AiringTodayModel;
import com.example.netflop.data.models.local.SearchHistory;
import com.example.netflop.databinding.FragmentSearchBinding;
import com.example.netflop.helpers.NoInternetToastHelpers;
import com.example.netflop.ui.TV_Detail.TVSeriesDetailActivity;
import com.example.netflop.ui.adapters.remote.SearchAdapter;
import com.example.netflop.ui.adapters.remote.SearchMovieAdapter;
import com.example.netflop.ui.adapters.remote.SearchPersonAdapter;
import com.example.netflop.ui.adapters.remote.SearchTVAdapter;
import com.example.netflop.ui.adapters.local.SearchHistoryAdapter;
import com.example.netflop.ui.base.BaseFragment;
import com.example.netflop.ui.movie_detail.MovieDetailActivity;
import com.example.netflop.ui.person_detail.PersonDetailActivity;
import com.example.netflop.utils.SeeMoreOnClickListener;
import com.example.netflop.utils.listeners.ItemTVOnClickListener;
import com.example.netflop.utils.listeners.ItemTouchHelperAdapter;
import com.example.netflop.utils.listeners.OnSearchHistoryListener;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.utils.listeners.SearchItemOnClickListener;
import com.example.netflop.viewmodel.connectivity.ConnectivityViewModel;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;
import com.example.netflop.viewmodel.local.SearchHistoryViewModel;
import com.example.netflop.viewmodel.remote.SearchMovieViewModel;
import com.example.netflop.viewmodel.remote.SearchMultiViewModel;
import com.example.netflop.viewmodel.remote.SearchPeopleViewModel;
import com.example.netflop.viewmodel.remote.SearchTVViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchFragment extends BaseFragment implements SearchItemOnClickListener, ItemTouchHelperAdapter, ItemTVOnClickListener, OnSearchHistoryListener {

    private FragmentSearchBinding binding;


    //view model
    SearchMultiViewModel searchMultiViewModel;
    SearchPeopleViewModel searchPeopleViewModel;
    SearchMovieViewModel searchMovieViewModel;
    SearchTVViewModel searchTVViewModel;
    SearchHistoryViewModel searchHistoryViewModel;
    FavouriteMediaViewModel favouriteMediaViewModel;
    ConnectivityViewModel connectivityViewModel;

    // lists
    List<SearchMultiModel> searchMultiList;
    List<Movie> listMovie;
    List<Person> listPerson;
    List<AiringTodayModel> lisTVShow;
    List<SearchHistory> searchHistoriesData;

    // adapters
    SearchAdapter searchAdapter;
    SearchPersonAdapter searchPersonAdapter;
    SearchMovieAdapter searchMovieAdapter;
    SearchTVAdapter searchTVAdapter;
    SearchHistoryAdapter searchHistoryAdapter;
    // UI
    DrawerLayout drawerLayout;
    TextView typeOfSearchingTV,isAdultTV;
    TextView clearAllHistoryTextView;

    ImageButton filterButton;

    SearchView searchView;
    RecyclerView recyclerView,searchHistoriesRecyclerView;
    boolean isAdult=false;
    boolean isCheckAll=true;
    boolean isPerson=false;
    boolean isMovie=false;
    boolean isTV=false;

    // backup variables for adjusting filter
    boolean isBackupAdult=false;
    boolean isBackupCheckAll=true;
    boolean isBackupPerson=false;
    boolean isBackupMovie=false;
    boolean isBackupTV=false;


    String queryText;
    boolean isFilterForward=false;
    boolean isSearched=false;

    //selected variables
    SearchMultiModel selectedSearchMultiModel;
    Movie selectedMovie;
    Person selectedPerson;
    AiringTodayModel selectedTV;

    LinearLayout nonSearchLayout,searchLayoutView;

    ImageView noSearchDataView;
    ImageView explorationView;
    LinearLayout searchHistoryView;

    private final ActivityResultLauncher<Intent>  launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == getActivity().RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
//                        isAdult = data.getBooleanExtra(StringConstants.isAdultKey, false);
                         isCheckAll = data.getBooleanExtra(StringConstants.isCheckAllKey, false);
                         isPerson = data.getBooleanExtra(StringConstants.isPersonKey, false);
                         isMovie = data.getBooleanExtra(StringConstants.isMovieKey, false);
                        isTV = data.getBooleanExtra(StringConstants.isTVKey, false);
                        Log.d("data", "isAdult: " + isAdult);
                        Log.d("data", "isCheckAll: " + isCheckAll);
                        Log.d("data", "isPerson: " + isPerson);
                        Log.d("data", "isMovie: " + isMovie);
                        Log.d("data", "isTV: " + isTV);

                        onBackupHandle();

                    }
                }
                if(!queryText.isEmpty()){
                    searchView.setQuery(queryText,false);
                    searchView.requestFocus();
                }
                isFilterForward=false;

            }
    );
    private void onBackupHandle(){
        boolean shouldRefresh = false;

        if(isAdult!= isBackupAdult){
            isBackupAdult = isAdult;
            shouldRefresh = true;
        }
        if(isPerson!= isBackupPerson){
            isBackupPerson = isPerson;
            shouldRefresh = true;
        }
        if(isTV!= isBackupTV){
            isBackupTV = isTV;
            shouldRefresh = true;
        }
        if(isMovie!= isBackupMovie){
            isBackupMovie = isMovie;
            shouldRefresh = true;
        }
        if(isCheckAll!= isBackupCheckAll){
            isBackupCheckAll = isCheckAll;
            shouldRefresh = true;
        }
        Log.d("Test queryText back","queryText back: "+queryText);
        searchView.setQuery(queryText,false);

        if(shouldRefresh &&queryText!=null&& !queryText.isEmpty()){
            refreshSearchResults();
        }
    }

    private void refreshSearchResults(){
        if(isCheckAll){
            initializeMultipleRecyclerView();
            callAPIs(queryText,isAdult);
            setTypeText(SearchType.MULTI);
        } else if(isPerson){
            initializePeopleRecyclerView();
            callPersonAPI(queryText,isAdult);
            setTypeText(SearchType.PEOPLE);
        } else if(isMovie){
            initializeMovieRecyclerView();
            callMovieAPI(queryText,isAdult);
            setTypeText(SearchType.MOVIE);
        } else {
            initializeTVRecyclerView();
            callTVAPI(queryText,isAdult);
           setTypeText(SearchType.TV);
        }
    }
    private void changeBoolWhenSelectHistoryItem(SearchType searchType){
        if(searchType==SearchType.MOVIE){
            isMovie=true;
            isPerson=false;
            isCheckAll=false;
            isTV=false;
        }else if(searchType==SearchType.TV){
            isTV=true;
            isMovie=false;
            isPerson=false;
            isCheckAll=false;
        }else if(searchType==SearchType.PEOPLE){
            isPerson=true;
            isTV=false;
            isMovie=false;
            isCheckAll=false;
        }else{
            isCheckAll=true;
            isPerson=false;
            isTV=false;
            isMovie=false;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getBinding();
        return root;
    }
    private void getBinding(){
        searchView=binding.searchView;
        recyclerView=binding.searchRecyclerView;
        filterButton=binding.filterImageButton;
        typeOfSearchingTV=binding.typeOfSearchingView;
        isAdultTV=binding.isAdultSearchingView;
        noSearchDataView=binding.noSearchDataView;
        nonSearchLayout=binding.noSearchLayoutView;
        searchLayoutView=binding.searchOutputLayout;
        searchHistoriesRecyclerView=binding.searchHistoryRecyclerView;
        explorationView=binding.explorationImage;
        searchHistoryView=binding.searchHistoryView;
        clearAllHistoryTextView=binding.clearAllHistory;
    }

    @Override
    public void onResume() {
        super.onResume();
        initialize();
        initializeSearchView();
        callSearchHistoryData();
        onHandleClearAllClick();
        observeSearchHistoryChanged();
        observeDataChanged();
        getOnBackPressed();
        onFilterButtonHandle();
        onScrollListener();
        observeFavouriteDataChange();
    }

    private void initialize(){
        initializeSearchView();
        searchMultiList=new ArrayList<>();
        listMovie=new ArrayList<>();
        listPerson=new ArrayList<>();
        lisTVShow=new ArrayList<>();
        searchHistoriesData=new ArrayList<>();
        searchMultiViewModel=new ViewModelProvider(this).get(SearchMultiViewModel.class);
        searchPeopleViewModel=new ViewModelProvider(this).get(SearchPeopleViewModel.class);
        searchMovieViewModel=new ViewModelProvider(this).get(SearchMovieViewModel.class);
        searchTVViewModel=new ViewModelProvider(this).get(SearchTVViewModel.class);
        searchHistoryViewModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(SearchHistoryViewModel.class);
        favouriteMediaViewModel=new ViewModelProvider(this).get(FavouriteMediaViewModel.class);
        connectivityViewModel=new ViewModelProvider(this).get(ConnectivityViewModel.class);

        searchPersonAdapter=new SearchPersonAdapter(listPerson,getActivity(),this);
        searchMovieAdapter=new SearchMovieAdapter(listMovie,getActivity(),this);
        searchAdapter=new SearchAdapter(searchMultiList,getActivity(),this);
        searchTVAdapter=new SearchTVAdapter(lisTVShow,getActivity(),this,favouriteMediaViewModel);
        searchHistoryAdapter=new SearchHistoryAdapter(getActivity(),searchHistoriesData,this,searchHistoryViewModel);

        RecyclerViewUtils.setupHorizontalRecyclerView(getActivity(),searchHistoriesRecyclerView,searchHistoryAdapter,LinearLayout.VERTICAL,false);
        RecyclerViewUtils.setupGridRecyclerView(getActivity(),recyclerView,searchAdapter,2);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }
    private void initializeMultipleRecyclerView(){
        clearCurrentAdapterData();
        listMovie=new ArrayList<>();
        listPerson=new ArrayList<>();
        lisTVShow=new ArrayList<>();
        searchAdapter=new SearchAdapter(searchMultiList,getActivity(),this);
        recyclerView.setAdapter(searchAdapter);
    }
    private void initializePeopleRecyclerView(){
        clearCurrentAdapterData();
        searchMultiList=new ArrayList<>();
        listMovie=new ArrayList<>();
        lisTVShow=new ArrayList<>();
        searchPersonAdapter=new SearchPersonAdapter(listPerson,getActivity(),this);
        recyclerView.setAdapter(searchPersonAdapter);
    }
    private void initializeMovieRecyclerView(){
        clearCurrentAdapterData();
        searchMultiList=new ArrayList<>();
        listPerson=new ArrayList<>();
        lisTVShow=new ArrayList<>();
        searchMovieAdapter=new SearchMovieAdapter(listMovie,getActivity(),this);
        recyclerView.setAdapter(searchMovieAdapter);
    }
    private void initializeTVRecyclerView(){
        clearCurrentAdapterData();
        searchMultiList=new ArrayList<>();
        listPerson=new ArrayList<>();
        listMovie=new ArrayList<>();
        searchTVAdapter=new SearchTVAdapter(lisTVShow,getActivity(),this,favouriteMediaViewModel);
        recyclerView.setAdapter(searchTVAdapter);
    }

    private void clearCurrentAdapterData(){
        RecyclerView.Adapter currentAdapter = recyclerView.getAdapter();
        if (currentAdapter instanceof SearchMovieAdapter) {
            ((SearchMovieAdapter) currentAdapter).clearData();
        }else if(currentAdapter instanceof SearchPersonAdapter){
            ((SearchPersonAdapter) currentAdapter).clearData();
        }else if(currentAdapter instanceof SearchAdapter){
            ((SearchAdapter) currentAdapter).clearData();
        }else{
            ((SearchTVAdapter) currentAdapter).clearData();
        }
    }

    private void initializeSearchView(){
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryText=query;
                if(query.isEmpty()){
                    searchView.clearFocus();
                    hideKeyboard();
                    return false;
                }else{
                    if(connectivityViewModel.getState()){
                        if(isCheckAll){
                            searchMultiViewModel.resetCurrentPage();
                            callAPIs(query,isAdult);
                            setTypeText(SearchType.MULTI);
                            setAdultText();
                            searchHistoryViewModel.insertSearchHistory(query,SearchType.MULTI,isAdult?1:0);
                        }else if(isMovie){
                            searchMovieViewModel.resetCurrentPage();
                            setTypeText(SearchType.MOVIE);
                            setAdultText();
                            callMovieAPI(query,isAdult);
                            searchHistoryViewModel.insertSearchHistory(query,SearchType.MOVIE,isAdult?1:0);
                        }else if(isPerson){
                            searchPeopleViewModel.resetCurrentPage();
                            setTypeText(SearchType.PEOPLE);
                            setAdultText();
                            callPersonAPI(query,isAdult);
                            searchHistoryViewModel.insertSearchHistory(query,SearchType.PEOPLE,isAdult?1:0);
                        }else{
                            searchTVViewModel.resetCurrentPage();
                            setTypeText(SearchType.TV);
                            setAdultText();
                            callTVAPI(query,isAdult);
                            searchHistoryViewModel.insertSearchHistory(query,SearchType.TV,isAdult?1:0);
                        }
                        searchView.clearFocus();
                        nonSearchLayout.setVisibility(View.GONE);
                        searchLayoutView.setVisibility(View.VISIBLE);
                        isSearched=true;
                    }else{
                        NoInternetToastHelpers.show(getActivity());
                    }
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                }
                return  false;
            }
        });
    }
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
    }
    private void callAPIs(String searchKey,boolean isAdultValue){
        searchMultiViewModel.searchMultiple(searchKey,isAdultValue,getViewLifecycleOwner());
    }
    private void callPersonAPI(String searchKey,boolean isAdultValue){
        searchPeopleViewModel.searchPeople(searchKey,isAdultValue,getViewLifecycleOwner());
    }
    private void callMovieAPI(String searchKey,boolean isAdultValue){
        searchMovieViewModel.searchMovie(searchKey,isAdultValue,getViewLifecycleOwner());
    }
    private void callTVAPI(String searchKey,boolean isAdultValue){
        searchTVViewModel.searchTVShows(searchKey,isAdultValue,getViewLifecycleOwner());
    }

    private void callSearchHistoryData(){
        searchHistoryViewModel.getData();
    }

    private void observeSearchHistoryChanged(){
        searchHistoryViewModel.getSearchHistories().observe(getViewLifecycleOwner(), searchHistories -> {
            if(searchHistories!=null&&!searchHistories.isEmpty()){
                explorationView.setVisibility(View.GONE);
                searchHistoryView.setVisibility(View.VISIBLE);
                searchHistoriesData.clear();
//                    Collections.reverse(searchHistories);
                if(searchHistories.size()>5){
                    for (int i = 0; i < 5; i++) {
                        searchHistoriesData.add(searchHistories.get(searchHistories.size()-i-1));
                    }
                }else{
                    for (int i = 0; i < searchHistories.size(); i++) {
                        searchHistoriesData.add(searchHistories.get(searchHistories.size()-i-1));
                    }
                }
//                    searchHistoriesData.addAll(searchHistories);
                searchHistoryAdapter.notifyDataSetChanged();
            }else{
                if(explorationView.getVisibility()==View.GONE){
                    searchHistoryView.setVisibility(View.GONE);
                    explorationView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void observeDataChanged(){
        searchMultiViewModel.getListSearchMulti().observe(getViewLifecycleOwner(), searchMultiModels -> {
                if(searchMultiModels!=null&&!searchMultiModels.isEmpty()){
                    if(recyclerView.getVisibility()==View.GONE){
                        noSearchDataView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    searchMultiList.clear();  // Clear the list to ensure no duplicates if needed
                    searchMultiList.addAll(searchMultiModels);
                    searchAdapter.notifyDataSetChanged();
                    if(recyclerView.getAdapter()!=searchAdapter){
                        clearCurrentAdapterData();
                        recyclerView.setAdapter(searchAdapter);
                    }

                }else{
                    searchAdapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.GONE);
                    noSearchDataView.setVisibility(View.VISIBLE);
                }
            });
        searchMovieViewModel.getListMovieData().observe(getViewLifecycleOwner(), movies -> {
                if(movies!=null&&!movies.isEmpty()){
                    if(recyclerView.getVisibility()==View.GONE){
                        noSearchDataView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    listMovie.clear();
                    listMovie.addAll(movies);
                    searchMovieAdapter.notifyDataSetChanged();
                    if(recyclerView.getAdapter()!=searchMovieAdapter){
                        clearCurrentAdapterData();
                        recyclerView.setAdapter(searchMovieAdapter);
                    }
                }else{
                    searchMovieAdapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.GONE);
                    noSearchDataView.setVisibility(View.VISIBLE);
                }
            });

        searchPeopleViewModel.getListPeopleData().observe(getViewLifecycleOwner(), people -> {
                if(people!=null&&!people.isEmpty()){
                    if(recyclerView.getVisibility()==View.GONE){
                        noSearchDataView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    listPerson.clear();
                    listPerson.addAll(people);
                    searchPersonAdapter.notifyDataSetChanged();
                    if(recyclerView.getAdapter()!=searchPersonAdapter){
                        clearCurrentAdapterData();
                        recyclerView.setAdapter(searchPersonAdapter);
                    }

                }else{
                    searchPersonAdapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.GONE);
                    noSearchDataView.setVisibility(View.VISIBLE);
                }
            });
        searchTVViewModel.getListAiringTodayModelData().observe(getViewLifecycleOwner(), airingTodayModels -> {
                if(airingTodayModels!=null&&!airingTodayModels.isEmpty()){
                    if(recyclerView.getVisibility()==View.GONE){
                        noSearchDataView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    lisTVShow.clear();
                    lisTVShow.addAll(airingTodayModels);
                    searchTVAdapter.notifyDataSetChanged();
                    if(recyclerView.getAdapter()!=searchTVAdapter){
                        clearCurrentAdapterData();
                        recyclerView.setAdapter(searchTVAdapter);
                    }
                }else{
                    searchTVAdapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.GONE);
                    noSearchDataView.setVisibility(View.VISIBLE);
                }
            });
    }
    private  void observeFavouriteDataChange(){
        favouriteMediaViewModel.getFavouriteTVSeries().observe(getViewLifecycleOwner(), new Observer<List<FavouriteMedia>>() {
            @Override
            public void onChanged(List<FavouriteMedia> favouriteMedia) {
//                searchTVAdapter.setFavouriteData(favouriteMedia);
            }
        });
    }
    private void getOnBackPressed(){
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isEnabled()){
                    searchView.clearFocus();
                }

            }
        });
    }
    private void onFilterButtonHandle(){
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(requireActivity(),FilterActivity.class);
                isFilterForward=true;
                Log.d("Test queryText","queryText: "+queryText);
                intent.putExtra("requestCode", RequestCode.REQUEST_FILTER);
                intent.putExtra(StringConstants.isAdultKey,isAdult);
                intent.putExtra(StringConstants.isPersonKey,isPerson);
                intent.putExtra(StringConstants.isMovieKey,isMovie);
                intent.putExtra(StringConstants.isTVKey,isTV);
                intent.putExtra(StringConstants.isCheckAllKey,isCheckAll);
                launcher.launch(intent);
            }
        });
    }

    private void onScrollListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if(isCheckAll){
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == searchAdapter.getItemCount() - IntConstants.PRE_FETCH_THRESHOLD) {
                        searchMultiViewModel.loadNextPage(queryText,isAdult,getViewLifecycleOwner());
                    }
                }else if(isMovie){
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == searchMovieAdapter.getItemCount() - IntConstants.PRE_FETCH_THRESHOLD) {
                        searchMovieViewModel.loadNextPage(queryText,isAdult,getViewLifecycleOwner());
                    }
                }else if(isPerson){
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == searchPersonAdapter.getItemCount() - IntConstants.PRE_FETCH_THRESHOLD) {
                        searchPeopleViewModel.loadNextPage(queryText,isAdult,getViewLifecycleOwner());
                    }
                }else{
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == searchTVAdapter.getItemCount() - IntConstants.PRE_FETCH_THRESHOLD) {
                        searchTVViewModel.loadNextPage(queryText,isAdult,getViewLifecycleOwner());
                    }
                }

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void onStop() {
        super.onStop();
        if(!isFilterForward){
         searchMultiList=new ArrayList<>();
        listPerson=new ArrayList<>();
        listMovie=new ArrayList<>();
        searchMovieAdapter.notifyDataSetChanged();
        searchPersonAdapter.notifyDataSetChanged();
        searchAdapter.notifyDataSetChanged();
        searchView.setQuery("",false);
            resetViewModels();
            resetData();
            typeOfSearchingTV.setText("");
            isAdultTV.setText("");
            isSearched=false;
            searchLayoutView.setVisibility(View.GONE);
            nonSearchLayout.setVisibility(View.VISIBLE);
            callSearchHistoryData();
            resetDefaultBoolValue();
        }

    }
    private void resetDefaultBoolValue(){
           isCheckAll=true;
            isTV=false;
            isMovie=false;
            isPerson=false;
    }
    private void resetViewModels() {
        if(recyclerView.getAdapter()==searchTVAdapter){
            searchTVViewModel.getListAiringTodayModelData().removeObservers(getViewLifecycleOwner());
        }
        if(recyclerView.getAdapter()==searchPersonAdapter){
            searchPeopleViewModel.getListPeopleData().removeObservers(getViewLifecycleOwner());
        }
        if(recyclerView.getAdapter()==searchMovieAdapter){
            searchMovieViewModel.getListMovieData().removeObservers(getViewLifecycleOwner());
        }
        if(recyclerView.getAdapter()==searchAdapter){
            searchMultiViewModel.getListSearchMulti().removeObservers(getViewLifecycleOwner());
        }
    }
    private void resetData(){
        if(recyclerView.getAdapter()==searchTVAdapter){
            searchTVViewModel.resetData();
        }
        if(recyclerView.getAdapter()==searchPersonAdapter){
            searchPeopleViewModel.resetData();
        }
        if(recyclerView.getAdapter()==searchMovieAdapter){
            searchMovieViewModel.resetData();
        }
        if(recyclerView.getAdapter()==searchAdapter){
            searchMultiViewModel.resetData();
        }
    }
    private void onHandleClearAllClick(){
        SeeMoreOnClickListener.getSeeMoreOnClick(clearAllHistoryTextView,() -> {
            searchHistoryViewModel.clearSearchHistory();
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        searchMultiList=new ArrayList<>();
        listPerson=new ArrayList<>();
        listMovie=new ArrayList<>();
        searchMovieAdapter.notifyDataSetChanged();
        searchPersonAdapter.notifyDataSetChanged();
        searchAdapter.notifyDataSetChanged();
        if(!isFilterForward){
            searchView.setQuery("",false);
        }else{
            queryText=searchView.getQuery().toString();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onClick(SearchMultiModel searchMultiModel) {
        if(connectivityViewModel.getState()){
            selectedSearchMultiModel=searchMultiModel;
            if(Objects.equals(searchMultiModel.getMediaType(), "movie")){
                Intent intent=new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(StringConstants.movieDetailPageDataKey,searchMultiModel.getId());
                startActivity(intent);
            }else if(searchMultiModel.getMediaType().equals("person")){
                Intent intent=new Intent(getActivity(), PersonDetailActivity.class);
                intent.putExtra(StringConstants.personDetailDataKey,searchMultiModel.getId());
                startActivity(intent);
            }else{
                Intent intent=new Intent(getActivity(), TVSeriesDetailActivity.class);
                intent.putExtra(StringConstants.tvSeriesIDKey,searchMultiModel.getId());
                startActivity(intent);
            }
        }else{
            NoInternetToastHelpers.show(getActivity());
        }

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
        } else{
            NoInternetToastHelpers.show(getActivity());
        }
    }

    @Override
    public void onCastClick(Cast cast) {

    }

    @Override
    public void onTVCLick(AiringTodayModel airingTodayModel) {
        if(connectivityViewModel.getState()){
            selectedTV=airingTodayModel;
            Intent intent=new Intent(getActivity(), TVSeriesDetailActivity.class);
            intent.putExtra(StringConstants.tvSeriesIDKey,selectedTV.getId());
            startActivity(intent);
        }else{
            NoInternetToastHelpers.show(getActivity());
        }

    }

    @Override
    public void onClick(SearchHistory searchHistory) {
        if(connectivityViewModel.getState()){
            if(searchHistory.getSearchType()==SearchType.TV){
                changeBoolWhenSelectHistoryItem(SearchType.TV);
                callTVAPI(searchHistory.getSearchKey(), searchHistory.isAdult() == 1);
                setTypeText(SearchType.TV);
                queryText=searchHistory.getSearchKey();
                searchView.setQuery(queryText,false);
                nonSearchLayout.setVisibility(View.GONE);
                searchLayoutView.setVisibility(View.VISIBLE);
                searchHistoryViewModel.insertSearchHistory(queryText,SearchType.TV,searchHistory.isAdult());
            }else if(searchHistory.getSearchType()==SearchType.MOVIE){
                changeBoolWhenSelectHistoryItem(SearchType.MOVIE);
                callMovieAPI(searchHistory.getSearchKey(), searchHistory.isAdult() == 1);
                setTypeText(SearchType.MOVIE);
                queryText=searchHistory.getSearchKey();
                searchView.setQuery(queryText,false);
                nonSearchLayout.setVisibility(View.GONE);
                searchLayoutView.setVisibility(View.VISIBLE);
                searchHistoryViewModel.insertSearchHistory(queryText,SearchType.MOVIE,searchHistory.isAdult());
            }else if(searchHistory.getSearchType()==SearchType.MULTI){
                changeBoolWhenSelectHistoryItem(SearchType.MULTI);
                callAPIs(searchHistory.getSearchKey(), searchHistory.isAdult() == 1);
                setTypeText(SearchType.MULTI);
                queryText=searchHistory.getSearchKey();
                searchView.setQuery(queryText,false);
                nonSearchLayout.setVisibility(View.GONE);
                searchLayoutView.setVisibility(View.VISIBLE);
                searchHistoryViewModel.insertSearchHistory(queryText,SearchType.MULTI,searchHistory.isAdult());
            }else{
                changeBoolWhenSelectHistoryItem(SearchType.PEOPLE);
                callPersonAPI(searchHistory.getSearchKey(), searchHistory.isAdult() == 1);
                setTypeText(SearchType.PEOPLE);
                queryText=searchHistory.getSearchKey();
                searchView.setQuery(queryText,false);
                nonSearchLayout.setVisibility(View.GONE);
                searchLayoutView.setVisibility(View.VISIBLE);
                searchHistoryViewModel.insertSearchHistory(queryText,SearchType.PEOPLE,searchHistory.isAdult());
            }
        }else{
            NoInternetToastHelpers.show(getActivity());
        }

    }
    private void setAdultText(){
        if(isAdult){
            isAdultTV.setText("Adult");
        }else{
            isAdultTV.setText("Any");
        }
    }
    private void setTypeText(SearchType searchType){
        if(searchType==SearchType.MOVIE){
            typeOfSearchingTV.setText("Search: Movies");
        }else if(searchType==SearchType.TV){
            typeOfSearchingTV.setText("Search: TVs");
        }else if(searchType==SearchType.PEOPLE){
            typeOfSearchingTV.setText("Search: People");
        }else{
            typeOfSearchingTV.setText("Any");
        }
    }
}