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
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.constants.enums.TypeOfMedia;
import com.example.netflop.constants.enums.WatchStatus;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.TVs.GuestStar;
import com.example.netflop.utils.listeners.OnClickIDListener;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListGuestStarAdapter extends RecyclerView.Adapter<ListGuestStarAdapter.ViewHolder> {
    List<GuestStar> listGuestStar;
    OnClickIDListener itemTouchHelperAdapter;
    Context context;
    FavouriteMediaViewModel favouriteMediaViewModel;
    private List<FavouriteMedia> favouriteData = new ArrayList<>();

    public ListGuestStarAdapter(List<GuestStar> listGuestStar,OnClickIDListener itemTouchHelperAdapter, Context context,FavouriteMediaViewModel favouriteMediaViewModel) {
        this.listGuestStar = listGuestStar;
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
        this.context = context;
        this.favouriteMediaViewModel=favouriteMediaViewModel;
        observeFavouriteData();
    }
    private void observeFavouriteData(){
        favouriteMediaViewModel.getFavouritePeople().observe((LifecycleOwner) context, favouriteMedia -> {
            favouriteData = favouriteMedia;
            notifyDataSetChanged();
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_guest_star_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GuestStar guestStar=listGuestStar.get(position);
        holder.nameTextView.setText(guestStar.getName()!=null ? guestStar.getName() :guestStar.getOriginalName()!=null?guestStar.getOriginalName():"null");

        String imageURL= URLConstants.imageURL+guestStar.getProfilePath();
        Log.d("TAG","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.circel_avatar).into(holder.imageView);
        holder.characterNameTextView.setText(guestStar.getCharacter()!=null?guestStar.getCharacter():"null");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemTouchHelperAdapter.onCLick(guestStar.getId(), StringConstants.personType);
            }
        });

        Optional<FavouriteMedia> optionalFavouriteMedia=favouriteData.stream().filter(favouriteMedia -> favouriteMedia.getMediaID()==guestStar.getId()).findFirst();
        if(optionalFavouriteMedia.isPresent()){
            holder.addGuestStarButtonView.setImageResource(R.drawable.remove);
        }else {
            holder.addGuestStarButtonView.setImageResource(R.drawable.add);
        }
        holder.addGuestStarButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(optionalFavouriteMedia.isPresent()){
                    favouriteMediaViewModel.deleteFavouriteMedia(optionalFavouriteMedia.get().getId());
                }else{
                    favouriteMediaViewModel.insertFavouriteMedia(guestStar.getId(),guestStar.getName(), TypeOfMedia.person,null,null,guestStar.getProfilePath(), WatchStatus.UNWATCH);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGuestStar.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView nameTextView,characterNameTextView;
        ImageButton addGuestStarButtonView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=(TextView)itemView.findViewById(R.id.guestStarNameTextView) ;
            imageView=(ImageView)  itemView.findViewById(R.id.guestStarProfileView) ;
            cardView=(CardView)  itemView.findViewById(R.id.singleGuestStarCardView) ;
            characterNameTextView=(TextView)itemView.findViewById(R.id.guestStarCharacterTextView) ;
            addGuestStarButtonView=(ImageButton)itemView.findViewById(R.id.addGuestStarButtonView) ;
        }
    }
}
