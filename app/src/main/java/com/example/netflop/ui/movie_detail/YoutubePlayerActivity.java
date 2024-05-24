package com.example.netflop.ui.movie_detail;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.databinding.ActivityYoutubePlayerBinding;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class YoutubePlayerActivity extends AppCompatActivity {
    ActivityYoutubePlayerBinding binding;
    YouTubePlayerView youTubePlayerView;
    String youtubeURL= "";
    String videoID="";
    String title="";
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityYoutubePlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getBinding();
        getData();
        initializeToolBar();
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.loadVideo(videoID,0);
            }
        });
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
        videoID=intent.getStringExtra(StringConstants.youtubeURLKey);
        youtubeURL=URLConstants.youtubeBaseURL+videoID;
        title=intent.getStringExtra(StringConstants.youtubeTitleKey);
    }
    private void getBinding(){
        youTubePlayerView=binding.youtubePlayerView;
        toolbar=binding.toolBarYoutubeView;
    }
    private void initializeToolBar(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.black_arrow_back);
        toolbar.setNavigationContentDescription("Back");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setTitle(title!=null?title:"Trailer");
    }
}