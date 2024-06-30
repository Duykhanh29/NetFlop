package com.example.netflop.ui.on_boarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflop.MainActivity;
import com.example.netflop.R;
import com.example.netflop.databinding.ActivityOnBoardingBinding;
import com.example.netflop.ui.adapters.on_boarding.ViewPagerAdapter;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;
import com.example.netflop.viewmodel.local.OnBoardingViewModel;

public class OnBoardingActivity extends AppCompatActivity {
    ActivityOnBoardingBinding binding;
    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    Button backBtn, nextBtn, skipBtn;
    ViewPagerAdapter viewPagerAdapter;
    TextView[] dots;

    OnBoardingViewModel onBoardingViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        binding=ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getBinding();
        initialize();
        initViews();
        onHandleSkipClick();
        onHandleNextClick();
        onHandleBackClick();
        handleOnPageChanged();

    }
    private void getBinding(){
        mSLideViewPager=binding.slideViewPager;
        mDotLayout=binding.indicatorLayout;
        backBtn=binding.backOnBoardingButton;
        nextBtn=binding.nextOnBoardingButton;
        skipBtn=binding.skipOnBoardingButton;
    }
    private int getItem(int i){
        return mSLideViewPager.getCurrentItem() + i;
    }
    private void initialize(){
        onBoardingViewModel=new ViewModelProvider(this).get(OnBoardingViewModel.class);

    }
    private void initViews(){
        viewPagerAdapter=new ViewPagerAdapter(OnBoardingActivity.this);
        mSLideViewPager.setAdapter(viewPagerAdapter);
    }
    private void onHandleSkipClick(){
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBoardingViewModel.setState();
                Intent i = new Intent(OnBoardingActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void onHandleNextClick(){
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) < 3)
                    mSLideViewPager.setCurrentItem(getItem(1),true);
                else {
                    onBoardingViewModel.setState();
                    Intent i = new Intent(OnBoardingActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
    private void onHandleBackClick(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) > 0){
                    mSLideViewPager.setCurrentItem(getItem(-1),true);
                }
            }
        });
    }
    private void handleOnPageChanged(){
        mSLideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                setUpIndicator(position);
                if (position > 0){
                    backBtn.setVisibility(View.VISIBLE);
                }else {
                    backBtn.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void setUpIndicator(int position){

        dots = new TextView[4];
        mDotLayout.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive,getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.active,getApplicationContext().getTheme()));
    }
}