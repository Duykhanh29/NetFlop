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
import com.example.netflop.data.models.remote.TVs.Season;
import com.example.netflop.utils.listeners.OnTVClickListener;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListSeasonAdapter extends RecyclerView.Adapter<ListSeasonAdapter.ViewHolder> {
    List<Season> listSeason;
    Context context;
    OnTVClickListener onTVClickListener;
    FavouriteMediaViewModel favouriteMediaViewModel;
    private List<FavouriteMedia> favouriteData = new ArrayList<>();
    int tvSeriesID;

    public ListSeasonAdapter(int tvSeriesID,List<Season> listSeason, Context context,OnTVClickListener onTVClickListener,FavouriteMediaViewModel favouriteMediaViewModel) {
        this.listSeason = listSeason;
        this.context = context;
        this.onTVClickListener=onTVClickListener;
        this.favouriteMediaViewModel=favouriteMediaViewModel;
        this.tvSeriesID=tvSeriesID;
        observeFavouriteData();
    }
    private void observeFavouriteData(){
        favouriteMediaViewModel.getFavouriteTVSeasons().observe((LifecycleOwner) context, favouriteMedia -> {
            favouriteData = favouriteMedia;
            notifyDataSetChanged();
        });
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_season_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Season season=listSeason.get(position);
        holder.nameTextView.setText(season.getName()!=null ? season.getName() :"null");
        holder.seasonNumberTV.setText("Season "+season.getSeasonNumber());
        String imageURL= URLConstants.imageURL+season.getPosterPath();
        Log.d("TAG","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.place_holder).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTVClickListener.onClick(season.getSeasonNumber());
            }
        });

        Optional<FavouriteMedia> optionalFavouriteMedia=favouriteData.stream().filter(favouriteMedia -> favouriteMedia.getMediaID()==tvSeriesID&&favouriteMedia.getSeasonNumber()== season.getSeasonNumber()).findFirst();
        if(optionalFavouriteMedia.isPresent()){
            holder.favouriteButton.setImageResource(R.drawable.favoourite_no_border);
        }else {
            holder.favouriteButton.setImageResource(R.drawable.favourite);
        }
        holder.favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(optionalFavouriteMedia.isPresent()){
                    favouriteMediaViewModel.deleteFavouriteMedia(optionalFavouriteMedia.get().getId());
                }else{
                    favouriteMediaViewModel.insertFavouriteMedia(tvSeriesID,season.getName(), TypeOfMedia.TVSeason,season.getSeasonNumber(),null,season.getPosterPath());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSeason.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView nameTextView,seasonNumberTV;
        ImageButton favouriteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=(TextView)itemView.findViewById(R.id.seasonNameTextView) ;
            favouriteButton=(ImageButton)itemView.findViewById(R.id.favourite_tv_season_view) ;
            imageView=(ImageView)  itemView.findViewById(R.id.seasonView) ;
            cardView=(CardView)  itemView.findViewById(R.id.single_season_card_view) ;
            seasonNumberTV=(TextView)itemView.findViewById(R.id.seasonNumberTextView) ;

        }
    }
}
