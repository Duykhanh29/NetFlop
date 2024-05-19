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
import com.example.netflop.constants.StringConstants;
import com.example.netflop.data.models.Cast;
import com.example.netflop.data.models.Movie;
import com.example.netflop.data.models.Person;
import com.example.netflop.databinding.ActivityAllTrendingPeopleBinding;
import com.example.netflop.databinding.ActivityAllUpcomingBinding;
import com.example.netflop.ui.adapters.GridMoviesAdapter;
import com.example.netflop.ui.adapters.GridPeopleAdapter;
import com.example.netflop.ui.movie_detail.MovieDetailActivity;
import com.example.netflop.ui.person_detail.PersonDetailActivity;
import com.example.netflop.utils.CustomActionBar;
import com.example.netflop.utils.ItemTouchHelperAdapter;
import com.example.netflop.utils.SpacingItemDecorator;
import com.example.netflop.viewmodel.TrendingPeopleViewModel;
import com.example.netflop.viewmodel.UpcomingViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllTrendingPeopleActivity extends AppCompatActivity implements ItemTouchHelperAdapter {
    ActivityAllTrendingPeopleBinding binding;

    //
    Person selectedPerson;
    GridPeopleAdapter gridPeopleAdapter;
    List<Person> listPeople;

    // view model
    TrendingPeopleViewModel trendingPeopleViewModel;
    // UI
    RecyclerView allTrendingPeopleRecyclerView;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//       setContentView(R.layout.activity_all_trending_people);
        binding=ActivityAllTrendingPeopleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getBinding();
        initialize();
        callAPI();
        observeDataChange();
        scrollListener();
    }
    private void getBinding(){
        allTrendingPeopleRecyclerView=binding.allTrendingPeopleRecyclerView;
    }
    private void initialize(){
        actionBar=getSupportActionBar();
//        CustomActionBar.createActionBar(actionBar,"All people movie");
        SpannableString spannableTitle = new SpannableString("All people movie");
        spannableTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(spannableTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.black_arrow_back);
        trendingPeopleViewModel=new ViewModelProvider(this).get(TrendingPeopleViewModel.class);
        listPeople=new ArrayList<>();
        gridPeopleAdapter=new GridPeopleAdapter(listPeople,this,this);
        allTrendingPeopleRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(30,20);
        allTrendingPeopleRecyclerView.addItemDecoration(itemDecorator);
        allTrendingPeopleRecyclerView.setAdapter(gridPeopleAdapter);
//        allUpcomingMovieRecyclerView.setAdapter(gridMoviesAdapter);
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
        trendingPeopleViewModel.callAPI();
    }
    private void observeDataChange(){
        trendingPeopleViewModel.getListPeopleData().observe(this, new Observer<List<Person>>() {
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
    private void scrollListener(){
        allTrendingPeopleRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == gridPeopleAdapter.getItemCount() - 1) {
                    // Gọi phương thức để tải trang tiếp theo
                    trendingPeopleViewModel.loadNextPage();
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
        selectedPerson=p;
        Intent intent=new Intent(this, PersonDetailActivity.class);
        intent.putExtra(StringConstants.personDetailDataKey,selectedPerson.getID());
        startActivity(intent);
    }

    @Override
    public void onCastClick(Cast cast) {

    }
}