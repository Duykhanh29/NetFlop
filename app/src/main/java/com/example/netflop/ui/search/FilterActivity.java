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
import android.widget.RadioButton;
import android.widget.Switch;

import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.databinding.ActivityFilterBinding;

public class FilterActivity extends AppCompatActivity {
    ActivityFilterBinding binding;
    RadioButton allRadioButton,movieRadioButton,peopleRadioButton,tvRadioButton;
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
        onSwitchHandle();
        onApplyButtonHandle();
    }
    private void getBinding(){
        toolbar=binding.toolBarFilterView;
        allRadioButton=binding.checkAllButton;
        peopleRadioButton=binding.checkPeopleButton;
        movieRadioButton=binding.checkMovieButton;
        tvRadioButton=binding.checkTVButton;
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
                intent.putExtra(StringConstants.isCheckAllKey, allRadioButton.isChecked());
                intent.putExtra(StringConstants.isPersonKey, peopleRadioButton.isChecked());
                intent.putExtra(StringConstants.isMovieKey, movieRadioButton.isChecked());
                intent.putExtra(StringConstants.isTVKey, tvRadioButton.isChecked());
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
        allRadioButton.setChecked(isCheckAll);
        peopleRadioButton.setChecked(isPerson);
        movieRadioButton.setChecked(isMovie);
        tvRadioButton.setChecked(isTV);
        adultSwitch.setChecked(isAdult);
    }


}