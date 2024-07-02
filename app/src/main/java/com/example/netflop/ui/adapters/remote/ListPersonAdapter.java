package com.example.netflop.ui.adapters.remote;

import android.content.Context;
import android.util.Log;
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
import com.example.netflop.constants.enums.WatchStatus;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.people.Person;
import com.example.netflop.utils.listeners.ItemTouchHelperAdapter;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListPersonAdapter extends RecyclerView.Adapter<ListPersonAdapter.ViewHolder> implements Filterable {
    List<Person> listPeople;
    Context context;
    int layout;
    ItemTouchHelperAdapter itemTouchHelperAdapter;
    FavouriteMediaViewModel favouriteMediaViewModel;
    private List<FavouriteMedia> favouriteData = new ArrayList<>();

    public ListPersonAdapter(List<Person> listPeople, Context context, int layout,ItemTouchHelperAdapter itemTouchHelperAdapter,FavouriteMediaViewModel favouriteMediaViewModel) {
        this.listPeople = listPeople;
        this.context = context;
        this.layout = layout;
        this.itemTouchHelperAdapter=itemTouchHelperAdapter;
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
        View view= LayoutInflater.from(context).inflate(layout,parent,false);
        return new ListPersonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person person= listPeople.get(position);
        holder.namePersonTextView.setText(person.getName());
        String imageURL= URLConstants.imageURL+person.getProfilePath();
        Glide.with(context).load(imageURL).placeholder(R.drawable.circel_avatar).into(holder.profileView);
        holder.personCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemTouchHelperAdapter.onPersonClick(person);
            }
        });
        Log.d("CheckData","personID: "+person.getID()+", name: "+person.getName());
        Optional<FavouriteMedia> optionalFavouriteMedia=favouriteData.stream().filter(favouriteMedia -> favouriteMedia.getMediaID()==person.getID()).findFirst();
        if(optionalFavouriteMedia.isPresent()){
            Log.d("CHeck data","MediaID: "+optionalFavouriteMedia.get().getMediaID()+", and personID: "+person.getID());
            holder.addPersonButton.setImageResource(R.drawable.remove);
        }else {
            holder.addPersonButton.setImageResource(R.drawable.favourite);
        }
        holder.addPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(optionalFavouriteMedia.isPresent()){
                    favouriteMediaViewModel.deleteFavouriteMedia(optionalFavouriteMedia.get().getId());
                }else{
                    favouriteMediaViewModel.insertFavouriteMedia(person.getID(),person.getName(), TypeOfMedia.person,null,null,person.getProfilePath(), WatchStatus.UNWATCH);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPeople.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView namePersonTextView;
        CardView personCardView;
        ImageView profileView;
        ImageButton addPersonButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileView=(ImageView) itemView.findViewById(R.id.profilePersonView);
            personCardView=(CardView) itemView.findViewById(R.id.person_card_view);
            namePersonTextView=(TextView) itemView.findViewById(R.id.personNameTextView);
            addPersonButton=(ImageButton) itemView.findViewById(R.id.addPersonButton);
        }
    }
    @Override
    public Filter getFilter() {
        return null;
    }
}
