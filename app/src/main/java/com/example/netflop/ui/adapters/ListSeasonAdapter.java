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
import com.example.netflop.data.models.TVs.CreatedBy;
import com.example.netflop.data.models.TVs.Season;
import com.example.netflop.utils.OnTVClickListener;

import java.util.List;

public class ListSeasonAdapter extends RecyclerView.Adapter<ListSeasonAdapter.ViewHolder> {
    List<Season> listSeason;
    Context context;
    OnTVClickListener onTVClickListener;

    public ListSeasonAdapter(List<Season> listSeason, Context context,OnTVClickListener onTVClickListener) {
        this.listSeason = listSeason;
        this.context = context;
        this.onTVClickListener=onTVClickListener;
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
