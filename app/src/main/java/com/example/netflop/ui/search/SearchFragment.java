package com.example.netflop.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflop.MainActivity;
import com.example.netflop.R;
import com.example.netflop.constants.RequestCode;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.data.models.Cast;
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.models.Person;
import com.example.netflop.data.models.SearchMultiModel;
import com.example.netflop.databinding.FragmentSearchBinding;
import com.example.netflop.ui.adapters.SearchAdapter;
import com.example.netflop.ui.adapters.SearchMovieAdapter;
import com.example.netflop.ui.adapters.SearchPersonAdapter;
import com.example.netflop.ui.movie_detail.MovieDetailActivity;
import com.example.netflop.ui.person_detail.PersonDetailActivity;
import com.example.netflop.utils.ItemTouchHelperAdapter;
import com.example.netflop.utils.SearchItemOnClickListener;
import com.example.netflop.utils.SpacingItemDecorator;
import com.example.netflop.viewmodel.SearchMovieViewModel;
import com.example.netflop.viewmodel.SearchMultiViewModel;
import com.example.netflop.viewmodel.SearchPeopleViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchFragment extends Fragment implements SearchItemOnClickListener, ItemTouchHelperAdapter {

    private FragmentSearchBinding binding;


    //view model
    SearchMultiViewModel searchMultiViewModel;
    SearchPeopleViewModel searchPeopleViewModel;
    SearchMovieViewModel searchMovieViewModel;

    // lists
    List<SearchMultiModel> searchMultiList;
    List<Movie> listMovie;
    List<Person> listPerson;

    // adapters
    SearchAdapter searchAdapter;
    SearchPersonAdapter searchPersonAdapter;
    SearchMovieAdapter searchMovieAdapter;
    // UI
    DrawerLayout drawerLayout;
    TextView typeOfSearchingTV,isAdultTV,noSearchDataView;
    ImageButton filterButton;

    SearchView searchView;
    RecyclerView recyclerView;
    boolean isAdult=false;
    boolean isCheckAll=true;
    boolean isPerson=true;
    boolean isMovie=true;
    boolean isTV=true;

    // backup variables for adjusting filter
    boolean isBackupAdult=false;
    boolean isBackupCheckAll=true;
    boolean isBackupPerson=true;
    boolean isBackupMovie=true;
    boolean isBackupTV=true;


    String queryText;
    boolean isFilterForward=false;

    //selected variables
    SearchMultiModel selectedSearchMultiModel;
    Movie selectedMovie;
    Person selectedPerson;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == getActivity().RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        isAdult = data.getBooleanExtra(StringConstants.isAdultKey, false);
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

        if(shouldRefresh &&queryText!=null&& !queryText.isEmpty()){
            refreshSearchResults();
        }
    }

    private void refreshSearchResults(){
        if(isCheckAll){
            initializeMultipleRecyclerView();
            callAPIs(queryText);
        } else if(isPerson){
            initializePeopleRecyclerView();
            callPersonAPI(queryText);
        } else if(isMovie){
            initializeMovieRecyclerView();
            callMovieAPI(queryText);
        } else {
            // Handle other cases as needed
        }
    }
//    private void onBackupHandle(){
//        if(isAdult==isBackupAdult){
//
//        }else{
//            isBackupAdult=isAdult;
//        }
//        if(isPerson==isBackupPerson){
//
//        }else{
//            isBackupPerson=   isPerson;
//        }
//        if(isTV==isBackupTV){
//
//        }else{
//            isBackupTV=  isTV;
//        }
//        if(isBackupMovie==isMovie){
//
//        }else{
//            isBackupMovie=isMovie;
//        }
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getBinding();
        initialize();
        observeDataChanged();
        getOnBackPressed();
        onFilterButtonHandle();
        onScrollListener();
        return root;
    }
    private void getBinding(){
        searchView=binding.searchView;
        recyclerView=binding.searchRecyclerView;
        filterButton=binding.filterImageButton;
        typeOfSearchingTV=binding.typeOfSearchingView;
        isAdultTV=binding.isAdultSearchingView;
        noSearchDataView=binding.noSearchDataView;
    }



    private void initialize(){
        initializeSearchView();
        searchMultiList=new ArrayList<>();
        listMovie=new ArrayList<>();
        listPerson=new ArrayList<>();
        searchMultiViewModel=new ViewModelProvider(this).get(SearchMultiViewModel.class);
        searchPeopleViewModel=new ViewModelProvider(this).get(SearchPeopleViewModel.class);
        searchMovieViewModel=new ViewModelProvider(this).get(SearchMovieViewModel.class);

        searchPersonAdapter=new SearchPersonAdapter(listPerson,getActivity(),this);
        searchMovieAdapter=new SearchMovieAdapter(listMovie,getActivity(),this);
        searchAdapter=new SearchAdapter(searchMultiList,getActivity(),this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(20,20);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setAdapter(searchAdapter);
    }
    private void initializeMultipleRecyclerView(){
        clearCurrentAdapterData();
        listMovie=new ArrayList<>();
        listPerson=new ArrayList<>();
        searchAdapter=new SearchAdapter(searchMultiList,getActivity(),this);
        recyclerView.setAdapter(searchAdapter);
    }
    private void initializePeopleRecyclerView(){
        clearCurrentAdapterData();
        searchMultiList=new ArrayList<>();
        listMovie=new ArrayList<>();
        searchPersonAdapter=new SearchPersonAdapter(listPerson,getActivity(),this);
        recyclerView.setAdapter(searchPersonAdapter);
    }
    private void initializeMovieRecyclerView(){
        clearCurrentAdapterData();
        searchMultiList=new ArrayList<>();
        listPerson=new ArrayList<>();
        searchMovieAdapter=new SearchMovieAdapter(listMovie,getActivity(),this);
        recyclerView.setAdapter(searchMovieAdapter);
    }
    private void clearCurrentAdapterData(){
        RecyclerView.Adapter currentAdapter = recyclerView.getAdapter();
        if (currentAdapter instanceof SearchMovieAdapter) {
            ((SearchMovieAdapter) currentAdapter).clearData();
        }else if(currentAdapter instanceof SearchPersonAdapter){
            ((SearchPersonAdapter) currentAdapter).clearData();
        }else if(currentAdapter instanceof SearchAdapter){
            ((SearchAdapter) currentAdapter).clearData();
        }
    }

    private void initializeSearchView(){
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryText=query;
                if(query.isEmpty()||query==null){
                    searchView.clearFocus();
                    hideKeyboard();
                    return false;
                }else{
                    if(isCheckAll){
                        callAPIs(query);
                        typeOfSearchingTV.setText("Any");
                        if(isAdult){
                            isAdultTV.setText("Adult");
                        }else{
                            isAdultTV.setText("Any");
                        }
                    }else if(isMovie){
                        typeOfSearchingTV.setText("Search: Movies");
                        if(isAdult){
                            isAdultTV.setText("Adult");
                        }else{
                            isAdultTV.setText("Any");
                        }
                        callMovieAPI(query);
                    }else if(isPerson){
                        typeOfSearchingTV.setText("Search: People");
                        if(isAdult){
                            isAdultTV.setText("Adult");
                        }else{
                            isAdultTV.setText("Any");
                        }
                        callPersonAPI(query);
                    }else{
                        callAPIs(query);
                    }
                    searchView.clearFocus();
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
    private void callAPIs(String searchKey){
        searchMultiViewModel.callAPI(searchKey,isAdult);
    }
    private void callPersonAPI(String searchKey){
        searchPeopleViewModel.callAPI(searchKey,isAdult);
    }
    private void callMovieAPI(String searchKey){
        searchMovieViewModel.callAPI(searchKey,isAdult);
    }
    private void observeDataChanged(){
        searchMultiViewModel.getListSearchMulti().observe(getViewLifecycleOwner(), new Observer<List<SearchMultiModel>>() {
            @Override
            public void onChanged(List<SearchMultiModel> searchMultiModels) {

                if(searchMultiModels!=null&&!searchMultiModels.isEmpty()){
                    searchMultiList.clear();  // Clear the list to ensure no duplicates if needed
                    searchMultiList.addAll(searchMultiModels);
                    searchAdapter.notifyDataSetChanged();
                }else{
                    searchAdapter.notifyDataSetChanged();
                                                                                                                                }
            }
        });
        searchMovieViewModel.getListMovieData().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {

                if(movies!=null&&!movies.isEmpty()){
                    listMovie.clear();
                    listMovie.addAll(movies);
                    searchMovieAdapter.notifyDataSetChanged();
                }else{
                    searchMovieAdapter.notifyDataSetChanged();
                }
            }
        });

        searchPeopleViewModel.getListPeopleData().observe(getViewLifecycleOwner(), new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {

                if(people!=null&&!people.isEmpty()){
                    listPerson.clear();
                    listPerson.addAll(people);
                    searchPersonAdapter.notifyDataSetChanged();
                }else{
                    searchPersonAdapter.notifyDataSetChanged();

                }
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
//                View view = requireActivity().getCurrentFocus();
//                InputMethodManager imm = (InputMethodManager)requireActivity(). getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }
    private void onFilterButtonHandle(){
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(requireActivity(),FilterActivity.class);
                isFilterForward=true;
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
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if(isCheckAll){
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == searchAdapter.getItemCount() - 1) {
                        searchMultiViewModel.loadNextPage(queryText,isAdult);
                    }
                }else if(isMovie){
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == searchMovieAdapter.getItemCount() - 1) {
                        searchMovieViewModel.loadNextPage(queryText,isAdult);
                    }
                }else if(isPerson){
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == searchPersonAdapter.getItemCount() - 1) {
                        searchPeopleViewModel.loadNextPage(queryText,isAdult);
                    }
                }else{
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == searchAdapter.getItemCount() - 1) {

                    }
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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
        searchMultiViewModel.getListSearchMulti().removeObservers(getViewLifecycleOwner());
        searchPeopleViewModel.getListPeopleData().removeObservers(getViewLifecycleOwner());
            searchMovieViewModel.getListMovieData().removeObservers(getViewLifecycleOwner());
            searchMultiViewModel.resetData();
            searchMovieViewModel.resetData();
            searchPeopleViewModel.resetData();
            typeOfSearchingTV.setText("");
            isAdultTV.setText("");
        }

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onClick(SearchMultiModel searchMultiModel) {
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

        }
    }

    @Override
    public void onMovieClick(Movie movie) {
        selectedMovie=movie;
        Intent intent=new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(StringConstants.movieDetailPageDataKey,selectedMovie.getID());
        startActivity(intent);
    }

    @Override
    public void onPersonClick(Person p) {
        selectedPerson=p;
        Intent intent=new Intent(getActivity(), PersonDetailActivity.class);
        intent.putExtra(StringConstants.personDetailDataKey,selectedPerson.getID());
        startActivity(intent);
    }

    @Override
    public void onCastClick(Cast cast) {

    }
}