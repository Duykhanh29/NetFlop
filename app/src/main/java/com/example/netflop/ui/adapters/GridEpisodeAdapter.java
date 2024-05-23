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
import com.example.netflop.data.models.TVs.Episode;
import com.example.netflop.utils.OnTVClickListener;

import java.util.List;

public class GridEpisodeAdapter extends RecyclerView.Adapter<GridEpisodeAdapter.ViewHolder> {
    List<Episode> listEpisode;
    Context context;
    OnTVClickListener onTVClickListener;

    public GridEpisodeAdapter(List<Episode> listEpisode, Context context, OnTVClickListener onTVClickListener) {
        this.listEpisode = listEpisode;
        this.context = context;
        this.onTVClickListener = onTVClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_tv_episode_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Episode episode=listEpisode.get(position);
        holder.nameOfTVTextView.setText(episode.getName());
        holder.starTextView.setText(episode.getVoteAverage()+"");
        holder.episodeCountTextView.setText(episode.getEpisodeNumber()+"");
        String imageURL= URLConstants.imageURL+episode.getStillPath();
        Log.d("TAG","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.place_holder).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTVClickListener.onClick(episode.getEpisodeNumber());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listEpisode.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView nameOfTVTextView,starTextView,episodeCountTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.single_tv_episode_view);
            nameOfTVTextView=(TextView) itemView.findViewById(R.id.tvEpisodeNameView);
            starTextView=(TextView) itemView.findViewById(R.id.starTVEpisodeView);
            episodeCountTextView=(TextView) itemView.findViewById(R.id.tvEpisodeCountView);
            imageView=(ImageView) itemView.findViewById(R.id.tvEpisodeImageView);
        }
    }
}
