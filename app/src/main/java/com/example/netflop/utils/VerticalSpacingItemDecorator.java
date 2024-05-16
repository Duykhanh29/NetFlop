package com.example.netflop.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpacingItemDecorator extends RecyclerView.ItemDecoration{
    private  int verticalSpaceHeight,horizontalSpaceWidth;

    public VerticalSpacingItemDecorator(int verticalSpaceHeight,int horizontalSpaceWidth) {
        this.verticalSpaceHeight = verticalSpaceHeight;
        this.horizontalSpaceWidth=horizontalSpaceWidth;
    }



    public VerticalSpacingItemDecorator() {
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = verticalSpaceHeight;
        outRect.right=horizontalSpaceWidth;
    }
}
