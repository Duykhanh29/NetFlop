package com.example.netflop.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflop.R;
import com.facebook.shimmer.ShimmerFrameLayout;

public class HorizontalShimmerAdapter extends RecyclerView.Adapter<HorizontalShimmerAdapter.ViewHolder> {
    Context context;

    public HorizontalShimmerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_shimmer_horizontal_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.shimmerFrameLayout.stopShimmer();
    }
    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ShimmerFrameLayout shimmerFrameLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shimmerFrameLayout=(ShimmerFrameLayout) itemView.findViewById(R.id.shimmerFrameLayoutHorizontal);
        }
    }
}
