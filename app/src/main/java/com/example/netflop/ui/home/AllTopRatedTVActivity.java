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
import com.example.netflop.constants.IntConstants;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.data.models.TVs.AiringTodayModel;
import com.example.netflop.databinding.ActivityAllTopRatedTvactivityBinding;
import com.example.netflop.ui.TV_Detail.TVSeriesDetailActivity;
import com.example.netflop.ui.adapters.GridTVAdapter;
import com.example.netflop.utils.listeners.ItemTVOnClickListener;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.viewmodel.TopRatedTVViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllTopRatedTVActivity extends AppCompatActivity implements ItemTVOnClickListener {
    ActivityAllTopRatedTvactivityBinding binding;
    AiringTodayModel selectedTV;
    GridTVAdapter gridTVAdapter;
    List<AiringTodayModel> todayModelList;

    // view model
    TopRatedTVViewModel topRatedTVViewModel;
    // UI
    RecyclerView allTopRatedTVRecyclerView;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_top_rated_tvactivity);
        binding= ActivityAllTopRatedTvactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getBinding();
        initialize();
        callAPI();
        observeDataChange();
        scrollListener();
    }
    private void getBinding(){
        allTopRatedTVRecyclerView=binding.allTopRatedTVRecyclerView;
    }
    private void initialize(){
        actionBar=getSupportActionBar();
//        CustomActionBar.createActionBar(actionBar,"All people movie");
        SpannableString spannableTitle = new SpannableString("All popular TV series");
        spannableTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(spannableTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.black_arrow_back);
        topRatedTVViewModel=new ViewModelProvider(this).get(TopRatedTVViewModel.class);
        todayModelList=new ArrayList<>();
        gridTVAdapter=new GridTVAdapter(todayModelList,this,this);

        RecyclerViewUtils.setupGridRecyclerView(this,allTopRatedTVRecyclerView,gridTVAdapter,2);

//        allTopRatedTVRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(30,20);
//        allTopRatedTVRecyclerView.addItemDecoration(itemDecorator);
//        allTopRatedTVRecyclerView.setAdapter(gridTVAdapter);
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
        topRatedTVViewModel.fetchTopRatedTV(this);
    }
    private void observeDataChange(){

        topRatedTVViewModel.getListTopRatedTV().observe(this, new Observer<List<AiringTodayModel>>() {
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
        allTopRatedTVRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == gridTVAdapter.getItemCount() - IntConstants.PRE_FETCH_THRESHOLD) {
                    topRatedTVViewModel.loadNextPage(AllTopRatedTVActivity.this);
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