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
import com.example.netflop.data.models.Person;
import com.example.netflop.data.models.TVs.AiringTodayModel;
import com.example.netflop.databinding.ActivityAllPopularPeopleBinding;
import com.example.netflop.databinding.ActivityAllPopularTvactivityBinding;
import com.example.netflop.ui.TV_Detail.TVSeriesDetailActivity;
import com.example.netflop.ui.adapters.GridPeopleAdapter;
import com.example.netflop.ui.adapters.GridTVAdapter;
import com.example.netflop.utils.ItemTVOnClickListener;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.utils.SpacingItemDecorator;
import com.example.netflop.viewmodel.PopularPeopleViewModel;
import com.example.netflop.viewmodel.PopularTVViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllPopularTVActivity extends AppCompatActivity implements ItemTVOnClickListener {
    ActivityAllPopularTvactivityBinding binding;
    AiringTodayModel selectedTV;
    GridTVAdapter gridTVAdapter;
    List<AiringTodayModel> todayModelList;

    // view model
    PopularTVViewModel popularTVViewModel;
    // UI
    RecyclerView allPopularTVRecyclerView;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_popular_tvactivity);
        binding= ActivityAllPopularTvactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getBinding();
        initialize();
        callAPI();
        observeDataChange();
        scrollListener();
    }
    private void getBinding(){
        allPopularTVRecyclerView=binding.allPopularTVRecyclerView;
    }
    private void initialize(){
        actionBar=getSupportActionBar();
//        CustomActionBar.createActionBar(actionBar,"All people movie");
        SpannableString spannableTitle = new SpannableString("All popular TV series");
        spannableTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(spannableTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.black_arrow_back);
        popularTVViewModel=new ViewModelProvider(this).get(PopularTVViewModel.class);
        todayModelList=new ArrayList<>();
        gridTVAdapter=new GridTVAdapter(todayModelList,this,this);

        RecyclerViewUtils.setupGridRecyclerView(this,allPopularTVRecyclerView,gridTVAdapter,2);

//        allPopularTVRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(30,20);
//        allPopularTVRecyclerView.addItemDecoration(itemDecorator);
//        allPopularTVRecyclerView.setAdapter(gridTVAdapter);
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
        popularTVViewModel.fetchPopularTV(this);
    }
    private void observeDataChange(){
        popularTVViewModel.getListPopularTV().observe(this, new Observer<List<AiringTodayModel>>() {
            @Override
            public void onChanged(List<AiringTodayModel> airingTodayModels) {
                if(airingTodayModels!=null&&!airingTodayModels.isEmpty()){
                    todayModelList.clear();
                    todayModelList.addAll(airingTodayModels);
                    gridTVAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void scrollListener(){
        allPopularTVRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == gridTVAdapter.getItemCount() - 1) {
                    popularTVViewModel.loadNextPage(AllPopularTVActivity.this);
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
    public void onTVCLick(AiringTodayModel airingTodayModel) {
        selectedTV=airingTodayModel;
        Intent intent=new Intent(this, TVSeriesDetailActivity.class);
        intent.putExtra(StringConstants.tvSeriesIDKey,selectedTV.getId());
        startActivity(intent);
    }
}