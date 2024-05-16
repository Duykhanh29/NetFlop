package com.example.netflop.helpers;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.widget.TextView;

import com.example.netflop.R;
import com.google.android.flexbox.FlexboxLayout;

public class CustomTextView {
    public static TextView chipTextView(TextView textView){
//            textView.setText(country.getName());
        textView.setPadding(4, 2, 4, 2);
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(4, 2, 4, 2);  // Đặt margins cho TextView
        textView.setLayoutParams(params);
        return textView;
    }
}
