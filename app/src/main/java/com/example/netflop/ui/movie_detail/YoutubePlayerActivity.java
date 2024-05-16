package com.example.netflop.ui.movie_detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityYoutubePlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getBinding();
        getData();
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.loadVideo(videoID,0);
            }
        });
    }
    private void getData(){
        Intent intent=getIntent();
        videoID=intent.getStringExtra(StringConstants.youtubeURLKey);
        youtubeURL=URLConstants.youtubeBaseURL+videoID;
    }
    private void getBinding(){
        youTubePlayerView=binding.youtubePlayerView;
    }
}