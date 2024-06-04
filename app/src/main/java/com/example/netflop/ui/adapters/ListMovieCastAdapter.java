package com.example.netflop.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.netflop.R;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.MovieCast;
import com.example.netflop.utils.listeners.ItemMovieCastListener;

import java.util.List;

public class ListMovieCastAdapter extends RecyclerView.Adapter<ListMovieCastAdapter.ViewHolder> {
    List<MovieCast> movieCastList;
    Context context;
    ItemMovieCastListener itemMovieCastListener;

    public ListMovieCastAdapter(List<MovieCast> movieCastList, Context context, ItemMovieCastListener itemMovieCastListener) {
        this.movieCastList = movieCastList;
        this.context = context;
        this.itemMovieCastListener = itemMovieCastListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_movie_cast_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieCast movieCast=movieCastList.get(position);
        holder.titleMovieCastView.setText(movieCast.getTitle());
        holder.typeMovieCastView.setText(movieCast.getMediaType()!=null ? movieCast.getMediaType():"");
        String imageURL= URLConstants.imageURL+movieCast.getPosterPath();
        Log.d("TAG movieCastList","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.circel_avatar).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemMovieCastListener.onMovieCastClick(movieCast);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieCastList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView titleMovieCastView,typeMovieCastView;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.posterImageView);
            titleMovieCastView=(TextView)itemView.findViewById(R.id.movieCastNameView);
            typeMovieCastView=(TextView)itemView.findViewById(R.id.movieCastTypeView);
            cardView=(CardView) itemView.findViewById(R.id.single_movie_cast_card_view);
        }
    }
}
