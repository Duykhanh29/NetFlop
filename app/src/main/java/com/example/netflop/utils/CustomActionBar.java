package com.example.netflop.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import androidx.appcompat.app.ActionBar;

import com.example.netflop.R;

public class CustomActionBar {
    public static void createActionBar(ActionBar actionBar,String title){
        SpannableString spannableTitle = new SpannableString(title);
        spannableTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.black_arrow_back);
    }
}
