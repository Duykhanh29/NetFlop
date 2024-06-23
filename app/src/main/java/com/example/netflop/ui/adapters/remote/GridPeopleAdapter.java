package com.example.netflop.ui.adapters.remote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.example.netflop.data.models.remote.people.Person;
import com.example.netflop.utils.listeners.ItemTouchHelperAdapter;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GridPeopleAdapter extends RecyclerView.Adapter<GridPeopleAdapter.ViewHolder>  implements Filterable {
    List<Person> listPeople;
    Context context;
    ItemTouchHelperAdapter itemTouchHelperAdapter;
    FavouriteMediaViewModel favouriteMediaViewModel;
    private List<FavouriteMedia> favouriteData = new ArrayList<>();

    public GridPeopleAdapter(List<Person> listPeople, Context context, ItemTouchHelperAdapter itemTouchHelperAdapter,FavouriteMediaViewModel favouriteMediaViewModel) {
        this.listPeople = listPeople;
        this.context = context;
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
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
        View view= LayoutInflater.from(context).inflate(R.layout.a_grid_person_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person person= listPeople.get(position);
        holder.personNameView.setText(person.getName());
        String imageURL= URLConstants.imageURL+person.getProfilePath();
        Glide.with(context).load(imageURL).placeholder(R.drawable.circel_avatar).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemTouchHelperAdapter.onPersonClick(person);
            }
        });

        Optional<FavouriteMedia> optionalFavouriteMedia=favouriteData.stream().filter(favouriteMedia -> favouriteMedia.getMediaID()==person.getID()).findFirst();
        if(optionalFavouriteMedia.isPresent()){
            holder.addFavouritePeopleButton.setImageResource(R.drawable.favoourite_no_border);
        }else {
            holder.addFavouritePeopleButton.setImageResource(R.drawable.favourite);
        }
        holder.addFavouritePeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(optionalFavouriteMedia.isPresent()){
                    favouriteMediaViewModel.deleteFavouriteMedia(optionalFavouriteMedia.get().getId());
                }else{
                    favouriteMediaViewModel.insertFavouriteMedia(person.getID(),person.getName(), TypeOfMedia.person,null,null, person.getProfilePath());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPeople.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView personNameView;
        ImageView imageView;
        ImageButton addFavouritePeopleButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.aGridPersonCardView);
            personNameView=(TextView) itemView.findViewById(R.id.personGridNameTextView);
            imageView=(ImageView) itemView.findViewById(R.id.profileGridPersonView);
            addFavouritePeopleButton=(ImageButton) itemView.findViewById(R.id.addFavouritePeopleButton);
        }
    }
}
