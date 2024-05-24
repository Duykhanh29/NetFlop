package com.example.netflop.ui.movie_detail;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.data.models.Video;
import com.example.netflop.databinding.ActivityAllTrailersBinding;
import com.example.netflop.ui.adapters.SecondListTrailerAdapter;
import com.example.netflop.utils.SpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class AllTrailersActivity extends AppCompatActivity  {
    ActivityAllTrailersBinding binding;
    SecondListTrailerAdapter listTrailerAdapter;
    List<Video> listVideo;

    // ui
    RecyclerView allTrailerRecyclerView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAllTrailersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setBinding();
        initialize();
        initializeToolBar();
        getData();
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
        ArrayList<? extends Parcelable> parcelableArrayList = intent.getParcelableArrayListExtra(StringConstants.listVideoKey);
        if (parcelableArrayList != null) {
            List<Video> listVideo = new ArrayList<>(parcelableArrayList.size());
//            for (int i = 0; i <1 ; i++) {
//                listVideo.add((Video) parcelableArrayList.get(0));
//            }
            for (Parcelable parcelable : parcelableArrayList) {
                listVideo.add((Video) parcelable);
            }
            listTrailerAdapter=new SecondListTrailerAdapter(listVideo,this,getLifecycle());
            allTrailerRecyclerView.setAdapter(listTrailerAdapter);
        }
    }
    private void setBinding(){
        allTrailerRecyclerView=binding.allTrailerRecyclerView;
        toolbar=binding.toolBarAllTrailerView;
    }
    private void initialize(){
        listVideo=new ArrayList<>();
        listTrailerAdapter=new SecondListTrailerAdapter(listVideo,this,getLifecycle());
        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(20,0);
        allTrailerRecyclerView.addItemDecoration(itemDecorator);
        allTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        allTrailerRecyclerView.setAdapter(listTrailerAdapter);
    }
    private void initializeToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All trailer");
        toolbar.setNavigationIcon(R.drawable.black_arrow_back);
        toolbar.setNavigationContentDescription("Back");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}