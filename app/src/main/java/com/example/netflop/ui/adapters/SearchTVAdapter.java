package com.example.netflop.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.netflop.R;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.TVs.AiringTodayModel;
import com.example.netflop.utils.ItemTVOnClickListener;

import java.util.List;

public class SearchTVAdapter extends RecyclerView.Adapter<SearchTVAdapter.ViewHolder> {
    List<AiringTodayModel> listTV;
    Context context;
    ItemTVOnClickListener itemTVOnClickListener;

    public SearchTVAdapter(List<AiringTodayModel> listTV, Context context, ItemTVOnClickListener itemTVOnClickListener) {
        this.listTV = listTV;
        this.context = context;
        this.itemTVOnClickListener = itemTVOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_search_tv_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AiringTodayModel todayModel=listTV.get(position);
        holder.nameOfTVTextView.setText(todayModel.getName());
        holder.starTextView.setText(todayModel.getVoteAverage()+"");
        String imageURL= URLConstants.imageURL+todayModel.getPosterPath();
        Log.d("TAG","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.place_holder).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemTVOnClickListener.onTVCLick(todayModel);
            }
        });
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listTV.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView nameOfTVTextView,starTextView;
        ImageButton addButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.search_tv_card_view);
            nameOfTVTextView=(TextView) itemView.findViewById(R.id.nameTVSearchView);
            starTextView=(TextView) itemView.findViewById(R.id.starSearchTvView);
            imageView=(ImageView) itemView.findViewById(R.id.searchTVImageView);
            addButton=(ImageButton)itemView.findViewById(R.id.searchTVSeriesButtonView) ;
        }
    }
    public void clearData() {
        if (listTV != null) {
            listTV.clear();
            notifyDataSetChanged();
        }
    }
}
