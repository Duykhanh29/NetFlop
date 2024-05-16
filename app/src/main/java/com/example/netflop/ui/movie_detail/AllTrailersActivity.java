package com.example.netflop.ui.movie_detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.netflop.R;
import com.example.netflop.data.models.Video;
import com.example.netflop.databinding.ActivityAllTrailersBinding;
import com.example.netflop.ui.adapters.SecondListTrailerAdapter;

import java.util.List;

public class AllTrailersActivity extends AppCompatActivity {
    ActivityAllTrailersBinding binding;
    SecondListTrailerAdapter listTrailerAdapter;
    List<Video> listVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAllTrailersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}