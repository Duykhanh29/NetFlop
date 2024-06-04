package com.example.netflop.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.netflop.R;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Movie;
import com.example.netflop.utils.listeners.ItemTouchHelperAdapter;

import java.util.List;

public class SecondListMovieAdapter extends RecyclerView.Adapter<SecondListMovieAdapter.ViewHolder> implements Filterable {
    List<Movie> listMovie;
    Context context;
    ItemTouchHelperAdapter itemTouchMovieHelper;
    int layout;

    public SecondListMovieAdapter(List<Movie> listMovie, Context context,int layout,ItemTouchHelperAdapter itemTouchMovieHelper) {
        this.listMovie = listMovie;
        this.context = context;
        this.layout=layout;
        this.itemTouchMovieHelper=itemTouchMovieHelper;
    }

    @NonNull
    @Override
    public SecondListMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(layout,parent,false);
        return new SecondListMovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondListMovieAdapter.ViewHolder holder, int position) {
        Movie movie=listMovie.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.voteRatedTextView.setText(movie.getVoteAverage()+"");
        String imageURL= URLConstants.imageURL+movie.getPosterPath();
        Log.d("TAG","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.place_holder).into(holder.posterView);
        holder.movieCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemTouchMovieHelper.onMovieClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public  static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView titleTextView,voteRatedTextView;
        CardView movieCardView;
        ImageView posterView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView=(TextView) itemView.findViewById(R.id.movieTitleText);
            voteRatedTextView=(TextView) itemView.findViewById(R.id.voteRatedText);
            movieCardView=(CardView) itemView.findViewById(R.id.a_movie_card_view);
            posterView=(ImageView) itemView.findViewById(R.id.posterMovie);
        }
    }
    @Override
    public Filter getFilter() {
        return null;
    }
}
