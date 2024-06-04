package com.example.netflop.utils;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewUtils {
    public static void setupHorizontalRecyclerView(Context context, RecyclerView recyclerView, RecyclerView.Adapter<?> adapter, int orientation, boolean reverseLayout){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, orientation, reverseLayout);
        recyclerView.setLayoutManager(layoutManager);
        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(0,20);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setAdapter(adapter);
    }
    public static void setupVerticalRecyclerView(Context context, RecyclerView recyclerView, RecyclerView.Adapter<?> adapter, int orientation, boolean reverseLayout){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, orientation, reverseLayout);
        recyclerView.setLayoutManager(layoutManager);
        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(20,0);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setAdapter(adapter);
    }
    public static void setupGridRecyclerView(Context context, RecyclerView recyclerView, RecyclerView.Adapter<?> adapter,int count){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, count);
        recyclerView.setLayoutManager(gridLayoutManager);
        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(20,20);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setAdapter(adapter);
    }
}
