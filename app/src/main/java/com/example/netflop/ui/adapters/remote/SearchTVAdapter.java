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
import com.example.netflop.data.models.remote.TVs.AiringTodayModel;
import com.example.netflop.utils.listeners.ItemTVOnClickListener;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SearchTVAdapter extends RecyclerView.Adapter<SearchTVAdapter.ViewHolder> {
    List<AiringTodayModel> listTV;
    Context context;
    ItemTVOnClickListener itemTVOnClickListener;
    FavouriteMediaViewModel favouriteMediaViewModel;
    private List<FavouriteMedia> favouriteData = new ArrayList<>();

    public SearchTVAdapter(List<AiringTodayModel> listTV, Context context, ItemTVOnClickListener itemTVOnClickListener,FavouriteMediaViewModel favouriteMediaViewModel) {
        this.listTV = listTV;
        this.context = context;
        this.itemTVOnClickListener = itemTVOnClickListener;
        this.favouriteMediaViewModel=favouriteMediaViewModel;
        observeFavouriteData();
    }
    private void observeFavouriteData(){
        favouriteMediaViewModel.getFavouriteTVSeries().observe((LifecycleOwner) context, favouriteMedia -> {
            favouriteData = favouriteMedia;
            notifyDataSetChanged();
        });
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


        Optional<FavouriteMedia> optionalFavouriteMedia=favouriteData.stream().filter(favouriteMedia -> favouriteMedia.getMediaID()==todayModel.getId()).findFirst();
        if(optionalFavouriteMedia.isPresent()){
            holder.addButton.setImageResource(R.drawable.remove);
        }else {
            holder.addButton.setImageResource(R.drawable.add);
        }
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(optionalFavouriteMedia.isPresent()){
                    favouriteMediaViewModel.deleteFavouriteMedia(optionalFavouriteMedia.get().getId());
                }else{
                    favouriteMediaViewModel.insertFavouriteMedia(todayModel.getId(),todayModel.getName(), TypeOfMedia.TVSeries,null,null,todayModel.getPosterPath());
                }
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
