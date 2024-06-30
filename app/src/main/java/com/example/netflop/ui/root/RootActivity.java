package com.example.netflop.ui.root;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.netflop.MainActivity;
import com.example.netflop.R;
import com.example.netflop.ui.on_boarding.OnBoardingActivity;
import com.example.netflop.viewmodel.local.OnBoardingViewModel;

public class RootActivity extends AppCompatActivity {
    OnBoardingViewModel onBoardingViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        init();
        checkStatusAndNav();
    }
    private void init(){
        onBoardingViewModel=new ViewModelProvider(this).get(OnBoardingViewModel.class);
    }
    private void checkStatusAndNav(){
        if(onBoardingViewModel.getState()){
            Intent i = new Intent(RootActivity.this, MainActivity.class);
            startActivity(i);
        }else{
            Intent i = new Intent(RootActivity.this, OnBoardingActivity.class);
            startActivity(i);
        }
    }
}