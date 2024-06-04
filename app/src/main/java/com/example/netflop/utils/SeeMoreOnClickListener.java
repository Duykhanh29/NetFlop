package com.example.netflop.utils;

import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.netflop.utils.listeners.OnClickListener;

public class SeeMoreOnClickListener {
    public static void getSeeMoreOnClick(TextView textView, Runnable  onclick){
        SpannableString spannableString = ClickableSpanHandler.createClickableSpan(textView.getText().toString(), new OnClickListener() {
            @Override
            public void onClick() {
//                        List<Video> listVideo=movieVideosData.getResults();
//                        // nav to all trailers of movie
//                        navToAllTrailerActivity(listVideo);
                Log.d("TAG1","wth");
            }
        });
        textView.setText(spannableString);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.run();
            }
        });
    }
}
