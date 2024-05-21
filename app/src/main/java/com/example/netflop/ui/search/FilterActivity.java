package com.example.netflop.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.databinding.ActivityFilterBinding;

public class FilterActivity extends AppCompatActivity {
    ActivityFilterBinding binding;
    CheckBox allCheckBox,personCheckBox,movieCheckBox,tvCheckBox;
    Switch adultSwitch;
    Button applyBT;
    Toolbar toolbar;
    boolean isCheckAll,isTV,isMovie,isPerson,isAdult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFilterBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_filter);
        setContentView(binding.getRoot());
        getBinding();
        getData();
        initialize();
        handleCheckBox();
        onSwitchHandle();
        onApplyButtonHandle();
    }
    private void getBinding(){
        toolbar=binding.toolBarFilterView;
        allCheckBox=binding.checkBoxAllFilterView;
        personCheckBox=binding.checkBoxPersonFilterView;
        movieCheckBox=binding.checkBoxMovieFilterView;
        tvCheckBox=binding.checkBoxTVFilterView;
        adultSwitch=binding.switchAdultFilterView;
        applyBT=binding.applyFilterButtonView;
    }
    private void getData(){
        Intent intent=getIntent();
        isAdult = intent.getBooleanExtra(StringConstants.isAdultKey, false);
        isCheckAll = intent.getBooleanExtra(StringConstants.isCheckAllKey, true);
        isPerson = intent.getBooleanExtra(StringConstants.isPersonKey, true);
        isMovie = intent.getBooleanExtra(StringConstants.isMovieKey, true);
        isTV = intent.getBooleanExtra(StringConstants.isTVKey, true);
    }
    private void onApplyButtonHandle(){
        applyBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(StringConstants.isAdultKey, adultSwitch.isChecked());
                intent.putExtra(StringConstants.isCheckAllKey, allCheckBox.isChecked());
                intent.putExtra(StringConstants.isPersonKey, personCheckBox.isChecked());
                intent.putExtra(StringConstants.isMovieKey, movieCheckBox.isChecked());
                intent.putExtra(StringConstants.isTVKey, tvCheckBox.isChecked());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
    private void onSwitchHandle(){
        adultSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            }
        });
    }
    private void initialize(){
        setSupportActionBar(toolbar);
        allCheckBox.setChecked(isCheckAll);
        personCheckBox.setChecked(isPerson);
        movieCheckBox.setChecked(isMovie);
        tvCheckBox.setChecked(isTV);
        adultSwitch.setChecked(isAdult);
    }
    private void handleCheckBox(){
        allCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allCheckBox.isChecked()){
                    personCheckBox.setChecked(true);
                    movieCheckBox.setChecked(true);
                    tvCheckBox.setChecked(true);
                }else{
                    personCheckBox.setChecked(false);
                    movieCheckBox.setChecked(false);
                    tvCheckBox.setChecked(false);
                }
            }
        });

        personCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(personCheckBox.isChecked()&&movieCheckBox.isChecked()&&tvCheckBox.isChecked()){
                    allCheckBox.setChecked(true);
                }else if(!personCheckBox.isChecked()&&!movieCheckBox.isChecked()&&!tvCheckBox.isChecked()){
                    allCheckBox.setChecked(false);
                }
                if(!personCheckBox.isChecked()){
                    if(allCheckBox.isChecked()){
                        allCheckBox.setChecked(false);
                    }
                }
            }
        });

        movieCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(personCheckBox.isChecked()&&movieCheckBox.isChecked()&&tvCheckBox.isChecked()){
                    allCheckBox.setChecked(true);
                }else if(!personCheckBox.isChecked()&&!movieCheckBox.isChecked()&&!tvCheckBox.isChecked()){
                    allCheckBox.setChecked(false);
                }
                if(!movieCheckBox.isChecked()){
                    if(allCheckBox.isChecked()){
                        allCheckBox.setChecked(false);
                    }
                }
            }
        });


        tvCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(personCheckBox.isChecked()&&movieCheckBox.isChecked()&&tvCheckBox.isChecked()){
                    allCheckBox.setChecked(true);
                }else if(!personCheckBox.isChecked()&&!movieCheckBox.isChecked()&&!tvCheckBox.isChecked()){
                    allCheckBox.setChecked(false);
                }
                if(!tvCheckBox.isChecked()){
                    if(allCheckBox.isChecked()){
                        allCheckBox.setChecked(false);
                    }
                }
            }
        });

    }

}