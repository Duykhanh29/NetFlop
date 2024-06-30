package com.example.netflop.ui.movie_detail;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.netflop.R;
import com.example.netflop.constants.IntConstants;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.data.models.remote.movies.Review;
import com.example.netflop.databinding.ActivityAllReviewBinding;
import com.example.netflop.ui.adapters.remote.ListReviewAdapter;
import com.example.netflop.ui.base.BaseActivity;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.viewmodel.remote.ReviewViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllReviewActivity extends BaseActivity {
    ActivityAllReviewBinding binding;
    // view model
    ReviewViewModel reviewViewModel;
    RecyclerView recyclerView;
    List<Review> listReview;
    ListReviewAdapter listReviewAdapter;

    // UI
    Toolbar toolbar;

    //
    int movieID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_review);

        binding= ActivityAllReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setBinding();
        initialize();
        initializeToolBar();
        getData();
        loadReview();
        observeDataChanged();
        scrollListener();
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isEnabled()){
                    finish();
                }
            }
        });
    }
    private void getData(){
        Intent intent=getIntent();
        movieID=intent.getIntExtra(StringConstants.movieIDDataKey,-1);
    }
    private void loadReview(){
        reviewViewModel.fetchReviewData(movieID,this);
    }
    private void setBinding(){
        recyclerView=binding.allReviewRecyclerView;
        toolbar=binding.toolBarAllReviewView;
    }
    private void initialize(){
        listReview=new ArrayList<>();
        listReviewAdapter=new ListReviewAdapter(listReview,this);
        RecyclerViewUtils.setupHorizontalRecyclerView(this,recyclerView,listReviewAdapter, LinearLayoutManager.VERTICAL,false);
        reviewViewModel=new ViewModelProvider(this).get(ReviewViewModel.class);
//        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(20,0);
//        allTrailerRecyclerView.addItemDecoration(itemDecorator);
//        allTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        allTrailerRecyclerView.setAdapter(listTrailerAdapter);
    }
    private void initializeToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All reviews");
        toolbar.setNavigationIcon(R.drawable.black_arrow_back);
        toolbar.setNavigationContentDescription("Back");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void observeDataChanged(){
        reviewViewModel.getListReview().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                if(reviews!=null&&!reviews.isEmpty()){
                    listReview.clear();
                    listReview.addAll(reviews);
                    listReviewAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void scrollListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listReviewAdapter.getItemCount() - IntConstants.PRE_FETCH_THRESHOLD) {
                    // Gọi phương thức để tải trang tiếp theo
                    reviewViewModel.loadNextPage(movieID,AllReviewActivity.this);
//                    observeDataChange();
                }
            }
        });
    }
}