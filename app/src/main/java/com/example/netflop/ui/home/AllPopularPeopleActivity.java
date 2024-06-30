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
import com.example.netflop.databinding.ActivityAllPopularPeopleBinding;
import com.example.netflop.helpers.NoInternetToastHelpers;
import com.example.netflop.ui.adapters.remote.GridPeopleAdapter;
import com.example.netflop.ui.base.BaseActivity;
import com.example.netflop.ui.movie_detail.MovieDetailActivity;
import com.example.netflop.ui.person_detail.PersonDetailActivity;
import com.example.netflop.utils.listeners.ItemTouchHelperAdapter;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.viewmodel.connectivity.ConnectivityViewModel;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;
import com.example.netflop.viewmodel.remote.PopularPeopleViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllPopularPeopleActivity extends BaseActivity implements ItemTouchHelperAdapter {
    ActivityAllPopularPeopleBinding binding;

    Person selectedPerson;
    GridPeopleAdapter gridPeopleAdapter;
    List<Person> listPeople;

    // view model
    PopularPeopleViewModel popularPeopleViewModel;
    FavouriteMediaViewModel favouriteMediaViewModel;
    ConnectivityViewModel connectivityViewModel;
    // UI
    RecyclerView allPopularPeopleRecyclerView;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_popular_people);
        binding= ActivityAllPopularPeopleBinding.inflate(getLayoutInflater());
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
        allPopularPeopleRecyclerView=binding.allPopularPeopleRecyclerView;
    }
    private void initialize(){
        actionBar=getSupportActionBar();
//        CustomActionBar.createActionBar(actionBar,"All people movie");
        SpannableString spannableTitle = new SpannableString("All popular people");
        spannableTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(spannableTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.black_arrow_back);
        popularPeopleViewModel=new ViewModelProvider(this).get(PopularPeopleViewModel.class);
        favouriteMediaViewModel=new ViewModelProvider(this).get(FavouriteMediaViewModel.class);
        connectivityViewModel=new ViewModelProvider(this).get(ConnectivityViewModel.class);
        listPeople=new ArrayList<>();
        gridPeopleAdapter=new GridPeopleAdapter(listPeople,this,this,favouriteMediaViewModel);


        RecyclerViewUtils.setupGridRecyclerView(this,allPopularPeopleRecyclerView,gridPeopleAdapter,3);
//        allPopularPeopleRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(30,20);
//        allPopularPeopleRecyclerView.addItemDecoration(itemDecorator);
//        allPopularPeopleRecyclerView.setAdapter(gridPeopleAdapter);
//        allUpcomingMovieRecyclerView.setAdapter(gridMoviesAdapter);

    }
    private void callAPI(){
        popularPeopleViewModel.fetchPopularPeople(this);
    }
    private void observeDataChange(){
        popularPeopleViewModel.getListPeopleData().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                if(people!=null&&!people.isEmpty()){
                    listPeople.clear();
                    listPeople.addAll(people);
                    gridPeopleAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    private  void observeFavouriteDataChange(){
        favouriteMediaViewModel.getFavouritePeople().observe(this, new Observer<List<FavouriteMedia>>() {
            @Override
            public void onChanged(List<FavouriteMedia> favouriteMedia) {
//                gridPeopleAdapter.setFavouriteData(favouriteMedia);
            }
        });
    }
    private void fetchFavouriteData(){
        favouriteMediaViewModel.fetchPeopleData();
    }
    private void scrollListener(){
        allPopularPeopleRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == gridPeopleAdapter.getItemCount() - IntConstants.PRE_FETCH_THRESHOLD) {
                    // Gọi phương thức để tải trang tiếp theo
                    popularPeopleViewModel.loadNextPage(AllPopularPeopleActivity.this);
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

    }

    @Override
    public void onPersonClick(Person p) {
        if(connectivityViewModel.getState()){
            selectedPerson=p;
            Intent intent=new Intent(this, PersonDetailActivity.class);
            intent.putExtra(StringConstants.personDetailDataKey,selectedPerson.getID());
            startActivity(intent);
        }else{
            NoInternetToastHelpers.show(this);
        }
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