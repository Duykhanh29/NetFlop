package com.example.netflop.ui.adapters.remote;

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
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.netflop.R;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.constants.enums.TypeOfMedia;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.TVs.Episode;
import com.example.netflop.utils.listeners.OnTVClickListener;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GridEpisodeAdapter extends RecyclerView.Adapter<GridEpisodeAdapter.ViewHolder> {
    List<Episode> listEpisode;
    Context context;
    OnTVClickListener onTVClickListener;
    FavouriteMediaViewModel favouriteMediaViewModel;
    private List<FavouriteMedia> favouriteData = new ArrayList<>();
    int tvSeriesID;

    public GridEpisodeAdapter(int tvSeriesID,List<Episode> listEpisode, Context context, OnTVClickListener onTVClickListener,FavouriteMediaViewModel favouriteMediaViewModel) {
        this.listEpisode = listEpisode;
        this.context = context;
        this.onTVClickListener = onTVClickListener;
        this.favouriteMediaViewModel=favouriteMediaViewModel;
        this.tvSeriesID=tvSeriesID;
        observeFavouriteData();
    }


    private void observeFavouriteData(){
        favouriteMediaViewModel.getFavouriteTVEpisodes().observe((LifecycleOwner) context, favouriteMedia -> {
            favouriteData = favouriteMedia;
            notifyDataSetChanged();
        });
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

        Optional<FavouriteMedia> optionalFavouriteMedia=favouriteData.stream().filter(favouriteMedia -> favouriteMedia.getMediaID()==tvSeriesID&&favouriteMedia.getSeasonNumber()==episode.getSeasonNumber()&&favouriteMedia.getEpisodeNumber()==episode.getEpisodeNumber()).findFirst();
        if(optionalFavouriteMedia.isPresent()){
            holder.addImageButton.setImageResource(R.drawable.remove);
        }else {
            holder.addImageButton.setImageResource(R.drawable.add);
        }
        holder.addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(optionalFavouriteMedia.isPresent()){
                    favouriteMediaViewModel.deleteFavouriteMedia(optionalFavouriteMedia.get().getId());
                }else{
                    favouriteMediaViewModel.insertFavouriteMedia(tvSeriesID,episode.getName(), TypeOfMedia.TVEpisode,episode.getSeasonNumber(),episode.getEpisodeNumber(), episode.getStillPath());
                }
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
        ImageButton addImageButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.single_tv_episode_view);
            nameOfTVTextView=(TextView) itemView.findViewById(R.id.tvEpisodeNameView);
            starTextView=(TextView) itemView.findViewById(R.id.starTVEpisodeView);
            episodeCountTextView=(TextView) itemView.findViewById(R.id.tvEpisodeCountView);
            imageView=(ImageView) itemView.findViewById(R.id.tvEpisodeImageView);
            addImageButton=(ImageButton) itemView.findViewById(R.id.addImageButton);
        }
    }
}
