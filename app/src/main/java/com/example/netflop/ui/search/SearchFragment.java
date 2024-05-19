package com.example.netflop.ui.search;

import android.app.SearchManager;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
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
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.models.Person;
import com.example.netflop.data.models.SearchMultiModel;
import com.example.netflop.databinding.FragmentSearchBinding;
import com.example.netflop.ui.adapters.SearchAdapter;
import com.example.netflop.utils.SearchItemOnClickListener;
import com.example.netflop.utils.SpacingItemDecorator;
import com.example.netflop.viewmodel.SearchMovieViewModel;
import com.example.netflop.viewmodel.SearchMultiViewModel;
import com.example.netflop.viewmodel.SearchPeopleViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchItemOnClickListener {

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
    // UI
    DrawerLayout drawerLayout;
    TextView textView;


    SearchView searchView;
    RecyclerView recyclerView;
    boolean isAdult=false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getBinding();
        initialize();
        observeDataChanged();
        getOnBackPressed();
        return root;
    }
    private void getBinding(){
        searchView=binding.searchView;
        recyclerView=binding.searchRecyclerView;
    }


    private void initialize(){
        initializeSearchView();
        searchMultiList=new ArrayList<>();
        listMovie=new ArrayList<>();
        listPerson=new ArrayList<>();
        searchMultiViewModel=new ViewModelProvider(this).get(SearchMultiViewModel.class);
        searchPeopleViewModel=new ViewModelProvider(this).get(SearchPeopleViewModel.class);
        searchMovieViewModel=new ViewModelProvider(this).get(SearchMovieViewModel.class);

        searchAdapter=new SearchAdapter(searchMultiList,getActivity(),this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(20,20);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setAdapter(searchAdapter);

    }
    private void initializeSearchView(){
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callAPIs(query,isAdult);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private void callAPIs(String searchKey,boolean isAdult){
        searchMultiViewModel.callAPI(searchKey,isAdult);
    }
    private void observeDataChanged(){
        searchMultiViewModel.getListSearchMulti().observe(getViewLifecycleOwner(), new Observer<List<SearchMultiModel>>() {
            @Override
            public void onChanged(List<SearchMultiModel> searchMultiModels) {
                if(searchMultiModels!=null&&!searchMultiModels.isEmpty()){
                    searchMultiList.clear();  // Clear the list to ensure no duplicates if needed
                    searchMultiList.addAll(searchMultiModels);
                    searchAdapter.notifyDataSetChanged();
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
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onClick(SearchMultiModel searchMultiModel) {

    }

}