package com.example.netflop.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.View;

import com.example.netflop.utils.listeners.OnClickListener;

public class ClickableSpanHandler {
    public static SpannableString createClickableSpan(String text, OnClickListener onClickListener) {
        SpannableString spannableString = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (onClickListener != null) {
                    onClickListener.onClick();
                }
            }
        };
        spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
